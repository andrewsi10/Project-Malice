package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction;

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
    public static int getInputDirection() {
//        if ( Gdx.app.getType().equals( ApplicationType.Desktop ) )
        int dirY = -1;
        if ( Gdx.input.isKeyPressed( CONTROLS[0] ) )
            dirY = Direction.NORTH.getDirection();
        if ( Gdx.input.isKeyPressed( CONTROLS[2] ) )
            dirY = ( dirY == Direction.NORTH.getDirection() ) ? -1 : Direction.SOUTH.getDirection();
        
        int dirX = -1;
        if ( Gdx.input.isKeyPressed( CONTROLS[1] ) )
            dirX = Direction.EAST.getDirection();
        if ( Gdx.input.isKeyPressed( CONTROLS[3] ) )
            dirX = ( dirX == Direction.EAST.getDirection() ) ? -1 : Direction.WEST.getDirection();
        
        if ( dirY == -1 )
            return dirX;
        if ( dirX == -1 )
            return dirY;
        if ( dirY == Direction.NORTH.getDirection() && dirX == Direction.WEST.getDirection() )
            return Direction.NORTHWEST.getDirection();
        return ( dirY + dirX ) / 2;
    }

    @Override
    public boolean keyDown( int keycode )
    {
        return false;
    }

    @Override
    public boolean keyUp( int keycode )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped( char character )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved( int screenX, int screenY )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled( int amount )
    {
        // TODO Auto-generated method stub
        return false;
    }
}
