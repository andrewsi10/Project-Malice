package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction.EAST;
import static com.mygdx.game.player.AnimatedSprite.Direction.NORTH;
import static com.mygdx.game.player.AnimatedSprite.Direction.NUMDEGREES;
import static com.mygdx.game.player.AnimatedSprite.Direction.SOUTH;
import static com.mygdx.game.player.AnimatedSprite.Direction.WEST;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DesktopController extends Controller
{

    /**
     * This array stores Key input values 0 , 1 , 2 , 3 NORTH, EAST, SOUTH, WEST
     */
    public static final int[] CONTROLS = new int[] { Input.Keys.W,
            Input.Keys.D, Input.Keys.S, Input.Keys.A, };
    
    private boolean[] isKeyPressed;
    
    public DesktopController( Malice g ) {
        super( g );
        reset();
    }

    /**
     * @see com.mygdx.game.Controller#getInputDirection()
     */
    @Override
    public double getInputDirection() 
    {
        int dirY = isKeyPressed[0] ? NORTH.getDirection() : -1;
        if ( isKeyPressed[2] )
            dirY = ( dirY == -1 ) ? SOUTH.getDirection() : -1;

        int dirX = isKeyPressed[1] ? EAST.getDirection() : -1;
        if ( isKeyPressed[3] )
            dirX = ( dirX == -1 ) ? WEST.getDirection() : -1;

        if ( dirY == -1 )
            return dirX;
        if ( dirX == -1 )
            return dirY;
        if ( dirY == NORTH.getDirection() && dirX == WEST.getDirection() )
            dirY = NUMDEGREES.getDirection();
        return ( dirY + dirX ) / 2;
    }

    /**
     * @see com.mygdx.game.Controller#getShootingDirection()
     */
    @Override
    public double getShootingDirection()
    {
        float deltaX = 0, deltaY = 0;
        if ( Gdx.input.isTouched() ) {
            deltaX = Gdx.input.getX() - Gdx.graphics.getWidth() / 2;
            deltaY = Gdx.graphics.getHeight() / 2 - Gdx.input.getY();
        }
        
        if ( deltaX != 0 && deltaY != 0 )
            return 90 + 360 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
        return -1;
    }

    /** @see com.badlogic.gdx.InputProcessor#keyDown(int) */
    @Override
    public boolean keyDown( int keycode )
    {
        if ( keycode == Input.Keys.ESCAPE )
            game.gameScreen.toggleGameState();
        for ( int i = 0; i < CONTROLS.length; i++ )
            if ( CONTROLS[i] == keycode )
                isKeyPressed[i] = true;
        return super.keyDown( keycode );
    }

    /** @see com.badlogic.gdx.InputProcessor#keyUp(int) */
    @Override
    public boolean keyUp( int keycode )
    {
        for ( int i = 0; i < CONTROLS.length; i++ )
            if ( CONTROLS[i] == keycode )
                isKeyPressed[i] = false;
        return super.keyUp( keycode );
    }

    @Override
    public void reset()
    {
        isKeyPressed = new boolean[CONTROLS.length];
    }
}
