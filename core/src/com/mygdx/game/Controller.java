package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction.*;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.player.AnimatedSprite.Direction;

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
    
    public static final int LENGTH = DIRECTIONS.length - 1; // length not including NUMDEGREES
    public static int DIRECTION = -1;
    public static int XDIR;
    public static int YDIR;
    
    public static float dir; // TODO for android
    
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
    
    private void keyPressed( int dir )
    {
        dir *= 2; // translate direction from being based on 4 to be based on 8
        // compression
        if ( DIRECTION == -1 ) {
             DIRECTION = dir; return; }
        
        
        int num = ( DIRECTION - dir + LENGTH ) % LENGTH;
        switch ( num )
        {
            case 0:
                break;
            case 1:
                break;
            case 2:
            case 3:
                DIRECTION--;
                DIRECTION += LENGTH;
                DIRECTION %= LENGTH;
                break;
            case 4:
                DIRECTION = -2;
                break;
            case 5:
            case 6:
                DIRECTION++;
                DIRECTION %= LENGTH;
                break;
            case 7:
                break;
        }
//        if ( DIRECTION == -1 ) DIRECTION = dir;
//        else if ( num == LENGTH / 2 ) DIRECTION = -2;
//        else if ( num < LENGTH / 2 ) DIRECTION = ( num - 1 ) % LENGTH;
//        else if ( num > LENGTH / 2 ) DIRECTION = ( num + 1 ) % LENGTH;
        
        // second attempt
//        Direction d1 = null; // no current direction
//        Direction d2 = DIRECTIONS[( dir - 3 + LENGTH ) % LENGTH];
//        Direction d3 = DIRECTIONS[( dir - 2 + LENGTH ) % LENGTH];
//        Direction d4 = DIRECTIONS[( dir - 1 + LENGTH ) % LENGTH];
//        Direction d5 = DIRECTIONS[(dir + LENGTH / 2) % LENGTH];
//        Direction d6 = DIRECTIONS[( dir + 1 ) % LENGTH];
//        Direction d7 = DIRECTIONS[( dir + 2 ) % LENGTH];
//        Direction d8 = DIRECTIONS[( dir + 3 ) % LENGTH];
//        DIRECTION = ( DIRECTION == d1 ) ? DIRECTIONS[dir] :
//                    ( DIRECTION == d2 ) ? DIRECTIONS[( dir - 2 + LENGTH ) % LENGTH] :
//                    ( DIRECTION == d3 ) ? DIRECTIONS[( dir - 1 + LENGTH ) % LENGTH] :
//                    ( DIRECTION == d4 ) ? DIRECTION :
//                    ( DIRECTION == d5 ) ? NUMDEGREES : 
//                    ( DIRECTION == d6 ) ? DIRECTION :
//                    ( DIRECTION == d7 ) ? DIRECTIONS[( dir + 1 ) % LENGTH] :
//                    ( DIRECTION == d8 ) ? DIRECTIONS[( dir + 2 ) % LENGTH] : null;
       // first attempt: needs consideration of diagonals
//        if ( DIRECTION == null ) 
//            DIRECTION = DIRECTIONS[dir];
//        else if ( DIRECTION == DIRECTIONS[( dir - 2 + LENGTH ) % LENGTH] ) 
//            DIRECTION = DIRECTIONS[( dir - 1 + LENGTH ) % LENGTH];
//        else if ( DIRECTION == DIRECTIONS[(dir + LENGTH / 2) % LENGTH] ) 
//            DIRECTION = NUMDEGREES; // represent conflict
//        else if ( DIRECTION == DIRECTIONS[( dir + 2 ) % LENGTH] ) 
//            DIRECTION = DIRECTIONS[( dir + 1 ) % LENGTH];
    }

    // TODO get following 2 methods to change DIRECTION according to what getInputDirection() returns
    @Override
    public boolean keyDown( int keycode ) // incomplete
    {
//        for ( int i = 0; i < CONTROLS.length; i++ )
//        {
//            if ( keycode == CONTROLS[i] ) keyPressed( i );
//        }
//        return true;
        return false;
    }
    
    private void keyReleased( int dir )
    {
        dir *= 2; // translate direction from being based on 4 to be based on 8
        if ( DIRECTION == -2 ) {
            DIRECTION = (dir + LENGTH / 2) % LENGTH; return; }
       
       int num = ( DIRECTION - dir + LENGTH ) % LENGTH;
       switch ( num )
       {
           case 0:
               DIRECTION = -1;
               break;
           case 1:
           case 2:
               DIRECTION++;
               DIRECTION %= LENGTH;
               break;
           case 3:
               break;
           case 4:
               break;
           case 5:
               break;
           case 6:
           case 7:
               DIRECTION--;
               DIRECTION += LENGTH;
               DIRECTION %= LENGTH;
               break;
       }
       
//        int num = ( DIRECTION - dir + LENGTH ) % LENGTH;
//        if ( DIRECTION == -2 ) DIRECTION = (dir + LENGTH / 2) % LENGTH;
//        else if ( DIRECTION == dir ) DIRECTION = -1;
//        else if ( num < LENGTH / 2 ) DIRECTION = ( num + 1 ) % LENGTH;
//        else if ( num > LENGTH / 2 ) DIRECTION = ( num - 1 ) % LENGTH;
//        int index = DIRECTION.ordinal();
//        DIRECTION = DIRECTIONS[
//                               (index - dir < LENGTH / 2) ? index + 1 :
//                               (index - dir > LENGTH / 2) ? index - 1 :
//                               9];
//        if ( DIRECTION == DIRECTIONS[dir] ) 
//            DIRECTION = null;
//        else if ( DIRECTION == DIRECTIONS[( dir - 1 + LENGTH ) % LENGTH] ) 
//            DIRECTION = DIRECTIONS[( dir - 2 + LENGTH ) % LENGTH];
//        else if ( DIRECTION == NUMDEGREES ) // represent conflict
//            DIRECTION = DIRECTIONS[(dir + LENGTH / 2) % LENGTH]; 
//        else if ( DIRECTION == DIRECTIONS[(dir+1)%LENGTH] ) 
//            DIRECTION = DIRECTIONS[( dir + 2 ) % LENGTH];
    }

    @Override
    public boolean keyUp( int keycode )
    {
//        for ( int i = 0; i < CONTROLS.length; i++ )
//        {
//            if ( keycode == CONTROLS[i] ) keyReleased( i );
//        }
//        return true;
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
