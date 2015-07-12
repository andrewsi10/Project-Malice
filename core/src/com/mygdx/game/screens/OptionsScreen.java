package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Audio;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;

public class OptionsScreen extends StagedScreen
{
    private TextButton musicButton, soundButton, backButton;
    private Slider musicSlider, soundSlider, zoomSlider;
    private Label zoomLabel;
    private boolean isAndroid = Gdx.app.getType().equals( ApplicationType.Android );
    private float fontScale = 1.7f;
    
    /**
     * Suggested outline:
     *          Music: slider
     *          Sound: slider
     *          
     *          Controls:   list of controls(all buttons to change them)
     *          
     *          Android controls: adjustable positions
     */
    public OptionsScreen( Malice g, Skin s )
    {
        super( g, s, -1 );
        background.setVisible( false );

        // initialize buttons
        musicButton = new TextButton( "Music", skin );
        soundButton = new TextButton( "Sound", skin );
        // initialize sliders
        musicSlider = new Slider( 0, 100, 5, false, skin );
        soundSlider = new Slider( 0, 100, 5, false, skin );
        zoomSlider = new Slider( 20, 200, 5, false, skin ); // TODO
        
        // xy -coordinates of music and sound settings
        float buttonX = Gdx.graphics.getWidth() / 4 - musicButton.getWidth() / 2;
        float musicY = Gdx.graphics.getHeight() * 2 / 3;
        float sliderX = Gdx.graphics.getWidth() / 2;
        float soundY = Gdx.graphics.getHeight() / 2;
        float sliderWidth = musicButton.getWidth();
        float zoomY = Gdx.graphics.getHeight() / 3;
        
        // set buttons
        musicButton.setPosition( buttonX, musicY );
        musicButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Audio.MUSIC_VOLUME = Audio.isMusicMuted() ? 100 : 0;
                musicSlider.setValue( Audio.MUSIC_VOLUME );
                Audio.playTheme();
                musicButton.toggle();
            }
        } );
        
        soundButton.setPosition( buttonX, soundY );
        soundButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Audio.SOUND_VOLUME = Audio.isSoundMuted() ? 100 : 0;
                soundSlider.setValue( Audio.SOUND_VOLUME );
                Audio.playAudio( "levelup" );
                soundButton.toggle();
            }
        } );
        
        // set sliders
        musicSlider.setWidth( sliderWidth );
        musicSlider.setPosition( sliderX, musicY );
        musicSlider.setValue( Audio.MUSIC_VOLUME );
        musicSlider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                Slider slider = (Slider)actor;
                Audio.MUSIC_VOLUME = (int)slider.getValue();
                Audio.playTheme();
            }
        } );

        soundSlider.setWidth( sliderWidth );
        soundSlider.setPosition( sliderX, soundY );
        soundSlider.setValue( Audio.SOUND_VOLUME );
        soundSlider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                Slider slider = (Slider)actor;
                Audio.SOUND_VOLUME = (int)slider.getValue();
                if ( !slider.isDragging() )
                    Audio.playAudio( "levelup" );
            }
        } );
        
        zoomSlider.setWidth( sliderWidth );
        zoomSlider.setPosition( sliderX, zoomY );
        zoomSlider.setValue( GameScreen.ZOOM * 100 );
        zoomSlider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                Slider slider = (Slider)actor;
                float value = (int)slider.getValue() / 100.0f;
                if ( !slider.isDragging() )
                    GameScreen.ZOOM = value;
                zoomLabel.setText( "Zoom: " + value );
            }
        } );
        
        zoomLabel = new Label( "Zoom: " + GameScreen.ZOOM, s, "label" );
        zoomLabel.setPosition( buttonX, zoomY );
        
        // Additional properties
        if ( isAndroid )
        {
            musicButton.getLabel().setFontScale( fontScale, fontScale );
            soundButton.getLabel().setFontScale( fontScale, fontScale );
            zoomLabel.setFontScale( fontScale, fontScale );
        }
        
        // add Actors
        stage.addActor( musicButton );
        stage.addActor( soundButton );
        stage.addActor( musicSlider );
        stage.addActor( soundSlider );
        stage.addActor( zoomSlider );
        stage.addActor( zoomLabel );
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
        backButton = new TextButton( "Back", skin ); 
        if ( isAndroid )
        {
        	backButton.getLabel().setFontScale( fontScale, fontScale );
        }
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
    public void hide() {
        Options.saveSettings();
        super.hide();
    }

    @Override
    public void render( float delta )
    {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
        super.render( delta );
//        musicButton.setText( "Music: " + ( Audio.MUSIC_MUTED ? "UNMUTE" : "MUTE" ) );
//        soundButton.setText( "Sound: " + ( Audio.SOUND_MUTED ? "UNMUTE" : "MUTE" ) );
    }
}
