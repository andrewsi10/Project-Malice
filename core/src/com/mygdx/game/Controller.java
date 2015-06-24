package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction.NORTH;
import static com.mygdx.game.player.AnimatedSprite.Direction.EAST;
import static com.mygdx.game.player.AnimatedSprite.Direction.SOUTH;
import static com.mygdx.game.player.AnimatedSprite.Direction.WEST;
import static com.mygdx.game.player.AnimatedSprite.Direction.NUMDEGREES;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Controller implements InputProcessor
{
    /**
     * This array stores Key input values
     * 0    , 1   , 2    , 3
     * NORTH, EAST, SOUTH, WEST
     */
    public static final int[] CONTROLS = new int[]{ Input.Keys.W,
                                                                 Input.Keys.D,
                                                    Input.Keys.S,
                                       Input.Keys.A,
             };
    public static final boolean[] PRESSED = new boolean[CONTROLS.length];
    
    public static float dir; // TODO android
    
    // -------------------------- Player Controls --------------------- //
    
    /**
     * Returns direction to go based on key input and an array that stores
     * input values
     * (Used by Player class)
     * @return direction or -1 if no direction
     */
    public static double getInputDirection() {
        if ( Gdx.app.getType().equals( ApplicationType.Android ) ) // TODO implement Android vs Desktop
        {
//            if ( deltaY == 0 && deltaX == 0 )
                return -1;
                // note: formula to convert from standard x,y grid to course navigation grid
//            return 90 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
        }
        else
        {
//            return (DIRECTION == -1 || DIRECTION == -2)? -1 : DIRECTIONS[DIRECTION].getDirection();
            int dirY = PRESSED[0] ? NORTH.getDirection() : -1;
            if ( PRESSED[2] )
                dirY = ( dirY == -1 ) ? SOUTH.getDirection() : -1;

            int dirX = PRESSED[1] ? EAST.getDirection() : -1;
            if ( PRESSED[3] )
                dirX = ( dirX == -1 ) ? WEST.getDirection() : -1;

            if ( dirY == -1 ) return dirX;
            if ( dirX == -1 ) return dirY;
            if ( dirY == NORTH.getDirection() && dirX == WEST.getDirection() )
                dirY = NUMDEGREES.getDirection();
            return ( dirY + dirX ) / 2;
        }
    }
    
    @Override
    public boolean keyDown( int keycode ) { 
        for ( int i = 0; i < CONTROLS.length; i++ )
            if ( CONTROLS[i] == keycode )
                PRESSED[i] = true;
        return true; 
    }
    
    @Override
    public boolean keyUp( int keycode ) { 
        for ( int i = 0; i < CONTROLS.length; i++ )
            if ( CONTROLS[i] == keycode )
                PRESSED[i] = false;
        return true; 
    }

    @Override
    public boolean keyTyped( char character ) { return false; }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button )
    {
        return false;
    }

    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button )
    {
        return false;
    }

    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer )
    {
        // TODO implement Android controls here
        return false;
    }

    @Override
    public boolean mouseMoved( int screenX, int screenY ) { return false; }

    @Override
    public boolean scrolled( int amount ) { return false; }
}
