package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;
import com.mygdx.game.player.Player;

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
	private Image background;

	private final Malice game;

	private Stage stage;

	private TextButton retryButton, switchButton, backButton;
	
	private Player player;

	private Batch batch;

	private GlyphLayout layout;

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
	public GameOver(Malice g, Player player, final String playerType)
	{
		game = g;
		this.player = player;
		layout = new GlyphLayout();
        stage = new Stage();

        batch = new SpriteBatch();
        background = new Image( (Drawable) new SpriteDrawable( new Sprite(
                new Texture( "img/titlescreen.png" ) ) ) );
        
        retryButton = new TextButton( "Try Again", Options.buttonSkin );
        retryButton.setPosition(
            Gdx.graphics.getWidth() / 2 - retryButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 );
        retryButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.gameScreen.setPlayerType( playerType ) );
            }
        } );

        switchButton = new TextButton( "Switch Characters", Options.buttonSkin ); 
        switchButton.setPosition(
            Gdx.graphics.getWidth() / 2 - switchButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 3 );
        switchButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( new CharacterSelect( game ) );
            }
        } );
        
        backButton = new TextButton( "Back To Main Menu", Options.buttonSkin ); 
        backButton.setPosition(
                Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6 );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( new MainMenu( game ) );
            }
        } );

        stage.addActor( background );
        stage.addActor( retryButton );
        stage.addActor( switchButton );
        stage.addActor( backButton );
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
		String str = "You earned " + player.getPoints()
				+ " points and reached level " + player.getCurrentLevel()
				+ ". Better luck next time!";
		layout.setText( Options.FONT, str );
		Options.FONT.draw( batch, str, Gdx.graphics.getWidth() / 2 - layout.width / 2,
				Gdx.graphics.getHeight() * 67 / 100 );
		batch.end();
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {}

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
		batch.dispose();
	}
}
