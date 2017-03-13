package io.lememebot.handlers;

import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by guy on 12/03/17.
 *
 * Description:
 * This one is my favorite, speacially tailored for our beloved
 * dean, who sometimes wont shut the fuck up and posts a 10m rows rant
 */
public class HoferHandler extends IBaseHandler {
    private static final int s_my_limit = 4;
    int m_messageCount;
    int m_numRoasts;
    User m_lastSenderUser;

    public HoferHandler()
    {
        super("");
        Init(null);
    }

    private void Init(User currentSender)
    {
        m_lastSenderUser = currentSender;
        m_messageCount = 0;
        m_numRoasts = 0;
    }

    @Override
    public MediaRequest onMessage(Command cmd)
    {
        User currentSender = getEvent().getAuthor();

        if(null == m_lastSenderUser || currentSender != m_lastSenderUser) {
            Init(currentSender);
        }
        else {
            m_messageCount++;
        }

        if(m_messageCount > s_my_limit) {
            if(m_numRoasts < 1)
            sendMessage("OMG " + currentSender.getAsMention() + " will you shut the fuck up already?!");
            else if (m_numRoasts % 3 == 0) {
                sendMessage("For the love of chinchin just shut up" + currentSender.getAsMention());
            }
            else {
                sendMessage("Shut up " + currentSender.getAsMention());
            }

            m_numRoasts++;
        }

        return null;
    }
}
