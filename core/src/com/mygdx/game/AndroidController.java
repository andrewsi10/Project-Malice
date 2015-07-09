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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AndroidController extends Stage
{
	private Skin touchpadSkin;
	private TouchpadStyle touchpadStyle;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private static Touchpad touchpad;

	/**
	 * This array stores Key input values 0 , 1 , 2 , 3 NORTH, EAST, SOUTH, WEST
	 */
	public static final int[] CONTROLS = new int[] { Input.Keys.W,
			Input.Keys.D, Input.Keys.S, Input.Keys.A, };
	public static boolean[] PRESSED = new boolean[CONTROLS.length];

	public static float dir; // TODO android

	public AndroidController()
	{
		// Create a touchpad skin
		touchpadSkin = new Skin();
		// Set background image
		touchpadSkin.add( "touchBackground", new Texture(
				"ui/touchBackground.png" ) );
		// Set knob image
		touchpadSkin.add( "touchKnob", new Texture( "ui/touchKnob.png" ) );
		// Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		// Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable( "touchBackground" );
		touchKnob = touchpadSkin.getDrawable( "touchKnob" );
		// Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		// Create new TouchPad with the created style
		touchpad = new Touchpad( 10, touchpadStyle );
		// setBounds(x,y,width,height)
		touchpad.setBounds( 15, 15, 300, 300 );
		addActor( touchpad );
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

		// if ( deltaY == 0 && deltaX == 0 )
		// return -1;
		// note: formula to convert from standard x,y grid to course navigation
		// grid
		// return -1;
		// System.out.println("THIS IS THE ANGLE: " + Math.toDegrees(
		// Math.atan2( touchpad.getKnobY(), touchpad.getKnobX() )));
		return Math.toDegrees( Math.atan2( touchpad.getKnobY(),
				touchpad.getKnobX() ) );
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

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		super.touchDragged( screenX, screenY, pointer );
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
