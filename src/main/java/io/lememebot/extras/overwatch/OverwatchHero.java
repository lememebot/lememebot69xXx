package io.lememebot.extras.overwatch;

import io.lememebot.media.IMediaProvider;
import io.lememebot.media.MediaDescriptor;
import io.lememebot.media.MediaSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.security.provider.MD5;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

/**
 * Created by guy on 12/03/17.
 * Overwatch (<3) hero class
 */
public class OverwatchHero implements IMediaProvider {
    private final static Logger log = LogManager.getLogger();
    private final static int s_numHeroes;
    private final static Hashtable<Integer,OverwatchHero> s_heroes;
    private final static Random s_rnd;

    private OverwatchHeroClass m_heroType;
    private final String m_name;
    private final String m_nickname; // Zafig autistic nicknames
    private final String m_prettyName;
    private final String m_resDirectory;
    private final ArrayList<MediaDescriptor> m_resFiles;

    static {
        s_rnd = new Random();
        s_heroes = new Hashtable<Integer,OverwatchHero>(25);

        // Offensive
        s_heroes.put(1,new OverwatchHero(OverwatchHeroClass.OFFENSE,"genji","ninja"));
        s_heroes.put(2,new OverwatchHero(OverwatchHeroClass.OFFENSE,"mccree","cowboy"));
        s_heroes.put(3,new OverwatchHero(OverwatchHeroClass.OFFENSE,"solider76","solider"));
        s_heroes.put(4,new OverwatchHero(OverwatchHeroClass.OFFENSE,"tracer"));
        s_heroes.put(5,new OverwatchHero(OverwatchHeroClass.OFFENSE,"reaper","death"));
        s_heroes.put(6,new OverwatchHero(OverwatchHeroClass.OFFENSE,"sombra"));
        s_heroes.put(7,new OverwatchHero(OverwatchHeroClass.OFFENSE,"pharah"));

        // Defense
        s_heroes.put(8,new OverwatchHero(OverwatchHeroClass.DEFENSE,"hanzo","bowguy?")); // My Man
        s_heroes.put(9,new OverwatchHero(OverwatchHeroClass.DEFENSE,"torbjorn"));
        s_heroes.put(10,new OverwatchHero(OverwatchHeroClass.DEFENSE,"junkrat"));
        s_heroes.put(11,new OverwatchHero(OverwatchHeroClass.DEFENSE,"widowmaker","windowmaker"));
        s_heroes.put(12,new OverwatchHero(OverwatchHeroClass.DEFENSE,"mei","bitch"));
        s_heroes.put(13,new OverwatchHero(OverwatchHeroClass.DEFENSE,"bastion","johntron"));

        // Tank
        s_heroes.put(14,new OverwatchHero(OverwatchHeroClass.TANK,"dva","robot"));
        s_heroes.put(15,new OverwatchHero(OverwatchHeroClass.TANK,"reinhardt","sheildguy"));
        s_heroes.put(16,new OverwatchHero(OverwatchHeroClass.TANK,"roadhog","yoni")); // ayy
        s_heroes.put(17,new OverwatchHero(OverwatchHeroClass.TANK,"winston","harambe")); // <3 we miss you
        s_heroes.put(18,new OverwatchHero(OverwatchHeroClass.TANK,"zarya","lesbo"));
        s_heroes.put(19,new OverwatchHero(OverwatchHeroClass.TANK,"orisa"));

        // Support
        s_heroes.put(20,new OverwatchHero(OverwatchHeroClass.SUPPORT,"ana"));
        s_heroes.put(21,new OverwatchHero(OverwatchHeroClass.SUPPORT,"lucio","negro"));
        s_heroes.put(22,new OverwatchHero(OverwatchHeroClass.SUPPORT,"mercy","milf"));
        s_heroes.put(23,new OverwatchHero(OverwatchHeroClass.SUPPORT,"symetra","indiantechsupport"));
        s_heroes.put(24,new OverwatchHero(OverwatchHeroClass.SUPPORT,"zenyatta","healrobot"));

        s_numHeroes = s_heroes.size();
    }

    private OverwatchHero(OverwatchHeroClass heroType,String heroName, String nickname) {
        m_heroType = heroType;
        m_name = heroName;
        m_nickname = nickname;
        m_prettyName = heroName.toUpperCase().charAt(0) + heroName.toLowerCase().substring(1);
        m_resDirectory = "/Overwatch/" + m_prettyName + "/";
        m_resFiles = new ArrayList<>(2);
    }

    private OverwatchHero(OverwatchHeroClass heroType,String heroName)
    {
        this(heroType,heroName,heroName);
    }

    public String getName()
    {
        return m_name;
    }

    public String getNickname()
    {
        return m_nickname;
    }

    @Override
    public List<MediaDescriptor> getMediaDescriptorList() {

        // Lazy load the files
        if(m_resFiles.isEmpty())
        {
            try
            {
                InputStream inputStream = getClass().getResourceAsStream(m_resDirectory);
                if(null != inputStream) {
                    String audioFileName;
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    // Read the directory file list and add them to resource file list
                    while ((audioFileName = br.readLine()) != null) {
                        m_resFiles.add(new MediaDescriptor(m_resDirectory + audioFileName, MediaSource.JAVA_RESOURCE));
                    }

                    log.debug("Found {} files for {}", m_resFiles.size(), getName());
                } else {
                    log.debug("No files found for {} in directory {}", getName(),m_resDirectory);
                }
            }
            catch (java.io.IOException ex)
            {
                log.debug("getMediaDescriptorList for {} .IO Exception: {}",getName(),ex.getMessage());
                ex.printStackTrace();
            }
            catch (Exception ex)
            {
                log.debug("getMediaDescriptorList for {} . Exception: {}",getName(),ex.getMessage());
                ex.printStackTrace();
            }
        }

        return m_resFiles;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (other instanceof OverwatchHero) {
            OverwatchHero otherHero = (OverwatchHero)other;
            return (otherHero.getName().equals(getName()) || otherHero.getNickname().equals(getNickname()));
        }
        if(other instanceof String) {
            String heroName = (String)other;
            return (heroName.equals(getName()) || heroName.equals(getNickname()));
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return (31 * m_name.hashCode() + m_nickname.hashCode());
    }

    public static OverwatchHero getHero(String name)
    {
        for (OverwatchHero hero : s_heroes.values())
        {
            if(hero.equals(name))
                return hero;
        }

        return null;
    }

    public static OverwatchHero getRandomHero()
    {
        Integer index = (Math.abs(s_rnd.nextInt()) % s_numHeroes) + 1;

        return s_heroes.get(index);
    }
}
