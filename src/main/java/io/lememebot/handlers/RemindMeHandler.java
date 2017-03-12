package io.lememebot.handlers;

import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;

/**
 * Created by guy on 12/03/17.
 * RemindMe feature for yoni (:* have fun bby make papa proud)
 */
public class RemindMeHandler extends IBaseHandler {
    public RemindMeHandler()
    {
        super("!",1);
    }

    @Override
    public MediaRequest onMessage(Command cmd)
    {
        switch(cmd.getCommand().toLowerCase())
        {
            case "remindme":
                /* TODO: yoni its yours, i recommend sqllite
                * its a database layer on a file, which we can backup to dropbox or even github
                */
                break;
        }

        return null;
    }
}
