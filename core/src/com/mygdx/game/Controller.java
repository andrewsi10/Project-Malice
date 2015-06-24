package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction.*;

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
    public static int[] CONTROLS = new int[]{ Input.Keys.W,
                                                             Input.Keys.D,
                                               Input.Keys.S,
                                  Input.Keys.A,
             };
    
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
            int dirY = -1;
            if ( Gdx.input.isKeyPressed( CONTROLS[0] ) )
                dirY = NORTH.getDirection();
            if ( Gdx.input.isKeyPressed( CONTROLS[2] ) )
                dirY = ( dirY == NORTH.getDirection() ) ? -1 : SOUTH.getDirection();

            int dirX = -1;
            if ( Gdx.input.isKeyPressed( CONTROLS[1] ) )
                dirX = EAST.getDirection();
            if ( Gdx.input.isKeyPressed( CONTROLS[3] ) )
                dirX = ( dirX == EAST.getDirection() ) ? -1 : WEST.getDirection();

            if ( dirY == -1 )
                return dirX;
            if ( dirX == -1 )
                return dirY;
            if ( dirY == NORTH.getDirection() && dirX == WEST.getDirection() )
                return NORTHWEST.getDirection();
            return ( dirY + dirX ) / 2;
        }
    }

    // TODO get following 2 methods to change DIRECTION according to what getInputDirection() returns
    // note: has been attempted once but contained too many issues after compression of code
    @Override
    public boolean keyDown( int keycode ) { return false; }
    
    @Override
    public boolean keyUp( int keycode ) { return false; }

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
