package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;
import com.mygdx.game.sprites.AnimatedSprite;
import com.mygdx.game.sprites.DisplaySprite;
import com.mygdx.game.sprites.Player;

/**
 * This screen displays six different classes for the player to choose from and
 * an exit button. Selecting one of the classes begins gameplay and selecting
 * the exit button closes the game. The screen uses the same background image as
 * the main menu screen. The screen utilizes a ton of LibGDX libraries, including
 * Stage, Skin, and TextButton.
 *
 * @author Andrew Si
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class CharacterSelect extends StagedScreen
{
    public static final int SPRITE_DELAY = 5;
    public static final int SPRITE_SIZE = 128;
    /**
     * Gets the array storing the names of the characters that will be used for
     * the buttons.
     * 
     * @return characterNames, the array containing the names of the characters
     *         that will be used for the buttons
     */
    private static final int NUMBUTTONS = Player.NAMES.length;

	private TextButton playButton;
	private ImageButton backButton;
	private DisplaySprite[] sprites;
	private DisplaySprite spriteToDraw;
	private Player.Name currentName;

	/**
	 * Creates a CharacterSelect screen and stores the Malice object that
	 * created this screen and the music currently playing.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 * @param m
	 *            the music currently playing
	 */
	public CharacterSelect( Malice g, Skin s )
	{
	    super( g, s, 70 );
	    
	    float width = stage.getWidth();
	    float height = stage.getHeight();
	    float inc = height / ( NUMBUTTONS + 2 );
	    float x = width * 1 / 5 - BUTTON_WIDTH / 2;
	    float y = height - inc;

        sprites = new DisplaySprite[NUMBUTTONS + 1];
        
        for ( int i = 0; i <= NUMBUTTONS; i++ )
        {
            stage.addActor( this.characterSelectButton( x, y, i ) );
            y -= inc;
        }
        playButton = new TextButton( "Play", skin );
        backButton = new ImageButton( skin, "back" );
        
        setDefaultSizes( playButton );
        backButton.setSize( 75, 75 );
        
        // position
        playButton.setPosition( width * 3 / 4, height / 24 );
        backButton.setPosition( 2, 2 );
        
        // listeners
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y )
            {
                if ( currentName == null )
                    currentName = Player.NAMES[(int)(Math.random() * NUMBUTTONS)];
                game.setScreen( game.gameScreen.update( currentName ) );
                spriteToDraw = sprites[0];
                currentName = Player.NAMES[0];
                playButton.toggle();
            }
        } );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.mainMenu );
                backButton.toggle();
            }
        } );

        spriteToDraw = sprites[0];
        spriteToDraw.setVisible( true );
        currentName = Player.NAMES[0];
        stage.addActor( playButton );
        stage.addActor( backButton );
	}
	
	@Override
	public void render( float delta )
	{
	    super.render( delta );
	    Batch batch = stage.getBatch();
	    batch.begin();
	    spriteToDraw.render( delta );
	    spriteToDraw.draw( batch );
	    batch.end();
	}
	
	/**
	 * Returns a new CharacterSelect button based on parameters
	 * @param isRand whether this is the random button
	 * @param x the x -coordinate of the button
	 * @param y the y -coordinate of the button
	 * @param i the type of button to create
	 * @return
	 */
	public TextButton characterSelectButton( float x, float y, final int i )
	{
        float width = stage.getWidth();
        float height = stage.getHeight();
	    final TextButton button;
	    final Player.Name name;
	    if ( i != NUMBUTTONS ) // if this is not the random button
	    {
	        name = Player.NAMES[i];
            button = new TextButton( name.buttonName, skin );

	        sprites[i] = new DisplaySprite( skin, SPRITE_DELAY, Player.PLAYER_ANIMATIONS.get( name ) );
	        sprites[i].setSpriteData( Player.LOADERS.get( name ) );
	        sprites[i].createLabels();
	        sprites[i].setSize( SPRITE_SIZE, SPRITE_SIZE );
	    }
	    else
	    {
            name = null;
	        button = new TextButton( "Random", skin );

            sprites[i] = new DisplaySprite( skin, SPRITE_DELAY, 
                new Animation( AnimatedSprite.FRAME_DURATION, 
                    new TextureRegion( new Texture( "img/random.png" ) ) ) );
            sprites[i].setSize( SPRITE_SIZE * 2, SPRITE_SIZE * 2 );
	    }
        setDefaultSizes( button );
        button.setPosition( x, y );
        setDisplaySprite( sprites[i], width / 2, height * 5 / 8 );
        button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                spriteToDraw.setVisible( false );
                spriteToDraw = sprites[i];
                spriteToDraw.setVisible( true );
                currentName = name;
                button.toggle();
            }
        } );
        return button;
	}
	
	public void setDisplaySprite( DisplaySprite s, float x, float y )
	{
        s.setCenterPosition( x, y );
        Label[] labels = s.getLabels();
        for ( Label l : labels ) {
            stage.addActor( l );
        }
	}
}
