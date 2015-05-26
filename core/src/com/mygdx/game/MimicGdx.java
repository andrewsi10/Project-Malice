package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import static com.mygdx.game.player.Character.*;

public class MimicGdx
{
    /**
     * 0    , 1   , 2    , 3   , 4
     * NORTH, EAST, SOUTH, WEST, Attack
     */
    public static int[] CONTROLS = new int[]{ Input.Keys.W,
                                                             Input.Keys.D,
                                               Input.Keys.S,
                                  Input.Keys.A,
                Input.Buttons.LEFT
             };
    
    // -------------------------- Player Controls --------------------- //
    
    /**
     * Returns direction to go based on key input
     * (Used by Player class)
     * @return direction or -1 if no direction
     */
    public static int getInputDirection() {
        int dirY = -1;
        if ( isKeyPressed( CONTROLS[0] ) )
            dirY = NORTH;
        if ( isKeyPressed( CONTROLS[2] ) )
            dirY = ( dirY == NORTH ) ? -1 : SOUTH;
        
        int dirX = -1;
        if ( isKeyPressed( CONTROLS[1] ) )
            dirX = EAST;
        if ( isKeyPressed( CONTROLS[2] ) )
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

    public static boolean isTesting = false;
    
    // ------------------- Input of Keyboard and Mouse ------------------- //
    
    public static int[] testKeys;
    public static int[] testBtns;
    public static int mouseX;
    public static int mouseY;
    
    public static boolean isKeyJustPressed( int key )
    {
        return isTesting ? isKeyPressed( key ) : Gdx.input.isKeyJustPressed( key );
    }
    
    public static boolean isKeyPressed( int key )
    {
        return isTesting ? inArray(key,testKeys) : Gdx.input.isKeyPressed(key);
    }
    
    public static boolean isButtonPressed( int button )
    {
        return isTesting ? inArray(button,testBtns) : Gdx.input.isButtonPressed( button );
    }
    
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
    // note: Music and Audio may have to be separated somehow, this class may not be able to do that
    
    
    // -------------------------- File Input -------------------------- //
    // note: File Input may have to be separated somehow, this class may not be able to do that
    
    
    
}
