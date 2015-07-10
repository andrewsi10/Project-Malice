package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;

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
public class GameOver implements Screen
{
	private final Malice game;
	private Skin skin;
	private Stage stage;
    private Batch batch;
    
    private Image background;
	private TextButton retryButton, switchButton, leaderButton, backButton;
	private TextField textField;

	private GlyphLayout layout;
	private String message;

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
		game = g;
		skin = s;
		layout = new GlyphLayout();
        stage = new Stage();

        batch = stage.getBatch();
        background = new Image( (Drawable) new SpriteDrawable( new Sprite(
                new Texture( "img/titlescreen.png" ) ) ) );

        switchButton = new TextButton( "Switch Characters", skin ); 
        switchButton.setPosition(
            Gdx.graphics.getWidth() * 2 / 3 - switchButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 3 );
        switchButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.characterSelect );
                switchButton.toggle();
            }
        } );
        
        leaderButton = new TextButton( "Leader Board", skin ); 
        leaderButton.setPosition(
                Gdx.graphics.getWidth() / 3 - leaderButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6 );
        leaderButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.leaderScreen.update( game.gameOver ) );
                leaderButton.toggle();
            }
        } );
        
        backButton = new TextButton( "Back To Main Menu", skin ); 
        backButton.setPosition(
                Gdx.graphics.getWidth() * 2 / 3 - backButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6 );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.mainMenu );
                backButton.toggle();
            }
        } );

        stage.addActor( background );
        stage.addActor( switchButton );
        stage.addActor( leaderButton );
        stage.addActor( backButton );
	}
    
    /**
     * Updates this Screen according to the parameters
     * @param points integer amount of points the player had
     * @param level integer representing the player's level
     * @return this Screen for the game to be set to
     */
	public GameOver update( final int points, int level )
	{
	    message = "You earned " + points + " points and reached level " + level
	                    + ". Better luck next time!";
        layout.setText( Options.FONT, message );
        
	    if ( retryButton != null ) retryButton.remove();
	    if ( textField != null ) textField.remove();
        retryButton = new TextButton( "Try Again", skin );
        retryButton.setPosition(
            Gdx.graphics.getWidth() / 3 - retryButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 3 );
        retryButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.gameScreen );
                retryButton.toggle();
            }
        } );
        
        textField = new TextField( "Enter Name ", skin );
        textField.setBounds( 
            Gdx.graphics.getWidth() / 2 - switchButton.getWidth() / 2, 
            Gdx.graphics.getHeight() / 2,
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
	
	/**
	 * Shows the Game Over screen by displaying the background image and the
	 * three buttons the user can select.
	 * 
	 * Sets up the Stage and background image and buttons.
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
        Options.FONT.setColor( Color.WHITE );
        Gdx.input.setInputProcessor( stage );// Make the stage consume events
	}

	/**
	 * Displays the amount of points the player received and the level that the
	 * player reached.
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		stage.act();
		stage.draw();
		batch.begin();
		Options.FONT.draw( batch, layout, 
		    Gdx.graphics.getWidth() / 2 - layout.width / 2, 
		    Gdx.graphics.getHeight() * 67 / 100 );
		batch.end();
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
    @Override
    public void resize( int width, int height ) {
        background.setSize( width, height );
        stage.getViewport().update( width, height );
    }

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {}

	/**
	 * Removes the Skin, Stage, and Batch to prevent memory leakage.
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		stage.dispose();
	}
}
