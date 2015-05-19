package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WarriorWalking implements Screen
{
	private SpriteBatch batch;
	// private Texture texture;
	private TextureAtlas textureAtlas;
	private Sprite sprite;
	private int currentFrame = 0;
	private String currentAtlasKey = new String( "0" );
	// TextureRegion[] frames;
	// float stateTime;
	// Animation animation;

	private int col = 4;
	private int row = 2;

	@Override
	public void dispose()
	{
		batch.dispose();
		textureAtlas.dispose();
	}

	@Override
	public void show()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(
				Gdx.files.internal( "img/sprites/WhiteMonk/WhiteMonk.atlas" ) );
		// TextureRegion[][] tmp = TextureRegion.split(texture,
		// texture.getWidth() / col,
		// texture.getHeight() / row);
		// frames = new TextureRegion[col * row];
		//
		// int index = 0;
		// for (int i = 0; i < row; i++) {
		// for (int j = 0; j < col; j++) {
		// frames[index++] = tmp[i][j];
		// }
		// }
		//
		// animation = new Animation(1f, frames);
		// stateTime = 0f;
		sprite = new Sprite( textureAtlas.findRegion( "6" ) );
		sprite.setPosition( w / 2 - sprite.getWidth() / 2,
				h / 2 - sprite.getHeight() / 2 );
		sprite.scale( 0.5f );
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor( 1, 1, 1, 1 );
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );

		if ( currentFrame < 29 )
		{
			currentFrame++;
		} else
		{
			currentFrame = 0;
		}

		if ( Gdx.input.isKeyPressed( Input.Keys.A )
				&& Gdx.input.isKeyPressed( Input.Keys.W ) )
		{
			sprite.translateX( (float) ( -5 / Math.sqrt( 2 ) ) );
			sprite.translateY( (float) ( 5 / Math.sqrt( 2 ) ) );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.A )
				&& Gdx.input.isKeyPressed( Input.Keys.S ) )
		{
			sprite.translateX( (float) ( -5 / Math.sqrt( 2 ) ) );
			sprite.translateY( (float) ( -5 / Math.sqrt( 2 ) ) );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.W )
				&& Gdx.input.isKeyPressed( Input.Keys.D ) )
		{
			sprite.translateX( (float) ( 5 / Math.sqrt( 2 ) ) );
			sprite.translateY( (float) ( 5 / Math.sqrt( 2 ) ) );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 + 4 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.D )
				&& Gdx.input.isKeyPressed( Input.Keys.S ) )
		{
			sprite.translateX( (float) ( 5 / Math.sqrt( 2 ) ) );
			sprite.translateY( (float) ( -5 / Math.sqrt( 2 ) ) );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 + 4 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.A ) )
		{
			sprite.translateX( -5f );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.D ) )
		{
			sprite.translateX( 5f );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 + 4 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.S ) )
		{
			sprite.translateY( -5f );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 + 6 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		} else if ( Gdx.input.isKeyPressed( Input.Keys.W ) )
		{
			sprite.translateY( 5f );
			currentAtlasKey = String.format( "%01d", currentFrame / 15 + 2 );
			sprite.setRegion( textureAtlas.findRegion( currentAtlasKey ) );
		}
		batch.begin();
		sprite.draw( batch );
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
