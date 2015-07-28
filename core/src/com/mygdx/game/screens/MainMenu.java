package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	private TextButton playButton, leaderButton, settingsButton, exitButton;

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
        playButton = new TextButton( "Play", skin );
        playButton.setPosition(
            stage.getWidth() / 2 - playButton.getWidth() / 2,
            stage.getHeight() / 2 );
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.characterSelect );
                playButton.toggle();
            }
        } );
        
        leaderButton = new TextButton( "Leader Board", skin );
        leaderButton.setPosition(
            stage.getWidth() / 2 - leaderButton.getWidth() / 2,
            stage.getHeight() * 3 / 8 );
        leaderButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.leaderScreen.update( game.mainMenu ) );
                leaderButton.toggle();
            }
        } );

        settingsButton = new TextButton( "Settings", skin ); 
        settingsButton.setSize( 150, 96 );
        settingsButton.setPosition( stage.getWidth() - settingsButton.getWidth(), 0 );
        settingsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.optionsScreen.update( game.mainMenu ) );
                settingsButton.toggle();
            }
        } );

        exitButton = new TextButton( "Exit", skin );
        exitButton.setPosition(
            stage.getWidth() / 2 - exitButton.getWidth() / 2,
            stage.getHeight() / 4 );
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        } );

        if ( isAndroid )
        {
            playButton.getLabel().setFontScale( fontScale );
            leaderButton.getLabel().setFontScale( fontScale );
            settingsButton.getLabel().setFontScale( fontScale );
            exitButton.getLabel().setFontScale( fontScale );
        }
        stage.addActor( playButton );
        stage.addActor( leaderButton );
        stage.addActor( settingsButton );
        stage.addActor( exitButton );
	}
}
