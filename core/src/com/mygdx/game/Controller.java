package com.mygdx.game;

import static com.mygdx.game.player.AnimatedSprite.Direction.NORTH;
import static com.mygdx.game.player.AnimatedSprite.Direction.EAST;
import static com.mygdx.game.player.AnimatedSprite.Direction.SOUTH;
import static com.mygdx.game.player.AnimatedSprite.Direction.WEST;
import static com.mygdx.game.player.AnimatedSprite.Direction.NUMDEGREES;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Controller extends Stage
{
    public static final boolean isAndroid = Gdx.app.getType().equals( ApplicationType.Android );
    
    private static Touchpad movementTouchpad;
    private static Touchpad shootTouchpad;

    /**
     * This array stores Key input values 0 , 1 , 2 , 3 NORTH, EAST, SOUTH, WEST
     */
    public static final int[] CONTROLS = new int[] { Input.Keys.W,
            Input.Keys.D, Input.Keys.S, Input.Keys.A, };
    public static boolean[] PRESSED = new boolean[CONTROLS.length];
    
    public Controller()
    {
        if ( isAndroid ) {
            // Create a touchpad skin
            Skin touchpadSkin = Options.SKIN;
            // Create TouchPad Style
            TouchpadStyle touchpadStyle = new TouchpadStyle();
            // Create Drawable's from TouchPad skin
            Drawable touchBackground = touchpadSkin.getDrawable( "touchBackground" );
            Drawable touchKnob = touchpadSkin.getDrawable( "touchKnob" );
            // Apply the Drawables to the TouchPad Style
            touchpadStyle.background = touchBackground;
            touchpadStyle.knob = touchKnob;
            // Create new TouchPad with the created style
            movementTouchpad = new Touchpad( 10, touchpadStyle );
            // setBounds(x,y,width,height)
            movementTouchpad.setBounds( 30, 30, 300, 300 );
            addActor( movementTouchpad );
            
            shootTouchpad = new Touchpad( 10, touchpadStyle );
            shootTouchpad.setBounds( getWidth() - 330, 30, 300, 300 );
            addActor( shootTouchpad );
        }
    }

    // -------------------------- Player Controls --------------------- //

    /**
     * Returns direction to go based on key input and an array that stores input
     * values (Used by Player class)
     * 
     * @return direction or -1 if no direction
     */
    public static double getInputDirection()
    {
        if ( isAndroid ) {
            if ( movementTouchpad.isTouched() ) {
                // this return statement converts to navigation coordinates with ( 90 - degrees )
                // then it returns a positive number with ( + 360 )
                return 90 + 360 - Math.toDegrees( 
                                    Math.atan2( movementTouchpad.getKnobPercentY(), 
                                                movementTouchpad.getKnobPercentX() ) );
            }
            return -1;
        }

        // else // if ( Gdx.app.getType().equals( ApplicationType.Desktop ) )
        int dirY = PRESSED[0] ? NORTH.getDirection() : -1;
        if ( PRESSED[2] )
            dirY = ( dirY == -1 ) ? SOUTH.getDirection() : -1;

        int dirX = PRESSED[1] ? EAST.getDirection() : -1;
        if ( PRESSED[3] )
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
     * This method will return the angle that the player should shoot
     * @return direction or -1 if no direction
     */
    public static double getShootingDirection()
    {
        float deltaX = 0, deltaY = 0;
        if ( Gdx.input.isTouched() ) {
            if ( isAndroid ) {
                deltaX = shootTouchpad.getKnobPercentY();
                deltaY = shootTouchpad.getKnobPercentX();
            }
            else {
                deltaX = Gdx.input.getX() - Gdx.graphics.getWidth() / 2;
                deltaY = Gdx.graphics.getHeight() / 2 - Gdx.input.getY();
            }
        }
        
        if ( deltaX != 0 && deltaY != 0 )
            return 90 + 360 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
    	return -1;
    }

    /** @see com.badlogic.gdx.InputProcessor#keyDown(int) */
    @Override
    public boolean keyDown(int keycode)
    {
        for ( int i = 0; i < CONTROLS.length; i++ )
            if ( CONTROLS[i] == keycode )
                PRESSED[i] = true;
        return true;
    }

    /** @see com.badlogic.gdx.InputProcessor#keyUp(int) */
    @Override
    public boolean keyUp(int keycode)
    {
        for ( int i = 0; i < CONTROLS.length; i++ )
            if ( CONTROLS[i] == keycode )
                PRESSED[i] = false;
        return true;
    }
}
