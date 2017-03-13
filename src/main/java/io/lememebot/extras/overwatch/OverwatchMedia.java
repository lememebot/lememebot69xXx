package io.lememebot.extras.overwatch;

import io.lememebot.media.MediaDescriptor;
import io.lememebot.media.MediaSource;

import java.util.Hashtable;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.extras.overwatch
 * Created by Guy on 13-Mar-17.
 * <p>
 * Description:
 */
public class OverwatchMedia {
    private final static Hashtable<String,MediaDescriptor> s_resFiles;

    static
    {
        s_resFiles = new Hashtable<>(16);

        s_resFiles.put("potg",new MediaDescriptor("/Overwatch/Announcer/POTG.mp3", MediaSource.JAVA_RESOURCE));
        s_resFiles.put("victory",new MediaDescriptor("/Overwatch/Announcer/Victory.mp3", MediaSource.JAVA_RESOURCE));
        s_resFiles.put("defeat",new MediaDescriptor("/Overwatch/Announcer/Defeat.mp3", MediaSource.JAVA_RESOURCE));
    }

    public static MediaDescriptor getSound(String name)
    {
        return s_resFiles.getOrDefault(name,MediaDescriptor.EMPTY);
    }
}
