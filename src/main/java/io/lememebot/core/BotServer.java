package io.lememebot.core;

import io.lememebot.handlers.*;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: lememebot69xXx
 * Package: io.github.lememebot.core
 * Created by Guy on 11-Mar-17.
 *
 * Description:
 */
public class BotServer {

    private JDA botInstance;
    private ArrayList<IBaseHandler> eventsHandlers;

    public BotServer() {
        eventsHandlers = new ArrayList<>(16);

        eventsHandlers.add(new DebugHandler());
        eventsHandlers.add(new OverwatchHandler());
    }

    public boolean start() {
        try {

            botInstance = new JDABuilder(AccountType.BOT)
                    .setAudioEnabled(true)
                    .setAutoReconnect(true)
                    .setStatus(OnlineStatus.ONLINE)
                    .setToken("Mjg0NDEzOTUzNzU5NjQxNjEx.C6XbYg.rRsTdbJEfq--E-2Fp8zsh6u0ddU")
                    .buildBlocking();

            // Register all handlers
            for (IBaseHandler eventHandler : eventsHandlers) {
                botInstance.addEventListener(eventHandler);
            }

            return true;
        } catch (LoginException e) {
            System.out.println("WTF is LoginException, hehe fuck that shit\nwait thats not that good");
        } catch (InterruptedException e) {
            System.out.println("WTF is InterruptedException, hehe fuck that shit");
        } catch (RateLimitedException e) {
            System.out.println("WTF is RateLimitedException, hehe fuck that shit");
        }
        // Init failed
        return false;
    }

    public void shutdown() {
        botInstance.shutdown();
        botInstance = null;
    }

    public boolean isOnline() {
        return null != botInstance && (botInstance.getStatus().equals(JDA.Status.CONNECTED));
    }
}
