package io.lememebot.media;

import java.net.URL;
/**
 * Project: lememebot69xXx
 * Package: io.lememebot.media
 * Created by Guy on 12-Mar-17.
 * <p>
 * Description:
 */
public class MediaDescriptor {
    private String m_resourceName;
    private MediaSource m_source;

    public MediaDescriptor(String resourceName, MediaSource source) {
        m_resourceName = resourceName;
        m_source = source;
    }

    public MediaDescriptor(String resourceName) {
        this(resourceName, MediaSource.JAVA_RESOURCE);
    }

    public String getResourceName() {
        return m_resourceName;
    }

    public URL getURL()
    {
        return getClass().getResource(m_resourceName);
    }

    public MediaSource getAudioSource() {
        return m_source;
    }

    @Override
    public String toString()
    {
        return "[" + m_source + " ]" +  m_resourceName;
    }
}
