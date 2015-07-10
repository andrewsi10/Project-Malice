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
    public static boolean SOUND_MUTED = false; // not implemented into the game, provides ability to mute once all audio is isolated
    public static boolean MUSIC_MUTED = false;

    public static Music mainTheme;
    public static HashMap<String,Sound> SOUNDS;
    
    public static double VOLUME = 1.0; // TODO
    public static int VOLUME_PERCENT = 100;

    /**
     * Initializes all the audio
     */
    public static void initializeAudio()
    {
        mainTheme = Gdx.audio.newMusic( Gdx.files.internal( "audio/music/revivedpower.mp3" ) );
        SOUNDS = new HashMap<String, Sound>();
        SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
        for ( Name n : Options.NAMES )
            SOUNDS.put( n.getProjectileName(), Gdx.audio.newSound( Gdx.files.internal( "audio/sound/" + n.getProjectileName().toLowerCase() + ".wav" ) ) );
//        SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//        SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//        SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
    }

    /**
     * Stops the theme music from playing
     * @param type method of stopping:
     *          0 - pauses playing
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
        if ( mainTheme == null )
            initializeAudio();
        mainTheme.setLooping( true );
        mainTheme.setVolume( volume );
        if ( !MUSIC_MUTED )
            mainTheme.play();
        else
            stopTheme( 1 );
    }

    /**
     * Plays a sound based on a given string (relates to the file name)
     * @param s string related to the file name of the sound to play
     */
    public static void playAudio( String s )
    {
        if ( !SOUND_MUTED && SOUNDS.containsKey( s ) )
            SOUNDS.get( s ).play();
    }
}
