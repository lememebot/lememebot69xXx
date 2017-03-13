package io.lememebot.media;

import net.dv8tion.jda.core.entities.User;

/**
 * Created by guy on 12/03/17.
 * Audio request to be analyzed by async worker later
 */

public class MediaRequest {
    private final MediaDescriptor m_descriptor;
    private final User m_invoker;

    public MediaRequest(MediaDescriptor mediaDescriptor, User user)
    {
        m_descriptor = mediaDescriptor;
        m_invoker = user;
    }

    public MediaDescriptor getMediaDescriptor()
    {
        return m_descriptor;
    }

    public User getInvoker()
    {
        return m_invoker;
    }
}
