package io.lememebot.handlers;

import io.lememebot.media.IMediaProvider;
import io.lememebot.media.MediaDescriptor;
import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;
import io.lememebot.extras.overwatch.OverwatchHero;

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
    public OverwatchHandler()
    {
        super("!");
    }

    @Override
    public MediaRequest onMessage(Command cmd)
    {
        switch (cmd.getCommand())
        {
            case "hello":
            case "hey":
                String heroName = cmd.getParameter(1);
                IMediaProvider owHero = null;

                if(!heroName.isEmpty())
                {
                    owHero = OverwatchHero.getHero(heroName);
                    if (null == owHero)
                    {
                        owHero = OverwatchHero.getRandomHero();
                        sendMessage("[ERROR] OverwatchHandler: " + heroName + " not found, choosing random hero");
                    }
                }
                else {
                    // Randrom choose hero
                    owHero = OverwatchHero.getRandomHero();
                }

                final List<MediaDescriptor> mediaDescriptorList = owHero.getMediaDescriptorList();
                if(!mediaDescriptorList.isEmpty()) {
                    return new MediaRequest(mediaDescriptorList.get(s_rnd.nextInt() % mediaDescriptorList.size()), getEvent().getAuthor());
                }

                break;

        }

        return null;
    }

}
