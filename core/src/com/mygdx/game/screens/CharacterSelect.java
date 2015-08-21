package com.mygdx.game.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;
import com.mygdx.game.sprites.Player;

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
	    
	    float width = stage.getWidth();
	    float height = stage.getHeight();
        
        for ( int i = 0; i < NUMBUTTONS; i++ )
        {
            final Player.Name n = Player.NAMES[i];
            final TextButton b = new TextButton( n.buttonName, skin );
            setDefualtSizes( b );
            scaleLabels( b.getLabel() );
            b.setPosition( 
                width * ( i < NUMBUTTONS / 2 ? 3 : 7 ) / 10 - BUTTON_WIDTH / 2,
                height * ( 63 - 18 * ( i % ( NUMBUTTONS / 2 ) ) ) / 100 ); // 5/8 - i*7/40
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
        backButton = new TextButton( "Back to Main Menu", skin );
        
        setDefualtSizes( backButton, randomButton );
        
        // position
        randomButton.setPosition(
                width * 3 / 10 - BUTTON_WIDTH / 2,
                height / 12 );
        backButton.setPosition(
                width * 7 / 10 - BUTTON_WIDTH / 2,
                height / 12 );
        
        // listeners
        randomButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Player.Name n = Player.NAMES[(int)(Math.random() * NUMBUTTONS)];
                game.setScreen( game.gameScreen.update( n ) );
                randomButton.toggle();
            }
        } );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.mainMenu );
                backButton.toggle();
            }
        } );

        scaleLabels( randomButton.getLabel(), backButton.getLabel() );

        stage.addActor( backButton );
        stage.addActor( randomButton );
	}
}
