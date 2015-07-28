package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AndroidController extends Controller
{
    private Touchpad movementTouchpad;
    private Touchpad shootTouchpad;
    private Button pauseButton;
    public AndroidController( Malice g, Skin skin )
    {
        super( g );
        // Create new TouchPad with the created style
        movementTouchpad = new Touchpad( 10, skin, "touchPad" );
        movementTouchpad.setBounds( 30, 30, 300, 300 );
        addActor( movementTouchpad );

        shootTouchpad = new Touchpad( 10, skin, "touchPad" );
        shootTouchpad.setBounds( getWidth() - 330, 30, 300, 300 );
        addActor( shootTouchpad );

        pauseButton = new Button( skin );
        pauseButton.setSize( 100, 100 );
        pauseButton.setPosition( getWidth() - pauseButton.getWidth(), 
                                 getHeight() - pauseButton.getHeight() );
        pauseButton.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y ) {
                game.gameScreen.toggleGameState();
            }
        });
        addActor( pauseButton );
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
            deltaX = shootTouchpad.getKnobPercentX();
            deltaY = shootTouchpad.getKnobPercentY();
        }
        if ( deltaX != 0 && deltaY != 0 )
            return 90 + 360 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
        return -1;
    }

    @Override
    public void pause()
    {
        // TODO need to reset the values for the touchpads
    }

    @Override
    public void resume()
    {
        
    }

    @Override
    public void reset()
    {
        pauseButton.toFront();
    }
}
