package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WarriorWalking implements Screen
{
	private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	@Override
	public void dispose()
	{
		batch.dispose();
		textureAtlas.dispose();
	}

	@Override
	public void show()
	{
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(
				Gdx.files.internal( "img/WarriorWalking.atlas" ) );
		animation = new Animation( 1 / 5f, textureAtlas.getRegions() );
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );

		batch.begin();
		// sprite.draw(batch);
		elapsedTime += delta;
		batch.draw( animation.getKeyFrame( elapsedTime, true ), 0, 0 );
		batch.end();

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

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
}
