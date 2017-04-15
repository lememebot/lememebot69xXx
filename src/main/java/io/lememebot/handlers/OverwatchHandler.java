package io.lememebot.handlers;

import io.lememebot.extras.overwatch.OverwatchMedia;
import io.lememebot.media.MediaDescriptor;
import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;
import io.lememebot.extras.overwatch.OverwatchHero;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.sql.*;
import java.util.List;
import java.util.Random;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.handlers
 * Created by Guy on 11-Mar-17.
 * <p>
 * Description:
 */
public class OverwatchHandler extends IBaseHandler {
    private static final Random s_rnd = new Random();
    private Connection owConnection;

    private final String queryGetHeroPref = "SELECT default_hero FROM ow_hero_pref where uid = ?";
    private final String insertHeroPref = "INSERT INTO ow_hero_pref VALUES(?,?)";
    private final String updateHeroPref = "UPDATE ow_hero_pref SET default_hero = ? where uid = ?";
    private final String createTable = "CREATE TABLE IF NOT EXISTS ow_hero_pref(uid string primary key,default_hero string not null)";
    private final String connectionString = "jdbc:sqlite:C:/sqlite/lememebot.db";

    /*
    How to create db:
    download sqlite3 binaries and dll (2 zips)
    open sqlite3.exe

    Run:
    create table dual (dummy char(1));
    insert into dual values('X');
    select * from dual;

    now gtfo
     */

    public OverwatchHandler() {
        super("!");
        owConnection = null;
        initDb();
    }

    private boolean connect() throws SQLException {
        log.debug("Getting connection");
        owConnection = DriverManager.getConnection(connectionString);
        log.debug("Test connection");
        try (Statement stmtDual = owConnection.createStatement()){
            try (ResultSet result = stmtDual.executeQuery("select * from dual"))
            {
                if (result.next()) {
                    log.info("Connected to database lememebot");
                    return true;
                }
                else
                    log.error("Could not connect to database lememebot");
            }
        }

        return false;
    }

    private void initDb() {
        try {
            if(connect()) {
                Statement stmtTables = owConnection.createStatement();
                stmtTables.executeUpdate(createTable);
                stmtTables.close();
            }
        }
        catch (SQLException ex)
        {
            log.error("Could not connect to database");
            ex.printStackTrace();
        }
    }

    private void setUserPref(User discordUser,String heroName)
    {
        if(null == owConnection) {
            sendMessage("Not connected to database");
            return;
        }

        if(!OverwatchHero.heroExists(heroName))
        {
            sendMessage("Invalid hero name \"" + heroName + "\"");
            return;
        }

        PreparedStatement stmtQuery = null;
        try {
            stmtQuery = owConnection.prepareStatement(updateHeroPref);
            stmtQuery.setString(1,heroName); // set
            stmtQuery.setString(2,discordUser.getId()); // where
            int rows = stmtQuery.executeUpdate();

            // User does not exists in table
            if(0 == rows)
            {
                stmtQuery.close();
                stmtQuery = owConnection.prepareStatement(insertHeroPref);

                /*
                * values
                */
                stmtQuery.setString(1,discordUser.getId());
                stmtQuery.setString(2,heroName);

                stmtQuery.executeUpdate();
                stmtQuery.close();
            }

            stmtQuery.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(null != stmtQuery)
                    stmtQuery.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private String getUserPref(User discordUser) {
        if(null == owConnection)
            return null;

        String heroName = null;
        PreparedStatement stmtQuery = null;
        try {
            stmtQuery = owConnection.prepareStatement(queryGetHeroPref);
            stmtQuery.setString(1,discordUser.getId());

            try (ResultSet queryResult = stmtQuery.executeQuery()) {
                if(queryResult.next())
                {
                    heroName = queryResult.getString(1);
                    if(heroName.isEmpty())
                        heroName = null;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(null != stmtQuery)
                    stmtQuery.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return heroName;
    }

    @Override
    public void onMessage(MessageReceivedEvent event, Command cmd) {
        switch (cmd.getCommand()) {
            case "hello":
            case "hey": {
                String heroName = cmd.getParameter(1);
                OverwatchHero owHero;

                if (!heroName.isEmpty()) {
                    log.info("Got parameter:" + heroName);
                    owHero = OverwatchHero.getHero(heroName);

                } else {
                    // Randrom choose hero
                    log.info("No parameter, choosing random hero");
                    heroName = getUserPref(getAuthor());

                    if (null == heroName)
                        owHero = OverwatchHero.getRandomHero();
                    else
                        owHero = OverwatchHero.getHero(heroName);
                }

                if (null == owHero) {
                    log.error("got null overwatch hero for " + heroName);
                    sendMessage(heroName + " does not exists");
                    break;
                }

                log.debug("chose " + owHero.getName());

                final List<MediaDescriptor> mediaDescriptorList = owHero.getMediaDescriptorList();
                if (null != mediaDescriptorList && !mediaDescriptorList.isEmpty()) {
                    int index = Math.abs(s_rnd.nextInt()) % mediaDescriptorList.size();
                    MediaDescriptor mediaDescriptor = mediaDescriptorList.get(index);

                    playAudio(new MediaRequest(mediaDescriptor, getAuthor()));
                } else {
                    sendMessage(owHero.getName() + " has got no sound files");
                    log.debug(owHero.getName() + " has got no sound files, sorry m8 :(");
                }

                break;
            }
            case "potg": {
                MediaDescriptor mediaDescriptor = OverwatchMedia.getSound("potg");
                if (!mediaDescriptor.isNullOrEmpty()) {
                    playAudio(new MediaRequest(mediaDescriptor, getAuthor()));
                }
                break;
            }
            case "owroast": {
                MediaDescriptor mediaDescriptor = OverwatchMedia.getSound("defeat");
                if (!mediaDescriptor.isNullOrEmpty()) {
                    playAudio(new MediaRequest(mediaDescriptor, getAuthor()));
                }
                break;
            }
            case "owset": {
                String heroName = cmd.getParameter(1);

                if (!heroName.isEmpty()) {
                    setUserPref(getAuthor(), heroName);
                }

                break;
            }
            case "owget": {
                String heroName = getUserPref(getAuthor());

                if(null != heroName && !heroName.isEmpty())
                {
                    sendMessage(getAuthor().getAsMention() + ", your default overwatch hero is \"" + heroName + "\"");
                }

                break;
            }
            case "help": {
                sendMessage("[OverwatchHandler] Options:\n" +
                        "!hello/hey <hero_name (default: random or user pref)> (play the hero hello sound, you can also use zafig's retarded nicknames)\n" +
                        "!potg (yall know what that is)\n" +
                        "!owroast (play this when some1 got roasted and mr.negi generation 3000 is unavailable)\n" +
                        "!owset <hero_name> (sets default hero to play when running !hello/hey with no parameter)\n" +
                        "!owget (Prints your default hero)");
                break;
            }
        }
    }
}
