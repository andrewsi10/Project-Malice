package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Controller implements InputProcessor
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
    
    public static float DIRECTION;
    
    // -------------------------- Player Controls --------------------- //
    
    /**
     * Returns direction to go based on key input and an array that stores
     * input values
     * (Used by Player class)
     * @return direction or -1 if no direction
     */
    public static double getInputDirection() {
//        if ( Gdx.app.getType().equals( ApplicationType.Desktop ) ) // TODO implement Android vs Desktop
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

    // TODO get following 2 methods to change DIRECTION according to what getInputDirection() returns
    @Override
    public boolean keyDown( int keycode ) // incomplete
    {
        if ( keycode == CONTROLS[0] )
        {
            DIRECTION = NORTH.getDirection();
        }
        if ( keycode == CONTROLS[1] )
        {
            DIRECTION = EAST.getDirection();
        }
        if ( keycode == CONTROLS[2] )
        {
            DIRECTION = SOUTH.getDirection();
        }
        if ( keycode == CONTROLS[3] )
        {
            DIRECTION = WEST.getDirection();
        }
        
        return false;
    }

    @Override
    public boolean keyUp( int keycode )
    {
        return false;
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
