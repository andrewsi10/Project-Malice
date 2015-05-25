package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import static com.mygdx.game.player.Character.*;

public class GdxInput
{
    public static boolean isTesting = false;
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
    public static int[] testKeys;
    public static int[] testBtns;
    
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
}
