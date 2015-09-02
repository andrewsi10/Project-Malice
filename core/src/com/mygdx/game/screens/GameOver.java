package com.mygdx.game.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;

/**
 * The Game Over screen displays the same background image as the Main Menu and
 * allows the user to try again with the same class, choose a different class,
 * or exit the game. The screen also shows users which level they reached and
 * how many points they received. The screen utilizes a ton of LibGDX libraries,
 * including Stage, Skin, and BitmapFont.
 *
 * @author  Som Pathak
 * @author  Other contributors: Andrew Si
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class GameOver extends StagedScreen
{
	private TextButton retryButton, switchButton, leaderButton, backButton;
	private TextField textField;
	private Label message;

	/**
	 * Creates a GameOver screen and stores the Malice object that created this
	 * screen, the music currently playing, the player object from Game Screen,
	 * and the class that the player chose.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 * @param m
	 *            the music currently playing
	 * @param player
	 *            the player that the user was controlling in the Game Screen
	 * @param playerType
	 *            the class that the player chose in the CharacterSelect screen
	 */
	public GameOver( Malice g, Skin s )
	{
	    super( g, s, -1 );
	    
	    float width = stage.getWidth();
	    float height = stage.getHeight();

        switchButton = new TextButton( "Switch Characters", skin ); 
        leaderButton = new TextButton( "Leader Board", skin );
        backButton = new TextButton( "Back To Main Menu", skin );
        
        setDefaultSizes( switchButton, leaderButton, backButton );
        
        // positions
        switchButton.setPosition( width * 2 / 3 - BUTTON_WIDTH / 2, height / 3 );
        leaderButton.setPosition( width / 3 - BUTTON_WIDTH / 2,     height / 6 );
        backButton.setPosition( width * 2 / 3 - BUTTON_WIDTH / 2,   height / 6 );
        
        // listeners
        switchButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.characterSelect );
                switchButton.toggle();
            }
        } );
        leaderButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.leaderScreen.update( game.gameOver ) );
                leaderButton.toggle();
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
        
        message = new Label( "", skin, "smallLabel" );

        scaleLabels( switchButton.getLabel(), leaderButton.getLabel(), backButton.getLabel(), message );
        
        stage.addActor( switchButton );
        stage.addActor( leaderButton );
        stage.addActor( backButton );
        stage.addActor( message );
	}
    
    /**
     * Updates this Screen according to the parameters
     * @param points integer amount of points the player had
     * @param level integer representing the player's level
     * @return this Screen for the game to be set to
     */
	public GameOver update( final int points, int level )
	{
	    message.setText( "You earned " + points + " points and reached level " 
	                    + level + ". Better luck next time!" );
        message.setPosition( stage.getWidth() / 2 - message.getPrefWidth() / 2, 
                             stage.getHeight() * 67 / 100 );
        
	    if ( retryButton != null ) retryButton.remove();
	    if ( textField != null ) textField.remove();
        retryButton = new TextButton( "Try Again", skin );
        scaleLabels( retryButton.getLabel() );
        retryButton.setPosition(
            stage.getWidth() / 3 - retryButton.getWidth() / 2,
            stage.getHeight() / 3 );
        retryButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.gameScreen.update() );
                retryButton.toggle();
            }
        } );
        
        textField = new TextField( "Enter Name ", skin );
        textField.setBounds( 
            stage.getWidth() / 2 - switchButton.getWidth() / 2, 
            stage.getHeight() / 2,
            retryButton.getWidth(), 
            retryButton.getHeight() );
        textField.setFocusTraversal( false );
        textField.setTextFieldListener( new TextFieldListener() {
            @Override
            public void keyTyped( TextField textField, char c )
            {
                if ( c == '\r' || c == '\n' )
                {
                    LeaderScreen.addEntry( textField.getText(), points );
                    game.setScreen( game.leaderScreen.update( game.gameOver ) );
                    textField.setDisabled( true );
                }
            }
        } );
        stage.addActor( textField );
        stage.addActor( retryButton );
	    return this;
	}
}
