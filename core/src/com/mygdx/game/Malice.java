package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.CharacterSelect;
import com.mygdx.game.screens.MainMenu;
import com.mygdx.game.screens.Splash;

/**
 * The Malice class extends Game and handles all the screens in the Gauntlet
 * game.
 * 
 * @author Andrew Si
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class Malice extends Game
{
	/**
	 * Name of the application window is TITLE followed by VERSION
	 */
	public static final String TITLE = "Gauntlet", VERSION = "1.0.0.0";
	
	public CharacterSelect characterSelect;
//	public GameOver gameOver;
//	public GameScreen gameScreen;
	public MainMenu mainMenu;
	public Splash splash;
	/**
	 * Sets the screen to a new Splash Screen.
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create()
	{
	    Options.initialize();
        splash = new Splash( this );
        mainMenu = new MainMenu( this );
	    characterSelect = new CharacterSelect( this );
		setScreen( splash );
	}

}