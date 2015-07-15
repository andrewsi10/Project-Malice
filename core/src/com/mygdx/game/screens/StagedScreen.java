package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.Audio;
import com.mygdx.game.Malice;

/**
 *  This class is the ground work of the screens in this game
 *  
 *  This class holds the game, Skin, Stage, background Image, and volume percent 
 *  of the screen, where the default image is "img/titlescreen.png".
 *  
 *  This class manages the resizing of the Stage and Image, and renders them 
 *  with the Image in the Stage.
 *  
 *  Note: this class will not dispose of the skin or the game, only the stage
 *
 *  @author  Nathan Lui
 *  @version Jul 10, 2015
 *  @author  Assignment: Project Malice-core
 *
 *  @author  Sources: Libgdx
 */
public class StagedScreen extends ScreenAdapter
{
    /**
     * Volume percent of this screen (must be in the range [0,100] to work 
     * properly)
     */
    private final int VOLUME;
    
    public static final boolean isAndroid = Gdx.app.getType().equals( ApplicationType.Android );
    public static final float fontScale = 1.7f;
    
    protected final Malice game;
    protected Skin skin;
    protected Stage stage;
    protected Image background;
    
    /**
     * Used by all Menu Screens
     * 
     * Fills this screen with a new empty stage and background from the file
     * "img/titlescreen.png"
     * 
     * @param g the Game that this Screen is in
     * @param s Skin for this screen to use
     * @param volume new volume percent of this screen; 
     *                  if Volume is -1, it will not change
     */
    public StagedScreen( Malice g, Skin s, int volume ) {
        this( g, s, new Stage(), volume );
    }
    private StagedScreen( Malice g, Skin s, Stage stg, int volume ) {
        this( g, s, stg, "img/titlescreen.png", volume );
    }
    
    /**
     * Used by Splash Screen
     * 
     * @param g the Game that this Screen is in
     * @param s Skin for this screen to use
     * @param img String file of image for background
     * @param volume new volume percent of this screen; 
     *                  if Volume is -1, it will not change
     */
    public StagedScreen( Malice g, Skin s, String img, int volume ) {
        this( g, s, new Image( new SpriteDrawable( new Sprite( 
                                        new Texture( img ) ) ) ), volume );
    }
    private StagedScreen( Malice g, Skin s, Image img, int volume ) {
        this( g, s, new Stage(), img, volume );
    }
    
    /**
     * Used by GameScreen
     * 
     * @param g the Game that this Screen is in
     * @param s Skin for this screen to use
     * @param stg Stage for this screen to use
     * @param img String file of image for background
     * @param volume new volume percent of this screen; 
     *                  if Volume is -1, it will not change
     */
    public StagedScreen( Malice g, Skin s, Stage stg, String img, int volume ) {
        this( g, s, stg, new Image( new SpriteDrawable( 
                                new Sprite( new Texture( img ) ) ) ), volume );
    }
    
    /**
     * Main Constructor
     * 
     * @param g the Game that this Screen is in
     * @param s Skin for this screen to use
     * @param stg Stage for this screen to use
     * @param img background image
     * @param volume new volume percent of this screen; 
     *                  if Volume is -1, it will not change
     */
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
        if ( VOLUME != -1 )
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
