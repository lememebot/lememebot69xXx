package io.lememebot.handlers;

import io.lememebot.core.Command;
import io.lememebot.core.Gang;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.handlers
 * Created by Guy on 11-Mar-17.
 * <p>
 * Description:
 */
public abstract class IBaseHandler extends ListenerAdapter {

    private Command command;
    private MessageReceivedEvent m_event;
    final static Logger log = LogManager.getLogger();

    IBaseHandler(String strCmdPrefix)
    {
        command = new Command(strCmdPrefix);
    }

    @Override
    public final void onMessageReceived(MessageReceivedEvent event) {
        m_event = event;
        if(command.parse(event.getMessage().getContent()))
        {
            onMessage(command);
        }
    }

    // Abstract method to be implemented on real handlers
    protected abstract void onMessage(Command cmd);

    MessageReceivedEvent getEvent()
    {
        return m_event;
    }

    String getAuthorName()
    {
        return getEvent().getAuthor().getName();
    }

    protected void sendMessage(String message)
    {
        m_event.getTextChannel().sendMessage(message);
    }

    protected boolean isSenderYoni()
    {
        return getAuthorName().equals(Gang.YONI);
    }

    protected boolean isSenderZafig()
    {
        return getAuthorName().equals(Gang.ZAFIG);
    }

    protected boolean isSenderMalul()
    {
        return getAuthorName().equals(Gang.MALUL);
    }

    protected boolean isSenderDean()
    {
        return getAuthorName().equals(Gang.IDIOT);
    }
}
