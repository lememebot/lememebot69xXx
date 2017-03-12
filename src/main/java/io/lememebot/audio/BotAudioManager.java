package io.lememebot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lememebot.media.MediaRequest;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.audio
 * Created by Guy on 12-Mar-17.
 * <p>
 * Description:
 */
public class BotAudioManager {
    private final static Logger log = LogManager.getLogger();
    AudioPlayerManager playerManager;
    AudioPlayer player;
    AudioPlayerSendHandler handler;
    TrackScheduler trackScheduler;

    public BotAudioManager()
    {
    }

    public void Init()
    {
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(playerManager);

        player = playerManager.createPlayer();
        handler = new AudioPlayerSendHandler(player);
        trackScheduler = new TrackScheduler(player);
    }

    public void shutdown()
    {
        playerManager.shutdown();
    }

    public void playAudio(Guild guild, MediaRequest mediaRequest) {
        if (!guild.getVoiceChannels().isEmpty()) {
            List<VoiceChannel> voiceChannelList = guild.getVoiceChannels();
            VoiceChannel chosenChannel = null;

            for (VoiceChannel voiceChannel : voiceChannelList) {
                for (Member member : voiceChannel.getMembers()) {
                    if (member.getUser() == mediaRequest.getInvoker()) {
                        chosenChannel = voiceChannel;
                        break;
                    }
                }
            }

            if (null != chosenChannel) {
                log.debug("{} is in channel ", mediaRequest.getInvoker().getName(), chosenChannel.getName());
                AudioManager audioManager = guild.getAudioManager();
                audioManager.openAudioConnection(chosenChannel);
                audioManager.setSendingHandler(handler);

                String trackURL = mediaRequest.getMediaDescriptor().getURL().getFile();
                log.info("Loading file {}",trackURL);

                playerManager.loadItem(trackURL, new AudioLoadResultHandler() {
                            @Override
                            public void trackLoaded(AudioTrack track) {
                                //trackScheduler.queue(track);
                                log.info("Playing track {}" ,track.getIdentifier());
                                trackScheduler.queue(track);
                            }

                            @Override
                            public void playlistLoaded(AudioPlaylist playlist) {
                                for (AudioTrack track : playlist.getTracks()) {
                                    log.info("Playing track {}" ,track.getIdentifier());
                                    trackScheduler.queue(track);
                                }
                            }

                            @Override
                            public void noMatches() {
                                // Notify the user that we've got nothing
                                log.info("Could not find this shitty sound file");
                            }

                            @Override
                            public void loadFailed(FriendlyException throwable) {
                                // Notify the user that everything exploded
                                log.info("Cant play that shit dog");
                            }
                });

                //audioManager.closeAudioConnection();
            } else {
                log.info("User is not in voice channel");
            }
        }
    }
}
