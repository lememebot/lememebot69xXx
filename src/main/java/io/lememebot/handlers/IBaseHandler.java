package io.lememebot.handlers;

import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;
import io.lememebot.core.Gang;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    // Abstract method to be implemented on real handlers
    public abstract MediaRequest onMessage(Command cmd);

    public void setEvent(MessageReceivedEvent event)
    {
        m_event = event;
    }

    public Command getCommand()
    {
        return m_command;
    }

    MessageReceivedEvent getEvent()
    {
        return m_event;
    }

    String getAuthorName()
    {
        return getEvent().getAuthor().getName();
    }

    boolean isBotMentioned() {
        // TODO: check if this works
        return getEvent().getMessage().getMentionedUsers().contains(getEvent().getJDA().getSelfUser());
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
