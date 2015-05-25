package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
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

public class CharacterSelect implements Screen
{
	Image background;

	private final Malice game;

	private Stage stage;

	private TextButton char1Button, char2Button, exitButton;

	private TextButton[] characters;

	private String[] characterNames = { "Dark Wizard", "Brawler",
			"Crimson Wizard", "Bandit", "Warrior", "Mage of Justice" };

	private Skin skin;

	Music music;

	public CharacterSelect(Malice g, Music m)
	{
		game = g;
		music = m;
	}
	
	public String[] getNames()
	{
		return characterNames;
	}

	// copied from online
	private void createSkin()
	{
		// Create a font
		BitmapFont font = new BitmapFont();

		characters = new TextButton[6];

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

		music.setVolume( 0.7f );
		stage = new Stage();
		Gdx.input.setInputProcessor( stage );// Make the stage consume events
		createSkin();

		for ( int i = 0; i < characters.length / 2; i++ )
		{
			final String charName = characterNames[i];
			characters[i] = new TextButton(charName, skin);
			characters[i].setPosition(
					Gdx.graphics.getWidth() / 5,
					Gdx.graphics.getHeight() * (60 - 20 * i) / 100 );
			characters[i].addListener( new ClickListener()
			{
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					for (TextButton button : characters)
					{
						button.remove();
					}
					exitButton.remove();
					game.setScreen( new GameScreen( game, music,
							charName ) );
				}
			} );
		}
		
		for ( int i = characters.length / 2; i < characters.length; i++ )
		{
			final String charName = characterNames[i];
			characters[i] = new TextButton(charName, skin);
			characters[i].setPosition(
					Gdx.graphics.getWidth() * 11 / 20,
					Gdx.graphics.getHeight() * (60 - 20 * (i - characters.length / 2)) / 100 );
			characters[i].addListener( new ClickListener()
			{
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					for (TextButton button : characters)
					{
						button.remove();
					}
					exitButton.remove();
					game.setScreen( new GameScreen( game, music,
							charName ) );
				}
			} );
		}

		exitButton = new TextButton( "Exit", skin );
		exitButton.setPosition(
				Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 15 );
		exitButton.addListener( new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.app.exit();
			}
		} );

		stage.addActor( background );
		for (TextButton button : characters)
		{
			stage.addActor( button );
		}
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