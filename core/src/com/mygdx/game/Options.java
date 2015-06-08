package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import static com.mygdx.game.player.Character.*;

/**
 *
 *  @author  Nathan Lui
 *  @version May 26, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice
 */
public class Options
{
    /**
     * This array stores Key input values
     * 0    , 1   , 2    , 3   , 4
     * NORTH, EAST, SOUTH, WEST, Attack
     */
    public static int[] CONTROLS = new int[]{ Input.Keys.W,
                                                             Input.Keys.D,
                                               Input.Keys.S,
                                  Input.Keys.A,
                Input.Buttons.LEFT
             };

    public static final String[] spriteNames = { "BlackMage", "Monk", "RedMage", "Thief",
        "Warrior", "WhiteMage" };
    public static final String[] projectileNames = { "DarkFire", "Boomerang", "Fireball",
        "PoisonShot", "Sword1", "HolyCross" };
    
    public static void initialize()
    {
        Audio.initializeAudio();
    }
    
    // -------------------------- Player Controls --------------------- //
    
    /**
     * Returns direction to go based on key input and an array that stores
     * input values
     * (Used by Player class)
     * @return direction or -1 if no direction
     */
    public static int getInputDirection() {
        int dirY = -1;
        if ( Gdx.input.isKeyPressed( CONTROLS[0] ) )
            dirY = NORTH;
        if ( Gdx.input.isKeyPressed( CONTROLS[2] ) )
            dirY = ( dirY == NORTH ) ? -1 : SOUTH;
        
        int dirX = -1;
        if ( Gdx.input.isKeyPressed( CONTROLS[1] ) )
            dirX = EAST;
        if ( Gdx.input.isKeyPressed( CONTROLS[3] ) )
            dirX = ( dirX == EAST ) ? -1 : WEST;
        
        if ( dirY == -1 )
            return dirX;
        if ( dirX == -1 )
            return dirY;
        if ( dirY == NORTH && dirX == WEST )
            return NORTHWEST;
        return ( dirY + dirX ) / 2;
    }
    
    /*** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * note:ALL METHODS BELOW ARE FOR TESTING AND ISOLATION OF THE GDX CLASS *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // -------------------------- Music and Audio --------------------- //
    public static class Audio {
        public static boolean MUTED = false; // not implemented into the game, provides ability to mute once all audio is isolated

        public static Music mainTheme;
        public static HashMap<String,Sound> SOUNDS;

        /**
         * Initializes all the audio
         */
        private static void initializeAudio()
        {
            mainTheme = Gdx.audio.newMusic( Gdx.files.internal( "audio/music/revivedpower.mp3" ) );
            SOUNDS = new HashMap<String, Sound>();
            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
            for ( String s : projectileNames )
                SOUNDS.put( s, Gdx.audio.newSound( Gdx.files.internal( "audio/sound/" + s.toLowerCase() + ".wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
        }
        
        /**
         * Stops the theme music from playing
         * @param type method of stopping:
         *          0 - pauses playing
         *          1 - stops playing (returns theme to the beginning)
         */
        public static void stopTheme( int type )
        {
            if ( mainTheme.isPlaying() )
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
            if ( !MUTED )
                mainTheme.play();
        }

        /**
         * Plays a sound based on a given string (relates to the file name)
         * @param s string related to the file name of the sound to play
         */
        public static void playAudio( String s )
        {
            if ( !MUTED && SOUNDS.get( s ) != null )
                SOUNDS.get( s ).play();
        }
    }
    
    // -------------------------- File Input -------------------------- //
    // note: File Input may have to be separated somehow through constructors, 
    //       this class may not be able to do that
    
}
