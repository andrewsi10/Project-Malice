package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;

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
public class CharacterSelect implements Screen
{
    /**
     * Volume of this screen
     */
    private static final float VOLUME = 0.7f;

    /**
     * Gets the array storing the names of the characters that will be used for
     * the buttons.
     * 
     * @return characterNames, the array containing the names of the characters
     *         that will be used for the buttons
     */
    public static final String[] characterNames = { "Dark Wizard", "Brawler",
            "Crimson Wizard", "Bandit", "Warrior", "Mage of Justice" };
    
    private static final int NUMBUTTONS = characterNames.length;
    
	private Image background;

	private final Malice game;

	private Stage stage;

	private TextButton[] characters;
	private TextButton exitButton, randomButton;

	/**
	 * Creates a CharacterSelect screen and stores the Malice object that
	 * created this screen and the music currently playing.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 * @param m
	 *            the music currently playing
	 */
	public CharacterSelect(Malice g)
	{
		game = g;
        characters = new TextButton[NUMBUTTONS];
        background = new Image( (Drawable) new SpriteDrawable( new Sprite(
                new Texture( "img/titlescreen.png" ) ) ) );
        stage = new Stage();
        
        for ( int i = 0; i < NUMBUTTONS; i++ )
        {
            final String charName = characterNames[i];
            characters[i] = Options.getButton( charName, 
                Gdx.graphics.getWidth() * ( i < NUMBUTTONS / 2 ? 3 : 7 ) / 10, 
                Gdx.graphics.getHeight() * ( 63 - 18 * ( i % ( NUMBUTTONS / 2 ) ) ) / 100, // 5/8 - i*7/40
                new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    game.setScreen( game.gameScreen.setPlayerType( charName ) );
                }
            } );
        }
        randomButton = Options.getButton( "Random Character", 
                                    Gdx.graphics.getWidth() * 3 / 10, 
                                    Gdx.graphics.getHeight() / 15, 
                                    new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.gameScreen.setPlayerType( characterNames[(int)(Math.random() * NUMBUTTONS)] ) );
            }
        } );

        exitButton = Options.getButton( "Back to Main Menu", 
                            Gdx.graphics.getWidth() * 7 / 10, 
                            Gdx.graphics.getHeight() / 15,
                            new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( new MainMenu( game ) );
            }
        } );
        
        stage.addActor( background );
        for ( TextButton b : characters )
            stage.addActor( b );
        stage.addActor( exitButton );
        stage.addActor( randomButton );
	}

	/**
	 * Sets up the six character class buttons in a two by three grid as well as
	 * an exit button beneath the six class buttons. The background image is the
	 * same as the image from MainMenu.
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
        Options.Audio.playTheme( VOLUME );
        Gdx.input.setInputProcessor( stage );// Make the stage consume events
	}

	/**
	 * Refreshes the screen.
	 * 
	 * This method shouldn't change anything on the screen since none of the
	 * images or anything are changed.
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		stage.act();
		stage.draw();
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
	 * Disposes the Skin and Stage to prevent memory leakage.
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		stage.dispose();
	}
}
