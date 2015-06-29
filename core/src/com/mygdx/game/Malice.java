package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.CharacterSelect;
import com.mygdx.game.screens.GameOver;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LeaderScreen;
import com.mygdx.game.screens.MainMenu;
import com.mygdx.game.screens.OptionsScreen;
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

    public MainMenu mainMenu;
    public OptionsScreen optionsScreen;
    public LeaderScreen leaderScreen;
    public CharacterSelect characterSelect;
	public GameScreen gameScreen;
	public GameOver gameOver;
	
	/**
	 * Sets the screen to a new Splash Screen.
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create()
	{
        Options.initialize();
        setScreen( new Splash( this ) );
	    mainMenu = new MainMenu( this );
	    optionsScreen = new OptionsScreen( this );
	    leaderScreen = new LeaderScreen( this );
	    characterSelect = new CharacterSelect( this );
	    gameScreen = new GameScreen( this );
	    gameOver = new GameOver( this );
	}

}