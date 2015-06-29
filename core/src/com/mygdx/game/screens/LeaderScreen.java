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

public class LeaderScreen implements Screen
{
    /**
     * Volume of this screen
     */
    private static final float VOLUME = 0.5f;
    
    
    private Malice game;
    private Stage stage;
    
    private TextButton prevButton;
    
    public LeaderScreen( Malice g ) {
        game = g;
        stage = new Stage();
        
        stage.addActor( new Image( (Drawable) new SpriteDrawable( new Sprite(
            new Texture( "img/titlescreen.png" ) ) ) ) );
    }
    
    public LeaderScreen update( final Screen prevScreen ) {
        if ( prevButton != null ) prevButton.remove();
        prevButton = new TextButton( "Back", Options.buttonSkin );
        prevButton.setPosition(
            Gdx.graphics.getWidth() / 2 - prevButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 12 );
        prevButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( prevScreen );
                prevButton.toggle();
            }
        } );
        stage.addActor( prevButton );
        return this;
    }

    @Override
    public void show()
    {
        Options.Audio.playTheme( VOLUME );
        Gdx.input.setInputProcessor( stage );
    }

    @Override
    public void render( float delta )
    {
        stage.act();
        stage.draw();
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
