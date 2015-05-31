package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Malice;
import com.mygdx.game.MimicGdx;

public class Splash implements Screen
{

	private SpriteBatch batch;

	private Sprite splashSprite;

	private float elapsed;

	private final Malice game;

	Music mainTheme;

	public Splash(Malice g)
	{
		game = g;
	}

	@Override
	public void show()
	{
	    MimicGdx.initializeAudio();
		batch = new SpriteBatch();
		Texture splashTexture = new Texture( "img/splashscreen.png" );
		splashSprite = new Sprite( splashTexture );
		splashSprite
				.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		mainTheme = Gdx.audio.newMusic( Gdx.files
				.internal( "audio/music/revivedpower.mp3" ) );
		mainTheme.setLooping( true );
		mainTheme.play();
	}

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
			game.setScreen( new MainMenu( game, mainTheme ) );
		}
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		batch.dispose();
		splashSprite.getTexture().dispose();
		mainTheme.dispose();
	}

}
