package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class OptionsScreen extends StagedScreen
{
    public static boolean hasChanged = false;
    
    private TextButton musicButton, soundButton, backButton;
    private Slider musicSlider, soundSlider, zoomSlider;
    private Label titleLabel, zoomLabel;
    
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

        titleLabel = new Label( "Settings", s, "label" );
        zoomLabel = new Label( "Zoom: " + GameScreen.ZOOM, s, "label" );
        // initialize buttons
        musicButton = new TextButton( "Music", skin );
        soundButton = new TextButton( "Sound", skin );
        setDefualtSizes( musicButton, soundButton );
        // initialize sliders
        musicSlider = new Slider( 0, 100, 5, false, skin );
        soundSlider = new Slider( 0, 100, 5, false, skin );
        zoomSlider = new Slider( 20, 200, 5, false, skin ); // TODO
        
        // xy -coordinates of settings
        float width = stage.getWidth();
        float height = stage.getHeight();
        float centerX = width / 2;
        float titleY = height * 7 / 8;
        float buttonX = width / 4 - BUTTON_WIDTH / 2;
        float musicY = height * 2 / 3;
        float sliderX = width / 2;
        float soundY = height / 2;
        float sliderWidth = BUTTON_WIDTH;
        float zoomY = height / 3;
        
        // Scaling
        titleLabel.setFontScale( 4.0f ); // title scale
        scaleLabels( titleLabel, musicButton.getLabel(), soundButton.getLabel(), zoomLabel );
        
        // setPositions
        titleLabel.setPosition( centerX - titleLabel.getPrefWidth() / 2, titleY );
        zoomLabel.setPosition( buttonX, zoomY );
        musicButton.setPosition( buttonX, musicY );
        soundButton.setPosition( buttonX, soundY );
        musicSlider.setPosition( sliderX, musicY );
        soundSlider.setPosition( sliderX, soundY );
        zoomSlider.setPosition( sliderX, zoomY );

        // set slider width and values
        musicSlider.setWidth( sliderWidth );
        musicSlider.setValue( Audio.MUSIC_VOLUME );
        soundSlider.setWidth( sliderWidth );
        soundSlider.setValue( Audio.SOUND_VOLUME );
        zoomSlider.setWidth( sliderWidth );
        zoomSlider.setValue( GameScreen.ZOOM * 100 );
        
        // set button Listeners
        musicButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                hasChanged = true;
                Audio.MUSIC_VOLUME = Audio.isMusicMuted() ? 100 : 0;
                musicSlider.setValue( Audio.MUSIC_VOLUME );
                Audio.playTheme();
                musicButton.toggle();
            }
        } );
        soundButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                hasChanged = true;
                Audio.SOUND_VOLUME = Audio.isSoundMuted() ? 100 : 0;
                soundSlider.setValue( Audio.SOUND_VOLUME );
                Audio.playAudio( "levelup" );
                soundButton.toggle();
            }
        } );
        
        // set Slider Listeners
        musicSlider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                hasChanged = true;
                Slider slider = (Slider)actor;
                Audio.MUSIC_VOLUME = (int)slider.getValue();
                Audio.playTheme();
            }
        } );
        soundSlider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                hasChanged = true;
                Slider slider = (Slider)actor;
                Audio.SOUND_VOLUME = (int)slider.getValue();
                if ( !slider.isDragging() )
                    Audio.playAudio( "levelup" );
            }
        } );
        zoomSlider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                hasChanged = true;
                Slider slider = (Slider)actor;
                float value = (int)slider.getValue() / 100.0f;
                if ( !slider.isDragging() )
                    GameScreen.ZOOM = value;
                zoomLabel.setText( "Zoom: " + value );
            }
        } );
        
        // add Actors
        stage.addActor( titleLabel );
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
        scaleLabels( backButton.getLabel() );
        backButton.setPosition(
                stage.getWidth() / 2 - backButton.getWidth() / 2,
                stage.getHeight() / 6 );
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
    public void render( float delta )
    {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
        super.render( delta );
//        musicButton.setText( "Music: " + ( Audio.MUSIC_MUTED ? "UNMUTE" : "MUTE" ) );
//        soundButton.setText( "Sound: " + ( Audio.SOUND_MUTED ? "UNMUTE" : "MUTE" ) );
    }
}
