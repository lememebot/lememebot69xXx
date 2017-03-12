package io.lememebot.handlers;

import io.lememebot.audio.AudioRequest;
import io.lememebot.core.Command;
import io.lememebot.extras.overwatch.OverwatchHero;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

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
    public AudioRequest onMessage(Command cmd)
    {
        switch (cmd.getCommand())
        {
            case "hey":
                String heroName = cmd.getParameter(1);
                if(!heroName.isEmpty())
                {
                    OverwatchHero owHero = OverwatchHero.getHero(heroName);
                }

                break;
        }

        return null;
    }

}
