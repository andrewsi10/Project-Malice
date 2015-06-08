package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

	private Skin skin;

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
	}

	/**
	 * Creates a skin and the text button style that will be displayed in the
	 * main menu.
	 * 
	 * The skin should be the default LibGDX skin and the text button style
	 * should also be the default style.
	 */
	private void createSkin()
	{
		// Create a font
		BitmapFont font = new BitmapFont();

		characters = new TextButton[NUMBUTTONS];

		background = new Image( (Drawable) new SpriteDrawable( new Sprite(
				new Texture( "img/titlescreen.png" ) ) ) );

		skin = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
		skin.add( "default", font );

		// Create a texture
		Pixmap pixmap = new Pixmap( (int) Gdx.graphics.getWidth() / 4,
				(int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888 );
		pixmap.setColor( Color.WHITE );
		pixmap.fill();
		skin.add( "background", new Texture( pixmap ) );

		// Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable( "background", Color.GRAY );
		textButtonStyle.down = skin.newDrawable( "background", Color.DARK_GRAY );
		textButtonStyle.checked = skin.newDrawable( "background",
				Color.DARK_GRAY );
		textButtonStyle.over = skin
				.newDrawable( "background", Color.LIGHT_GRAY );
		textButtonStyle.font = skin.getFont( "default" );
		skin.add( "default", textButtonStyle );

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
		stage = new Stage();
		Gdx.input.setInputProcessor( stage );// Make the stage consume events
		createSkin();
		
		final TextButton randomButton, exitButton;
		final String name = characterNames[(int)(Math.random() * NUMBUTTONS)];

        stage.addActor( background );

        exitButton = new TextButton( "Exit", skin );
        exitButton.setPosition(
                Gdx.graphics.getWidth() * 7 / 10 - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 15 );
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        } );

        randomButton = new TextButton( "Random", skin );
        randomButton.setPosition(
                Gdx.graphics.getWidth() * 3 / 10 - randomButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 15 );
        randomButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                for ( TextButton button : characters )
                {
                    button.remove();
                }
                randomButton.remove();
                exitButton.remove();
                game.setScreen( new GameScreen( game, name ) );
            }
        } );
        
		for ( int i = 0; i < NUMBUTTONS; i++ )
		{
			final String charName = characterNames[i];
			characters[i] = new TextButton( charName, skin );
			characters[i].setPosition( 
			    Gdx.graphics.getWidth() * ( i < NUMBUTTONS / 2 ? 3 : 7 ) / 10 - characters[i].getWidth() / 2,
				Gdx.graphics.getHeight() * ( 63 - 18 * ( i % ( NUMBUTTONS / 2 ) ) ) / 100 ); // 5/8 - i*7/40
			characters[i].addListener( new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					for ( TextButton button : characters )
					{
						button.remove();
					}
                    randomButton.remove();
					exitButton.remove();
					game.setScreen( new GameScreen( game, charName ) );
				}
			} );
            stage.addActor( characters[i] );
		}
        stage.addActor( exitButton );
        stage.addActor( randomButton );
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
		skin.dispose();
		stage.dispose();
	}
}
