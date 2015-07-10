package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Options.Name;

/**
 * This class supports all the Audio in the game
 * 
 * note: This class below can be taken out of the Options class and into a 
 * separate class for organization
 *
 *  @author  Nathan Lui
 *  @version Jun 29, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice-core
 *
 *  @author  Sources:
 */
public class Audio
{

    // -------------------------- Music and Audio --------------------- //

    public static Music mainTheme;
    public static HashMap<String,Sound> SOUNDS;
    
    public static int MUSIC_VOLUME = 100;
    public static int SOUND_VOLUME = 100; // TODO
    
    public static int MUSIC_PERCENT = 100;

    /**
     * Initializes all the audio
     */
    public static void initializeAudio()
    {
        mainTheme = Gdx.audio.newMusic( Gdx.files.internal( "audio/music/revivedpower.mp3" ) );
        mainTheme.setLooping( true );
        
        SOUNDS = new HashMap<String, Sound>();
        SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
        for ( Name n : Options.NAMES )
            SOUNDS.put( n.getProjectileName(), Gdx.audio.newSound( Gdx.files.internal( "audio/sound/" + n.getProjectileName().toLowerCase() + ".wav" ) ) );
        // add more sounds here
    }

    /**
     * Stops the theme music from playing
     * @param type method of stopping:
     *          0 - pauses playing;
     *          1 - stops playing (returns theme to the beginning)
     */
    public static void stopTheme( int type )
    {
        if ( mainTheme.isPlaying() ) // may be able to remove
            switch ( type )
            {
                case 0:
                    mainTheme.pause();
                    break;
                case 1:
                    mainTheme.stop();
                    break;
            }
    }

    /**
     * Plays the theme music
     * @param volume volume to set the theme music to
     */
    public static void playTheme( float volume )
    {
        MUSIC_PERCENT = (int)( volume * 100 );
        playTheme();
    }

    /**
     * Plays the theme music
     * @param volume volume to set the theme music to
     */
    public static void playTheme()
    {
        if ( mainTheme == null )
            initializeAudio();
        mainTheme.setVolume( MUSIC_VOLUME / 100.0f * MUSIC_PERCENT / 100 );
        if ( !isMusicMuted() )
            mainTheme.play();
        else
            stopTheme( 0 );
    }

    /**
     * Plays a sound based on a given string (relates to the file name)
     * @param s string related to the file name of the sound to play
     */
    public static void playAudio( String s )
    {
        if ( !isSoundMuted() && SOUNDS.containsKey( s ) )
            SOUNDS.get( s ).play( SOUND_VOLUME / 100.0f );
    }
    
    public static boolean isMusicMuted()
    {
        return MUSIC_VOLUME == 0;
    }
    
    public static boolean isSoundMuted()
    {
        return SOUND_VOLUME == 0;
    }
}
