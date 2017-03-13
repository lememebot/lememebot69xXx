package io.lememebot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import io.lememebot.media.MediaRequest;
import net.dv8tion.jda.core.audio.hooks.ConnectionListener;
import net.dv8tion.jda.core.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import java.util.List;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.audio
 * Created by Guy on 12-Mar-17.
 * <p>
 * Description:
 */
public class BotAudioManager implements ConnectionListener {
    private final static Logger log = LogManager.getLogger();
    private AudioPlayerManager m_playerManager;
    private AudioPlayer m_player;
    private AudioPlayerSendHandler m_handler;
    private DefaultTrackScheduler m_trackScheduler;
    private final Hashtable<String,AudioManager> audioManagersByGuild;

    public BotAudioManager()
    {
        audioManagersByGuild = new Hashtable<>(3);
    }

    public void Init()
    {
        m_playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(m_playerManager);

        m_player = m_playerManager.createPlayer();
        m_handler = new AudioPlayerSendHandler(m_player);
        m_trackScheduler = new DefaultTrackScheduler(m_player);
    }

    public void shutdown()
    {
        for(AudioManager audioManager : audioManagersByGuild.values())
        {
            audioManager.closeAudioConnection();
        }

        m_playerManager.shutdown();
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
                log.debug("{} is in channel (Guild: {})", mediaRequest.getInvoker().getName(), chosenChannel.getName(),guild.getId());

                // Create an audio manager for the GUILD if it does not exist in the hashtable
                if(!audioManagersByGuild.containsKey(guild.getId())) {
                    AudioManager audioManager = guild.getAudioManager();
                    audioManager.openAudioConnection(chosenChannel);
                    audioManager.setSendingHandler(m_handler);
                    audioManager.setConnectionListener(this);
                    audioManagersByGuild.put(guild.getId(),audioManager);
                }

                String trackURL = mediaRequest.getMediaDescriptor().getURL().getFile();
                log.info("Loading file {}",trackURL);

                m_playerManager.loadItem(trackURL, m_trackScheduler);

                //audioManager.closeAudioConnection();
            } else {
                log.info("User is not in voice channel");
            }
        }
    }

    public void stopAudio()
    {
        m_player.stopTrack();
    }

    @Override
    public void onPing(long l) {

    }

    @Override
    public void onStatusChange(ConnectionStatus connectionStatus) {
        if (ConnectionStatus.CONNECTED == connectionStatus ||
                ConnectionStatus.NOT_CONNECTED == connectionStatus)
            log.debug("[AudioManager] Status changed to {}", connectionStatus.toString());
    }

    @Override
    public void onUserSpeaking(User user, boolean b) {

    }
}
