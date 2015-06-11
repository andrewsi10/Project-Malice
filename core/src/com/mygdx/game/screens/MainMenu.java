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
 * This screen is the main menu of Gauntlet. It has a background image and
 * allows the user to play or exit the game. The screen also utilizes a ton of
 * LibGDX libraries, including Stage, Skin, and TextButton.
 *
 * @author  Som Pathak
 * @author  Other contributors: Andrew Si
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class MainMenu implements Screen
{
    /**
     * Volume of this screen
     */
    private static final float VOLUME = 0.55f;

	private Image background;

	private final Malice game;

	private Stage stage;

	private TextButton playButton, 
//	                optionsButton, 
	                exitButton;
	private final TextButton muteButton;

	/**
	 * Creates a MainMenu screen and stores the Malice object that created this
	 * screen as well as the music object that is currently playing.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 * @param m
	 *            the music currently playing
	 */
	public MainMenu(Malice g)
	{
		game = g;
        stage = new Stage();

        background = new Image( (Drawable) new SpriteDrawable( new Sprite(
                new Texture( "img/titlescreen.png" ) ) ) );
        playButton = new TextButton( "Play", Options.buttonSkin );
        playButton.setPosition(
            Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 );
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.characterSelect );
                playButton.toggle();
            }
        } );

//        optionsButton = new TextButton( "Settings", Options.buttonSkin ); 
//        optionsButton.setPosition( 
//                Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2, 
//                Gdx.graphics.getHeight() / 3 );
//        optionsButton.addListener( new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                game.setScreen( new OptionsScreen( game ) );
//            }
//        } );
        muteButton = new TextButton( Options.Audio.MUSIC_MUTED ? "UNMUTE" : "MUTE", Options.buttonSkin );
        muteButton.setSize( 100, 64 );
        muteButton.setPosition( Gdx.graphics.getWidth() - muteButton.getWidth(), 0 );
        muteButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Options.Audio.MUSIC_MUTED = !Options.Audio.MUSIC_MUTED;
                Options.Audio.SOUND_MUTED = !Options.Audio.SOUND_MUTED;
                Options.Audio.playTheme( Options.Audio.mainTheme.getVolume() );
                muteButton.setText( Options.Audio.MUSIC_MUTED ? "UNMUTE" : "MUTE" );
                muteButton.toggle();
            }
        });

        exitButton = new TextButton( "Exit", Options.buttonSkin ); 
        exitButton.setPosition(
            Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8,
            Gdx.graphics.getHeight() / 4 );
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        } );
        stage.addActor( background );
        stage.addActor( playButton );
        stage.addActor( muteButton );
//        stage.addActor( optionsButton );
        stage.addActor( exitButton );
	}
	
	/**
	 * Shows the screen.
	 * 
	 * Decreases the volume of the music, calls createSkin(), then displays the
	 * main menu background and two buttons, play and exit. Clicking play opens
	 * up the character select menu and clicking exit closes the application.
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
        Options.Audio.playTheme( VOLUME );
        Gdx.input.setInputProcessor( stage );
        muteButton.setText( Options.Audio.MUSIC_MUTED ? "UNMUTE" : "MUTE" );
	}

	/**
	 * Refreshes the screen and redraws the same stage.
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
	public void resize( int width, int height ) {}

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
	 * Removes everything that can create memory leakage.
	 * 
	 * Disposes of the skin and stage.
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		stage.dispose();
	}

}
