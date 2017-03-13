package io.lememebot.handlers;

import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;

/**
 * Project: lememebot69xXx
 * Package: io.github.lememebot.core
 * Created by Guy on 11-Mar-17.
 *
 * Description
 */
public class DebugHandler extends IBaseHandler {

    public DebugHandler()
    {
        super("");
    }

    @Override
    public MediaRequest onMessage(Command cmd)
    {
        /*log.debug("[{}] {}: {}",
                getEvent().getChannelType().toString(),
                getAuthorName(),
                getEvent().getMessage().getContent());

        log.debug("[Command] {} [Params] 1:{} 2:{} 3:{}",
                cmd.getCommand(),
                cmd.getParameter(1),
                cmd.getParameter(2),
                cmd.getParameter(3));*/

        if(cmd.getCommand().equals("whoami"))
        {
            sendMessage("You are " + getEvent().getAuthor().getAsMention());
        }

        return null;
    }
}
