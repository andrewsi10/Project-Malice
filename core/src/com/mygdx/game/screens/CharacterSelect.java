package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;
import com.mygdx.game.player.Player;

/**
 * This screen displays six different classes for the player to choose from and
 * an exit button. Selecting one of the classes begins gameplay and selecting
 * the exit button closes the game. The screen uses the same background image as
 * the main menu screen. The screen utilizes a ton of LibGDX libraries, including
 * Stage, Skin, and TextButton.
 *
 * @author Andrew Si
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class CharacterSelect extends StagedScreen
{
    /**
     * Gets the array storing the names of the characters that will be used for
     * the buttons.
     * 
     * @return characterNames, the array containing the names of the characters
     *         that will be used for the buttons
     */
    private static final int NUMBUTTONS = Player.NAMES.length;

	private TextButton backButton, randomButton;

	/**
	 * Creates a CharacterSelect screen and stores the Malice object that
	 * created this screen and the music currently playing.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 * @param m
	 *            the music currently playing
	 */
	public CharacterSelect( Malice g, Skin s )
	{
	    super( g, s, 70 );
        
        for ( int i = 0; i < NUMBUTTONS; i++ )
        {
            final Player.Name n = Player.NAMES[i];
            final TextButton b = new TextButton( n.buttonName, skin );
            if ( isAndroid )
            {
            	b.getLabel().setFontScale( fontScale );
            }
            b.setPosition( 
                Gdx.graphics.getWidth() * ( i < NUMBUTTONS / 2 ? 3 : 7 ) / 10 - b.getWidth() / 2,
                Gdx.graphics.getHeight() * ( 63 - 18 * ( i % ( NUMBUTTONS / 2 ) ) ) / 100 ); // 5/8 - i*7/40
            b.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    game.setScreen( game.gameScreen.update( n ) );
                    b.toggle();
                }
            } );
            stage.addActor( b );
        }
        randomButton = new TextButton( "Random Character", skin );
        randomButton.setPosition(
                Gdx.graphics.getWidth() * 3 / 10 - randomButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 12 );
        randomButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Player.Name n = Player.NAMES[(int)(Math.random() * NUMBUTTONS)];
                game.setScreen( game.gameScreen.update( n ) );
                randomButton.toggle();
            }
        } );

        backButton = new TextButton( "Back to Main Menu", skin );
        backButton.setPosition(
                Gdx.graphics.getWidth() * 7 / 10 - backButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 12 );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.mainMenu );
                backButton.toggle();
            }
        } );

        if ( isAndroid )
        {
            randomButton.getLabel().setFontScale( fontScale );
            backButton.getLabel().setFontScale( fontScale );
        }
        stage.addActor( backButton );
        stage.addActor( randomButton );
	}
}
