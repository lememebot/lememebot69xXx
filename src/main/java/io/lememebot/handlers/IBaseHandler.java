package io.lememebot.handlers;

import io.lememebot.audio.BotAudioManager;
import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;
import io.lememebot.core.Gang;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.handlers
 * Created by Guy on 11-Mar-17.
 * <p>
 * Description:
 */
public abstract class IBaseHandler {

    private Command m_command;
    private MessageReceivedEvent m_event;
    final static Logger log = LogManager.getLogger();

    IBaseHandler(String strCmdPrefix)
    {
        m_command = new Command(strCmdPrefix);
    }

    IBaseHandler(String strCmdPrefix,int cmdNumParameters)
    {
        m_command = new Command(strCmdPrefix,cmdNumParameters);
    }

    public void onMessageReceived(MessageReceivedEvent event)
    {
        m_event = event;
        onMessage(event,m_command);
    }

    // Abstract method to be implemented on real handlers
    protected abstract void onMessage(MessageReceivedEvent event,Command cmd);

    protected void playAudio(MediaRequest mediaRequest)
    {
        BotAudioManager.playAudio(m_event.getGuild(),mediaRequest);
    }

    private void setEvent(MessageReceivedEvent event)
    {
        m_event = event;
    }

    public Command getCommand()
    {
        return m_command;
    }

    String getAuthorName()
    {
        return m_event.getAuthor().getName();
    }

    User getAuthor()
    {
        return m_event.getAuthor();
    }

    boolean isBotMentioned()
    {
        // TODO: check if this works
        return m_event.getMessage().getMentionedUsers().contains(m_event.getJDA().getSelfUser());
    }

    void sendMessage(String message)
    {
        m_event.getChannel().sendMessage(message).submit();
    }

    protected boolean isSenderYoni()
    {
        return getAuthorName().equals(Gang.YONI);
    }

    protected boolean isSenderZafig()
    {
        return getAuthorName().equals(Gang.ZAFIG);
    }

    protected boolean isSenderMalul() { return getAuthorName().equals(Gang.MALUL); }

    protected boolean isSenderDean()
    {
        return getAuthorName().equals(Gang.IDIOT);
    }
}
