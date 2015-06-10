package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
        
        musicButton = Options.getButton( "Music", 
                           Gdx.graphics.getWidth() / 2, 
                           Gdx.graphics.getHeight() / 2, 
                           new ClickListener() );

        soundButton = Options.getButton( "Sound", 
                           Gdx.graphics.getWidth() / 2, 
                           Gdx.graphics.getHeight() / 3, 
                           new ClickListener() );

        backButton = Options.getButton( "Back to Main Menu", 
                            Gdx.graphics.getWidth() / 2, 
                            Gdx.graphics.getHeight() / 15,
                            new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( new MainMenu( game ) );
            }
        } );
        stage.addActor( musicButton );
        stage.addActor( soundButton );
        stage.addActor( backButton );
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
        if ( musicButton.isPressed() ) {
            Options.Audio.MUSIC_MUTED = !Options.Audio.MUSIC_MUTED;
            Options.Audio.playTheme( Options.Audio.mainTheme.getVolume() );
        }
        if ( soundButton.isPressed() ) {
            Options.Audio.SOUND_MUTED = !Options.Audio.SOUND_MUTED;
        }
            
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
