package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    /** Default Button width */
    public static final int BUTTON_WIDTH = 300;
    /** Default Button height */
    public static final int BUTTON_HEIGHT = 70;
    public static final void setDefaultSizes( Button... buttons ) {
        for ( Button b : buttons )
            b.setSize( BUTTON_WIDTH, BUTTON_HEIGHT );
    }
    
    
    /**
     * Volume percent of this screen (must be in the range [0,100] to work 
     * properly)
     */
    private final int VOLUME;
    
    protected final Malice game;
    protected Skin skin;
    protected Stage stage;
    protected Image background;
    
    private static Batch BATCH;
    
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
        this( g, s, null, s.get( "background", Texture.class ), volume );
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
    public StagedScreen( Malice g, String img, int volume ) {
        this( g, null, null, new Texture( img ), volume );
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
        this( g, s, stg, new Texture( img ), volume );
    }
    
    /**
     * Main Constructor used by all the other constructors
     * 
     * @param g the Game that this Screen is in
     * @param s Skin for this screen to use
     * @param stg Stage for this screen to use. If null, sets own stage.
     * @param img background image
     * @param volume new volume percent of this screen; 
     *                  if Volume is -1, it will not change
     */
    private StagedScreen( Malice g, Skin s, Stage stg, Texture t, int volume ) {
        game = g;
        skin = s;
        stage = stg;
        background = new Image( new SpriteDrawable( new Sprite( t ) ) );
        VOLUME = volume;
        
        if ( stage == null ) {
            stage = ( BATCH == null ) ? new Stage( game.viewport ) : new Stage( game.viewport, BATCH );
        }
        BATCH = stage.getBatch(); // only use one spriteBatch
        
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
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
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
        Viewport viewport = stage.getViewport();
//        if ( width > height && viewport.getWorldWidth() < viewport.getWorldHeight() ) {
//            viewport.setWorldSize( Malice.GAME_WIDTH, Malice.GAME_HEIGHT );
//        }
//        if ( width < height && viewport.getWorldWidth() > viewport.getWorldHeight() ) {
//            viewport.setWorldSize( Malice.GAME_HEIGHT, Malice.GAME_WIDTH );
//        }
        viewport.setWorldSize( Malice.GAME_WIDTH, Malice.GAME_HEIGHT );
        viewport.update( width, height, true );
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
