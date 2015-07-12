package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AndroidController extends Controller
{
    private static Touchpad movementTouchpad;
    private static Touchpad shootTouchpad;
    public AndroidController( Skin skin )
    {
        // Create a touchpad skin
        Skin touchpadSkin = skin;
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

    /**
     * @see com.mygdx.game.Controller#getInputDirection()
     */
    @Override
    public double getInputDirection() 
    {
        if ( movementTouchpad.isTouched() ) {
            // this return statement converts to navigation coordinates with ( 90 - degrees )
            // then it returns a positive number with ( + 360 )
            return 90 + 360 - Math.toDegrees( 
                                Math.atan2( movementTouchpad.getKnobPercentY(), 
                                            movementTouchpad.getKnobPercentX() ) );
        }
        return -1;
    }

    /**
     * @see com.mygdx.game.Controller#getShootingDirection()
     */
    @Override
    public double getShootingDirection()
    {
        float deltaX = 0, deltaY = 0;
        if ( shootTouchpad.isTouched() ) {
            deltaX = shootTouchpad.getKnobPercentY();
            deltaY = shootTouchpad.getKnobPercentX();
        }
        if ( deltaX != 0 && deltaY != 0 )
            return 90 + 360 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
        return -1;
    }

    /**
     * @see com.mygdx.game.Controller#reset()
     */
    @Override
    public void reset() {}

}
