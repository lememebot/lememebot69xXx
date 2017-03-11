package io.lememebot.handlers;

import io.lememebot.core.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.handlers
 * Created by Guy on 11-Mar-17.
 * <p>
 * Description:
 */
public class OverwatchHandler extends IBaseHandler {

    public OverwatchHandler()
    {
        super("!");
    }

    @Override
    public void onMessage(Command cmd)
    {
        switch (cmd.getCommand())
        {
            case "hey":
                String heroName = cmd.getParameter(1);
                if(!heroName.isEmpty())
                {
                    // play specific hero sound
                    playSound(heroName);
                }

                break;
        }
    }
}
