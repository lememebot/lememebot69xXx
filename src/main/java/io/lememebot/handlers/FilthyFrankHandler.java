package io.lememebot.handlers;

import io.lememebot.core.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.handlers
 * Created by Guy on 14-Apr-17.
 * <p>
 * Description:
 */
public class FilthyFrankHandler extends IBaseHandler {

    public FilthyFrankHandler()
    {
        super("!");
    }

    @Override
    protected void onMessage(MessageReceivedEvent event, Command cmd) {
        switch(cmd.getCommand())
        {
            case "420":
            case "blazeit":
            case "420blazeit":

                // play 420 blaze it by papa franku
                break;
        }
    }
}
