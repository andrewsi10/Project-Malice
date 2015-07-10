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
	private TextButton playButton, leaderButton, optionsButton, exitButton;

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
            Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 );
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
            Gdx.graphics.getWidth() / 2 - leaderButton.getWidth() / 2,
            Gdx.graphics.getHeight() * 3 / 8 );
        leaderButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.leaderScreen.update( game.mainMenu ) );
                leaderButton.toggle();
            }
        } );

        optionsButton = new TextButton( "Settings", skin ); 
        optionsButton.setSize( 100, 64 );
        optionsButton.setPosition( Gdx.graphics.getWidth() - optionsButton.getWidth(), 0 );
        optionsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.optionsScreen.update( game.mainMenu ) );
                optionsButton.toggle();
            }
        } );

        exitButton = new TextButton( "Exit", skin ); 
        exitButton.setPosition(
            Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8,
            Gdx.graphics.getHeight() / 4 );
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        } );
        
        stage.addActor( playButton );
        stage.addActor( leaderButton );
        stage.addActor( optionsButton );
        stage.addActor( exitButton );
	}
}
