package io.lememebot.handlers;

import io.lememebot.core.Command;
import io.lememebot.media.MediaRequest;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.handlers
 * Created by Guy on 19-Mar-17.
 * <p>
 * Description:
 */
public class TrumpTweetHandler extends IBaseHandler {

    final static int s_content_max_width = 550;

    public TrumpTweetHandler()
    {
            super("!");
    }

    @Override
    public void onMessage(MessageReceivedEvent event, Command cmd)
    {
        if(cmd.getCommand().equals("tweet")) {
            String strTweet = cmd.getParameter(1);
            if (strTweet.isEmpty()) {
                strTweet = getAuthor().getAsMention() + " is a fagget";
            }

            if (strTweet.length() > 140) {
                strTweet = strTweet.substring(0, 140);
            }

            log.debug("Tweeting {}", strTweet);
            sendMessage("Donald J.Trump is tweeting....");

            try {
                final BufferedImage imgBuffer = ImageIO.read(getClass().getResourceAsStream("/Trump/Tweet.png"));
                int textWidth;
                Graphics imgGraphics = imgBuffer.getGraphics();
                imgGraphics.setFont(imgGraphics.getFont().deriveFont(30f));

                do {
                    // Draw a line
                    textWidth = imgGraphics.getFontMetrics().stringWidth(strTweet);

                }
                while (textWidth > s_content_max_width);

                imgGraphics.drawString(strTweet, 15, 80);
                imgGraphics.dispose();
            } catch (IOException ex) {
                log.debug("Failed to read /Trump/Tweet.png from resource directory");
            }
        }
    }
}
