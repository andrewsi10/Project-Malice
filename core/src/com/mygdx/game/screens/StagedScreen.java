package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.Audio;
import com.mygdx.game.Malice;

public class StagedScreen extends ScreenAdapter
{
    /**
     * Volume percent of this screen (must be in the range [0,100] to work 
     * properly)
     */
    private final int VOLUME;
    
    protected final Malice game;
    protected Skin skin;
    protected Stage stage;
    
    private Image background;
    
    public StagedScreen( Malice g, Skin s, int volume ) {
        this( g, s, new Stage(), volume );
    }
    
    public StagedScreen( Malice g, Skin s, Stage stg, int volume ) {
        this( g, s, stg, "img/titlescreen.png", volume );
    }
    
    public StagedScreen( Malice g, Skin s, Stage stg, String img, int volume ) {
        this( g, s, stg, new Image( new SpriteDrawable( 
                                        new Sprite( new Texture( img ) ) ) ), volume );
    }
    
    public StagedScreen( Malice g, Skin s, Stage stg, Image img, int volume ) {
        game = g;
        skin = s;
        stage = stg;
        background = img;
        VOLUME = volume;
        
        stage.addActor( background );
    }

    /**
     * Sets the volume percent that this Screen has and sets Input to this 
     * Screen's stage
     * 
     * @see com.badlogic.gdx.Screen#show()
     */
    @Override
    public void show()
    {
        Audio.changePercent( VOLUME );
        Gdx.input.setInputProcessor( stage ); // Make the stage consume events
    }

    /**
     * Refreshes the screen. This method updates the stage with:
     * 
     * stage.act();
     * 
     * stage.draw();
     * 
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render( float delta )
    {
        stage.act();
        stage.draw();
    }
    
    // additional @Override methods: pause, resume, hide
    
    /**
     * Resizes the screen according to both the resolution and any resize 
     * changes
     * 
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize( int width, int height ) {
        background.setSize( width, height );
        stage.getViewport().update( width, height );
    }

    /**
     * Disposes the Stage to prevent memory leakage.
     * 
     * @see com.badlogic.gdx.Screen#dispose()
     */
    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
