package io.lememebot.core;

import io.lememebot.audio.BotAudioManager;
import io.lememebot.handlers.*;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;

/**
 * Project: lememebot69xXx
 * Package: io.github.lememebot.core
 * Created by Guy on 11-Mar-17.
 *
 * Description:
 */
public class BotServer extends ListenerAdapter {
    private final static Logger log = LogManager.getLogger();
    private JDA botInstance;
    private final ArrayList<IBaseHandler> eventsHandlers;

    public BotServer() {
        eventsHandlers = new ArrayList<>(16);

        eventsHandlers.add(new DebugHandler()); // used for command parse debugging
        eventsHandlers.add(new OverwatchHandler());
        eventsHandlers.add(new HoferHandler());
        eventsHandlers.add(new LastTimeHandler());
        eventsHandlers.add(new RemindMeHandler());
        eventsHandlers.add(new TrumpTweetHandler());
        eventsHandlers.add(new FilthyFrankHandler());
    }

    public boolean start() {
        try {

            botInstance = new JDABuilder(AccountType.BOT)
                    .setAudioEnabled(true)
                    .setAutoReconnect(true)
                    .setStatus(OnlineStatus.ONLINE)
                    .setToken(Gang.MALUL_TOKEM)
                    .buildBlocking();

            botInstance.addEventListener(this);
            BotAudioManager.init();

            return true;
        } catch (LoginException e) {
            log.error("WTF is LoginException, hehe fuck that shit\nwait thats not that good");
        } catch (InterruptedException e) {
            log.error("WTF is InterruptedException, hehe fuck that shit");
        } catch (RateLimitedException e) {
            log.error("WTF is RateLimitedException, hehe fuck that shit");
        }
        // Init failed
        return false;
    }

    public void stop() {
        BotAudioManager.shutdown();

        if (null != botInstance) {
            botInstance.shutdown(false);
            botInstance = null;
        }
    }

    @Override
    public final void onMessageReceived(MessageReceivedEvent event) {
        // Don't hook other bot messages
        if (!event.getAuthor().isBot()) {

            for (IBaseHandler eventHandler : eventsHandlers) {
                if (eventHandler.getCommand().parse(event.getMessage().getContent())) {
                   eventHandler.onMessageReceived(event);
                }
            }
        }
    }

    public boolean isOnline() {
        return null != botInstance && (botInstance.getStatus().equals(JDA.Status.CONNECTED));
    }
}
