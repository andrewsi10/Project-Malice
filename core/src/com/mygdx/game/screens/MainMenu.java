package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;
import com.mygdx.game.player.WarriorWalking;

public class MainMenu implements Screen
{

	private final Malice game;

	private Stage stage;

	private TextButton playButton, exitButton;

	private Skin skin;

	public MainMenu(Malice g)
	{
		game = g;
	}

	// copied from online
	private void createBasicSkin()
	{
		// Create a font
		BitmapFont font = new BitmapFont();
		skin = new Skin();
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

	@Override
	public void show()
	{
		// Stage stage = new Stage();
		// // atlas = new TextureAtlas( "ui/button.pack" );
		// skin = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
		//
		// table = new Table( skin );
		// table.setBounds( 0,
		// 0,
		// Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight() );
		//
		// white = new BitmapFont( Gdx.files.internal( "fonts/white.fnt" ),
		// false );
		// black = new BitmapFont( Gdx.files.internal( "fonts/black.fnt" ),
		// false );
		stage = new Stage();
		Gdx.input.setInputProcessor( stage );// Make the stage consume events

		createBasicSkin();
		playButton = new TextButton( "Play", skin ); // Use
														// the
														// initialized
														// skin
		playButton.setPosition(
				Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 2 );
		playButton.addListener( new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.setScreen( new WarriorWalking() );
				// game.setScreen( new Splash(game) );
			}
		} );

		exitButton = new TextButton( "Exit", skin );
		exitButton.setPosition(
				Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 4 );
		exitButton.addListener( new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.app.exit();
			}
		} );

		stage.addActor( playButton );
		stage.addActor( exitButton );
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
		stage.act();
		stage.draw();
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
		skin.dispose();
		stage.dispose();
	}

}
