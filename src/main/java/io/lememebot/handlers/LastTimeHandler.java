package io.lememebot.handlers;

import io.lememebot.media.MediaRequest;
import io.lememebot.core.Command;

/**
 * Created by guy on 12/03/17.
 *
 */
public class LastTimeHandler extends IBaseHandler {

    public LastTimeHandler()
    {
        super("!");
    }

    @Override
    public MediaRequest onMessage(Command cmd)
    {
        switch(cmd.getCommand())
        {
            case "last":
                // TODO: connect to db
                String action = cmd.getParameter(1);
                if(!action.isEmpty())
                {
                    // get time since last action like 'dean_played_ow' or 'shekem'

                }

                break;
        }

        return null;
    }
}
