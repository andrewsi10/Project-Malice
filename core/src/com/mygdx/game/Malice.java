package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
	public static final String TITLE = "Gauntlet", VERSION = "1.0.1.9";

	public Splash splash;
    public MainMenu mainMenu; // note: can use a single array for all the screens
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
	    splash = new Splash( this, Options.SKIN );
        Options.initialize();
        setScreen( splash );
        Controller c = ( Gdx.app.getType().equals( ApplicationType.Android ) ) 
                        ? new AndroidController( this, Options.SKIN ) 
                        : new DesktopController( this );
                        
	    mainMenu = new MainMenu( this, Options.SKIN );
	    optionsScreen = new OptionsScreen( this, Options.SKIN );
	    leaderScreen = new LeaderScreen( this, Options.SKIN );
	    characterSelect = new CharacterSelect( this, Options.SKIN );
	    gameScreen = new GameScreen( this, Options.SKIN, c );
	    gameOver = new GameOver( this, Options.SKIN );
	}
	
	/**
	 * Dispose of all the resources of the game when exiting
	 * 
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() 
	{
	    if ( OptionsScreen.hasChanged )
	        Options.saveSettings();
	    this.splash.dispose();
	    this.mainMenu.dispose();
	    this.optionsScreen.dispose();
	    this.leaderScreen.dispose();
	    this.characterSelect.dispose();
	    this.gameScreen.dispose();
	    this.gameOver.dispose();
	    Options.dispose();
	}

}