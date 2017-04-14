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
    private static BotAudioManager s_instance;

    private AudioPlayerManager m_playerManager;
    private AudioPlayer m_player;
    private AudioPlayerSendHandler m_handler;
    private DefaultTrackScheduler m_trackScheduler;
    private final Hashtable<String,AudioManager> audioManagersByGuild;
    private VoiceChannel currentVoiceChannel;

    static {
        s_instance = new BotAudioManager();
    }

    private BotAudioManager()
    {
        audioManagersByGuild = new Hashtable<>(3);
        currentVoiceChannel = null;
    }

    public static void init()
    {
        s_instance.initInstance();
    }

    public static void shutdown()
    {
        s_instance.destroyInstance();
    }

    public void initInstance()
    {
        m_playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(m_playerManager);

        m_player = m_playerManager.createPlayer();
        m_handler = new AudioPlayerSendHandler(m_player);
        m_trackScheduler = new DefaultTrackScheduler(m_player);
    }

    public void destroyInstance()
    {
        for(AudioManager audioManager : audioManagersByGuild.values())
        {
            audioManager.closeAudioConnection();
        }

        m_playerManager.shutdown();
    }

    public static void playAudio(Guild guild, MediaRequest mediaRequest)
    {
        s_instance.runPlayAudio(guild,mediaRequest);
    }

    public void runPlayAudio(Guild guild, MediaRequest mediaRequest) {
        if (!guild.getVoiceChannels().isEmpty()) {
            List<VoiceChannel> voiceChannelList = guild.getVoiceChannels();
            VoiceChannel userVoiceChannel = null;

            for (VoiceChannel voiceChannel : voiceChannelList) {
                for (Member member : voiceChannel.getMembers()) {
                    if (member.getUser() == mediaRequest.getInvoker()) {
                        userVoiceChannel = voiceChannel;
                        break;
                    }
                }
            }

            if (null != userVoiceChannel) {
                log.debug("{} is in channel (Guild: {})", mediaRequest.getInvoker().getName(), userVoiceChannel.getName(),guild.getId());

                // Create an audio manager for the GUILD if it does not exist in the hashtable
                AudioManager audioManager;
                if(!audioManagersByGuild.containsKey(guild.getId())) {
                    audioManager = guild.getAudioManager();
                    audioManager.openAudioConnection(userVoiceChannel);
                    audioManager.setSendingHandler(m_handler);
                    audioManager.setConnectionListener(this);

                    currentVoiceChannel = userVoiceChannel;
                    audioManagersByGuild.put(guild.getId(),audioManager);
                } else
                {
                    audioManager = audioManagersByGuild.get(guild.getId());
                    if(null == currentVoiceChannel || userVoiceChannel != currentVoiceChannel)
                    {
                        audioManager.closeAudioConnection();
                        audioManager.openAudioConnection(userVoiceChannel);
                        currentVoiceChannel = userVoiceChannel;
                    }
                }

                String trackURL = mediaRequest.getMediaDescriptor().getURL().getFile();
                log.info("Loading file {}",trackURL);
                m_playerManager.loadItem(trackURL, m_trackScheduler);
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
