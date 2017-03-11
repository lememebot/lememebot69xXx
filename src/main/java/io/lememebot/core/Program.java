package io.lememebot.core;

import io.lememebot.ui.MainWindow;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Project: lememebot69xXx
 * Package: io.github.lememebot.core
 * Created by Guy on 24-Feb-17.
 *
 * Description:
 * https://discordapp.com/api/oauth2/authorize?client_id=284413953759641611&scope=bot&permissions=0
 */

public class Program {

    public static void main(String args[]) {
        try {
            MainWindow window= new MainWindow()
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogManager.shutdown();
    }
}
