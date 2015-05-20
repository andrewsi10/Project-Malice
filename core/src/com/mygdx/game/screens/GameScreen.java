package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Malice;
import com.mygdx.game.player.Player;

public class GameScreen implements Screen
{

	private SpriteBatch batch;

	private final Malice game;

	private Player player;

	Music music;

	public GameScreen(Malice g, Music m)
	{
		game = g;
		music = m;
	}

	@Override
	public void show()
	{
		player = new Player();
		batch = new SpriteBatch();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		player.setPosition( w / 2 - player.getWidth() / 2,
				h / 2 - player.getHeight() / 2 );
		player.scale( 0.5f );
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );

		batch.begin();
		if ( Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT ) )
		{
			player.strafe();
		} else
		{
			player.move();
		}
		player.draw( batch );
		batch.end();
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
	}

}
