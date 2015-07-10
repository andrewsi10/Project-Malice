package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;

public class LeaderScreen extends StagedScreen
{
    public static final FileHandle scoreFile = Gdx.files.local( "LeaderBoard.txt" );
    
    public static void addEntry( String name, int score )
    {
        scoreFile.writeString( name + " " + score + "\n", true );
    }
    
    private GlyphLayout layout;
    
    private TextButton prevButton;
    
    public LeaderScreen( Malice g, Skin s ) {
        super( g, s, 50 );
        layout = new GlyphLayout();
    }

    
    /**
     * Updates this Screen according to the parameters
     * 
     * @param prevScreen the Screen that called this one
     * @return this Screen for the game to be set to
     */
    public LeaderScreen update( final Screen prevScreen ) {
        if ( prevButton != null ) prevButton.remove();
        prevButton = new TextButton( "Back", skin );
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
        super.show();
        Options.FONT.setColor( Color.WHITE );
        if ( scoreFile.exists() ) {
            layout.setText( Options.FONT, scoreFile.readString() );
        }
    }

    @Override
    public void render( float delta )
    {
        super.render( delta );
        stage.getBatch().begin();
        Options.FONT.draw( stage.getBatch(), layout, 
            Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() * 3 / 4 );
        stage.getBatch().end();
    }
}
