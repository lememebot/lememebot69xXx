package io.lememebot.audio;

/**
 * Created by guy on 12/03/17.
 * Audio request to be analyzed by async worker later
 */

public class AudioRequest {
    private String m_resourceName;
    private AudioSource m_source;

    public AudioRequest(String resourceName,AudioSource source)
    {
        m_resourceName = resourceName;
        m_source = source;
    }

    public AudioRequest(String resourceName) {
        this(resourceName,AudioSource.JAVA_RESOURCE);
    }

    public String getResourceName()
    {
        return m_resourceName;
    }

    public AudioSource getAudioSource()
    {
        return m_source;
    }
}
