package io.lememebot.core;

import io.lememebot.audio.BotAudioManager;
import io.lememebot.media.MediaRequest;
import io.lememebot.handlers.*;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;

/**
 * Project: lememebot69xXx
 * Package: io.github.lememebot.core
 * Created by Guy on 11-Mar-17.
 *
 * Description:
 */
public class BotServer extends ListenerAdapter {
    private final static Logger log = LogManager.getLogger();
    private JDA botInstance;
    private final ArrayList<IBaseHandler> eventsHandlers;
    private final BotAudioManager botAudioManager;
    private final ArrayList<MediaRequest> handlersMediaRequests;

    public BotServer() {
        eventsHandlers = new ArrayList<>(16);
        handlersMediaRequests = new ArrayList<>(4);
        botAudioManager = new BotAudioManager();

        eventsHandlers.add(new DebugHandler()); // used for command parse debugging
        eventsHandlers.add(new OverwatchHandler());
        eventsHandlers.add(new HoferHandler());
        eventsHandlers.add(new LastTimeHandler());
        eventsHandlers.add(new RemindMeHandler());
    }

    public boolean start() {
        try {

            botInstance = new JDABuilder(AccountType.BOT)
                    .setAudioEnabled(true)
                    .setAutoReconnect(true)
                    .setStatus(OnlineStatus.ONLINE)
                    .addListener(this)
                    .setToken("Mjg0NDEzOTUzNzU5NjQxNjEx.C6XbYg.rRsTdbJEfq--E-2Fp8zsh6u0ddU")
                    .buildBlocking();

            botAudioManager.Init();

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

    public void stop() {
        if (null != botAudioManager) {
            botAudioManager.shutdown();
        }

        if (null != botInstance) {
            botInstance.shutdown(false);
            botInstance = null;
        }
    }

    @Override
    public final void onMessageReceived(MessageReceivedEvent event) {
        // Don't hook other bot messages
        if (!event.getAuthor().isBot()) {
            handlersMediaRequests.clear();
            MediaRequest mediaRequest;

            for (IBaseHandler eventHandler : eventsHandlers) {
                if (eventHandler.getCommand().parse(event.getMessage().getContent())) {
                    eventHandler.setEvent(event);
                    mediaRequest = eventHandler.onMessage(eventHandler.getCommand());

                    if (null != mediaRequest) {
                        log.debug("request {} from {}", mediaRequest.getMediaDescriptor().toString(), mediaRequest.getInvoker().getName());

                        handlersMediaRequests.add(mediaRequest);
                    }
                }
            }

            for (MediaRequest handlerRequest : handlersMediaRequests) {
                botAudioManager.playAudio(event.getGuild(), handlerRequest);
            }
        }
    }

    public boolean isOnline() {
        return null != botInstance && (botInstance.getStatus().equals(JDA.Status.CONNECTED));
    }
}
