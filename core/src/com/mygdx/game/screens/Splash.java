package com.mygdx.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Malice;

/**
 * Splash Screen is a Screen that shows the developers of the project and gives
 * credit for all artwork and audio. It also initializes and plays the music.
 * After five seconds, the splash screen is replaced by the main menu.
 *
 * @author  Som Pathak
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class Splash extends StagedScreen
{
	private float elapsed;
	/**
	 * Creates a Splash screen object and stores the Malice object that created
	 * the screen.
	 * 
	 * @param g
	 *            the Malice game that controls all the screens
	 */
	public Splash( Malice g, Skin s )
	{
	    super( g, s, "img/splashscreen.png", 70 );
	}

	/**
	 * Refreshes the screen.
	 * 
	 * Clears the screen and redraws the same image. If five seconds have
	 * passed, the Malice game switches the screen to a Main Menu screen.
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		elapsed += delta;
		super.render( delta );
		if ( elapsed > 5 )
		{
			game.setScreen( game.mainMenu );
		}
	}
}
