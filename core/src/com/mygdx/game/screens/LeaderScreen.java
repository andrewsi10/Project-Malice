package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    
    public static final FileHandle scoreFile = Gdx.files.local( "LeaderBoard.txt" );
    
    public static void addEntry( String name, int score )
    {
        scoreFile.writeString( name + " " + score + "\n", true );
    }
    
    
    private Malice game;
    private Stage stage;
    private GlyphLayout layout;
    
    private TextButton prevButton;
    
    public LeaderScreen( Malice g ) {
        game = g;
        stage = new Stage();
        layout = new GlyphLayout();
        
        stage.addActor( new Image( (Drawable) new SpriteDrawable( new Sprite(
            new Texture( "img/titlescreen.png" ) ) ) ) );
    }

    
    /**
     * Updates this Screen according to the parameters
     * 
     * @param prevScreen the Screen that called this one
     * @return this Screen for the game to be set to
     */
    public LeaderScreen update( final Screen prevScreen ) {
        if ( prevButton != null ) prevButton.remove();
        prevButton = new TextButton( "Back", Options.SKIN );
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
        Options.FONT.setColor( Color.WHITE );
        Options.Audio.playTheme( VOLUME );
        Gdx.input.setInputProcessor( stage );
        if ( scoreFile.exists() ) {
            layout.setText( Options.FONT, scoreFile.readString() );
        }
    }

    @Override
    public void render( float delta )
    {
        stage.act();
        stage.draw();
        stage.getBatch().begin();
        Options.FONT.draw( stage.getBatch(), layout, 
            Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() * 3 / 4 );
        stage.getBatch().end();
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
