package io.lememebot.handlers;

import io.lememebot.extras.overwatch.OverwatchMedia;
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

    public OverwatchHandler() {
        super("!");
    }

    @Override
    public MediaRequest onMessage(Command cmd) {
        switch (cmd.getCommand()) {
            case "hello":
            case "hey":
                String heroName = cmd.getParameter(1);
                OverwatchHero owHero;

                if (!heroName.isEmpty()) {
                    log.info("Got parameter:" + heroName);
                    owHero = OverwatchHero.getHero(heroName);
                } else {
                    // Randrom choose hero
                    log.info("No parameter, choosing random hero");
                    owHero = OverwatchHero.getRandomHero();
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

                    return (new MediaRequest(mediaDescriptor, getAuthor()));
                } else {
                    sendMessage(owHero.getName() + " has got no sound files");
                    log.debug(owHero.getName() + " has got no sound files, sorry m8 :(");
                }

                break;
            case "potg": {
                MediaDescriptor mediaDescriptor = OverwatchMedia.getSound("potg");
                if (!mediaDescriptor.isNullOrEmpty()) {
                    return (new MediaRequest(mediaDescriptor, getAuthor()));
                }
                break;
            }
            case "owroast":
                MediaDescriptor mediaDescriptor = OverwatchMedia.getSound("defeat");
                if (!mediaDescriptor.isNullOrEmpty()) {
                    return (new MediaRequest(mediaDescriptor, getAuthor()));
                }
                break;
            case "help":
                sendMessage("[OverwatchHandler] Options:\n" +
                        "!hello <hero_name (default: random)> (play the hero hello sound, you can also use zafig's retarded nicknames)\n" +
                        "!potg (yall know what that is)\n" +
                        "!owroast (play this when some1 got roasted and mr.negi generation 3000 is unavailable)\n");
                break;
        }

        return null;
    }
}
