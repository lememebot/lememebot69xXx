package io.lememebot.extras.overwatch;

import java.util.ArrayList;

/**
 * Created by guy on 12/03/17.
 * Overwatch (<3) hero class
 */
public class OverwatchHero {

    private final static int NUM_HEROES=25;
    private final static ArrayList<OverwatchHero> s_heroes;

    private OverwatchHeroClass m_heroType;
    private String m_name;
    private String m_nickname; // Zafig autistic nicknames

    static {
        s_heroes = new ArrayList<>(NUM_HEROES);

        // Offensive
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"genji","ninja"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"mcree","cowboy"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"solider76","solider"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"tracer"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"reaper","death"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"sombra"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.OFFENSE,"pharah"));

        // Defense
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.DEFENSE,"hanzo","bowguy?")); // My Man
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.DEFENSE,"torbjorn"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.DEFENSE,"junkrat"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.DEFENSE,"windowmaker"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.DEFENSE,"mei","bitch"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.DEFENSE,"bastion","johntron"));

        // Tank
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.TANK,"dva","robot"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.TANK,"reinhardt","sheildguy"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.TANK,"roadhog","yoni")); // ayy
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.TANK,"winston","harambe")); // <3 we miss you
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.TANK,"zarya","lesbo"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.TANK,"orisa"));

        // Support
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.SUPPORT,"ana"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.SUPPORT,"lucio","negro"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.SUPPORT,"mercy","milf"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.SUPPORT,"symetra","indiantechsupport"));
        s_heroes.add(new OverwatchHero(OverwatchHeroClass.SUPPORT,"zenyatta","healrobot"));
    }

    private OverwatchHero(OverwatchHeroClass heroType,String heroName, String nickname)
    {
        m_heroType = heroType;
        m_name = heroName;
        m_nickname = nickname;
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

    private boolean equals(String name)
    {
        return (name.equals(getName()) || name.equals(getNickname()));
    }

    public static OverwatchHero getHero(String name)
    {
        for(OverwatchHero hero : s_heroes)
        {
            if(hero.equals(name))
            {
                return hero;
            }
        }

        return null;
    }
}
