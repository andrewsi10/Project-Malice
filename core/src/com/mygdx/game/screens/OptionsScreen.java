package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Audio;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;

public class OptionsScreen implements Screen
{
    private final Malice game;
    private Stage stage;
    private TextButton backButton;
    private TextButton musicButton;
    private TextButton soundButton;
    /**
     * Suggested outline:
     *          Music: scrollBar
     *          Sound: scrollBar
     *          
     *          Controls:   list of controls(all buttons to change them)
     * 
     * 
     */
    
    public OptionsScreen( Malice g )
    {
        game = g;
        stage = new Stage();
        
        musicButton = new TextButton( "Music", Options.SKIN ); 
        musicButton.setPosition( 
                    Gdx.graphics.getWidth() / 2 - musicButton.getWidth() / 2, 
                    Gdx.graphics.getHeight() / 2 );
        musicButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Audio.MUSIC_MUTED = !Audio.MUSIC_MUTED;
                Audio.playTheme( Audio.mainTheme.getVolume() );
                musicButton.toggle();
            }
        } );

        soundButton = new TextButton( "Sound", Options.SKIN ); 
        soundButton.setPosition( 
                    Gdx.graphics.getWidth() / 2 - soundButton.getWidth() / 2, 
                    Gdx.graphics.getHeight() / 3 );
        soundButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Audio.SOUND_MUTED = !Audio.SOUND_MUTED;
                Audio.playAudio( "levelup" );
                soundButton.toggle();
            }
        } );
        stage.addActor( musicButton );
        stage.addActor( soundButton );
    }

    
    /**
     * Updates this Screen according to the parameters
     * 
     * @param prev the Screen that called this one
     * @return this Screen for the game to be set to
     */
    public OptionsScreen update( final Screen prev )
    {
        if ( backButton != null ) backButton.remove();
        backButton = new TextButton( "Back", Options.SKIN ); 
        backButton.setPosition(
                Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6 );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( prev );
                backButton.toggle();
            }
        } );
        stage.addActor( backButton );
        return this;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor( stage );
    }

    @Override
    public void render( float delta )
    {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
        stage.act();
        stage.draw();
        musicButton.setText( "Music: " + ( Audio.MUSIC_MUTED ? "UNMUTE" : "MUTE" ) );
        soundButton.setText( "Sound: " + ( Audio.SOUND_MUTED ? "UNMUTE" : "MUTE" ) );
    }

    @Override
    public void resize( int width, int height ) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
