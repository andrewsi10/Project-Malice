package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;
import com.mygdx.game.MimicGdx;

/**
 * Splash Screen is a Screen that shows the developers of the project and gives
 * credit for all artwork and audio. It also initializes and plays the music.
 * After five seconds, the splash screen is replaced by the main menu.
 *
 * @author  Som Pathak
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class Splash implements Screen
{

	private SpriteBatch batch;

	private Sprite splashSprite;

	private float elapsed;

	private final Malice game;

	/**
	 * Creates a Splash screen object and stores the Malice object that created
	 * the screen.
	 * 
	 * @param g
	 *            the Malice game that controls all the screens
	 */
	public Splash(Malice g)
	{
		game = g;
	}

	/**
	 * Loads the image displaying the credits and decreases the volume of the
	 * music.
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
		MimicGdx.initializeAudio();
	    Options.initialize();
		batch = new SpriteBatch();
		Texture splashTexture = new Texture( "img/splashscreen.png" );
		splashSprite = new Sprite( splashTexture );
		splashSprite
				.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		Options.Audio.mainTheme.setLooping( true );
		Options.Audio.mainTheme.setVolume( 0.7f );
		Options.Audio.mainTheme.play();
	}

	/**
	 * Refreshes the screen.
	 * 
	 * Clears the screen and redraws the same image. If five seconds have
	 * passed, the Malice game switches the screen to a Main Menu screen.
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		elapsed += delta;
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
		batch.begin();
		splashSprite.draw( batch );
		batch.end();
		if ( elapsed > 5 )
		{
			game.setScreen( new MainMenu( game ) );
		}
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height)
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause()
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume()
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide()
	{

	}

	/**
	 * Gets rid of everything that can cause memory leakage.
	 * 
	 * Disposes the Batch, texture from the Sprite, and the Music.
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		batch.dispose();
		splashSprite.getTexture().dispose();
		Options.Audio.mainTheme.dispose();
	}

}
