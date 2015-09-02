package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;

/**
 * This screen is the main menu of Gauntlet. It has a background image and
 * allows the user to play or exit the game. The screen also utilizes a ton of
 * LibGDX libraries, including Stage, Skin, and TextButton.
 *
 * @author  Som Pathak
 * @author  Other contributors: Andrew Si
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class MainMenu extends StagedScreen
{
    private Label titleLabel;
	private TextButton playButton, leaderButton, exitButton;
	private ImageButton settingsButton;

	/**
	 * Creates a MainMenu screen and stores the Malice object that created this
	 * screen as well as the music object that is currently playing.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 * @param m
	 *            the music currently playing
	 */
	public MainMenu( Malice g, Skin s )
	{
	    super( g, s, 55 );
	    float width = stage.getWidth();
	    float height = stage.getHeight();
	    
	    titleLabel = new Label( Malice.TITLE, skin, "titleLabel" );
        playButton = new TextButton( "Play", skin );
        leaderButton = new TextButton( "Leader Board", skin );
        settingsButton = new ImageButton( skin, "settingsButtonStyle" );
        exitButton = new TextButton( "Exit", skin );
        
        // sizes
        setDefaultSizes( playButton, leaderButton, exitButton );
        settingsButton.setSize( 100, 100 );
        
        // positions
        float centerX = width / 2;
        float centerButtonX = width / 2 - BUTTON_WIDTH / 2;
        float titleX = centerX - titleLabel.getPrefWidth() / 2;
        titleLabel.setPosition(     titleX,        height * 3 / 4 );
        playButton.setPosition(     centerButtonX, height / 2 );
        leaderButton.setPosition(   centerButtonX, height * 3 / 8 );
        settingsButton.setPosition( width - settingsButton.getWidth(), 0 );
        exitButton.setPosition(     centerButtonX, height / 4 );
        
        // listeners
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.characterSelect );
                playButton.toggle();
            }
        } );
        leaderButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.leaderScreen.update( game.mainMenu ) );
                leaderButton.toggle();
            }
        } );
        settingsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.optionsScreen.update( game.mainMenu ) );
                settingsButton.toggle();
            }
        } );
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        } );

        scaleLabels( titleLabel,
                     playButton.getLabel(), 
                     leaderButton.getLabel(),
                     exitButton.getLabel() );

        stage.addActor( titleLabel );
        stage.addActor( playButton );
        stage.addActor( leaderButton );
        stage.addActor( settingsButton );
        stage.addActor( exitButton );
	}
}
