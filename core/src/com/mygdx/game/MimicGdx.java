package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 *  The MimicGdx Class isolates nearly all input from the Gdx class
 *  
 *  This will allow 2 purposes:
 *   - provides the functionality of an options screen when implemented
 *   - Allows for better JUnit testing as many methods would be unable to be 
 *     tested, commented methods can simulate input for testing if implemented
 *
 *  @author  Nathan Lui
 *  @version May 26, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice
 */
public class MimicGdx
{
    
    /*** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * note:ALL METHODS BELOW ARE FOR TESTING AND ISOLATION OF THE GDX CLASS *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * When this class is used for testing, 
     */
    public static boolean isTesting = false;
    
    // ------------------- Input of Keyboard and Mouse ------------------- //
    
    /**
     * Array full of test keys to simulate input -- currently not used
     */
    public static int[] testKeys;
    public static int[] testBtns;
    public static int mouseX;
    public static int mouseY;
    
    public static boolean isKeyJustPressed( int key )
    {
        return isTesting ? isKeyPressed( key ) : Gdx.input.isKeyJustPressed( key );
    }
    
    /**
     * Returns whether the given key is pressed
     * @param key key to check for
     * @return whether the key is pressed
     */
    public static boolean isKeyPressed( int key )
    {
        return isTesting ? inArray(key,testKeys) : Gdx.input.isKeyPressed(key);
    }
    
    public static boolean isButtonPressed( int button )
    {
        return isTesting ? inArray(button,testBtns) : Gdx.input.isButtonPressed( button );
    }
    
    /**
     * Returns whether an int is in the int array
     * @param i int to check for
     * @param a int array
     * @return whether an int is in the int array
     */
    private static boolean inArray( int i, int[] a )
    {
        if ( a != null )
            for ( int j : a )
                if ( j == i ) return true;
        return false;
    }
    
    public static int getX()
    {
        return isTesting ? mouseX : Gdx.input.getX();
    }
    
    public static int getY()
    {
        return isTesting ? mouseY : Gdx.input.getY();
    }
    
    public static void setMousePosition( int x, int y )
    {
        mouseX = x;
        mouseY = y;
    }
    
    // -------------------------- Graphics ---------------------------- //
    
    public static int getWidth()
    {
        return isTesting ? 1280 : Gdx.graphics.getWidth();
    }
    
    public static int getHeight()
    {
        return isTesting ? 720 : Gdx.graphics.getHeight();
    }
    
    // -------------------------- Music and Audio --------------------- //
    public static boolean MUTED = false; // not implemented into the game, provides ability to mute once all audio is isolated
    
    public static Sound levelUp;
//    public static Music mainTheme;
    
    public static void initializeAudio()
    {
        levelUp = Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) );
//        mainTheme = Gdx.audio.newMusic( Gdx.files.internal( "audio/music/revivedpower.mp3" ) );
    }
    
    public static void playAudio( Sound sound )
    {
        if ( !MUTED && sound != null )
        {
            sound.play();
        }
    }
    
    // -------------------------- File Input -------------------------- //
    // note: File Input may have to be separated somehow through constructors, 
    //       this class may not be able to do that
    
}
