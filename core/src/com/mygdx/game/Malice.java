package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.CharacterSelect;
import com.mygdx.game.screens.GameOver;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LeaderScreen;
import com.mygdx.game.screens.MainMenu;
import com.mygdx.game.screens.OptionsScreen;
import com.mygdx.game.services.IGoogleServices;

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
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	public static IGoogleServices googleServices;
    public static boolean isAndroid;
    public MainMenu mainMenu; // note: can use a single array for all the screens
    public OptionsScreen optionsScreen;
    public LeaderScreen leaderScreen;
    public CharacterSelect characterSelect;
	public GameScreen gameScreen;
	public GameOver gameOver;
	
	public Viewport viewport;
	
	public Malice(IGoogleServices googleServices)
	{
	    Malice.googleServices = googleServices;
	}
	
	/**
	 * Sets the screen to a new Splash Screen.
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create()
	{
	    isAndroid = Gdx.app.getType().equals( ApplicationType.Android );
	    viewport = new FitViewport( Malice.GAME_WIDTH, Malice.GAME_HEIGHT );
        Options.initialize();
	    mainMenu = new MainMenu( this, Options.SKIN );
        setScreen( mainMenu );
        Controller c = ( isAndroid ) 
                        ? new AndroidController( this, Options.SKIN ) 
                        : new DesktopController( this );
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
	    this.mainMenu.dispose();
	    this.optionsScreen.dispose();
	    this.leaderScreen.dispose();
	    this.characterSelect.dispose();
	    this.gameScreen.dispose();
	    this.gameOver.dispose();
	    Options.dispose();
	}

}