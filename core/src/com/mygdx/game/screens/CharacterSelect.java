package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Malice;
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
    /**
     * Gets the array storing the names of the characters that will be used for
     * the buttons.
     * 
     * @return characterNames, the array containing the names of the characters
     *         that will be used for the buttons
     */
    private static final int NUMBUTTONS = Player.NAMES.length;

	private TextButton playButton, backButton, randomButton;
	private DisplaySprite[] sprites;
	private DisplaySprite spriteToDraw;
	private Player.Name currentName;
	private Label randLabel;

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
	    float y = height - inc;

        sprites = new DisplaySprite[NUMBUTTONS + 1];
        randLabel = new Label( "?", skin, "label" );
        randLabel.setPosition( width * 3 / 4, height * 3 / 4 );
        
        for ( int i = 0; i < NUMBUTTONS; i++ )
        {
            final Player.Name n = Player.NAMES[i];
            final TextButton b = new TextButton( n.buttonName, skin );
            setDefaultSizes( b );
            scaleLabels( b.getLabel() );
            b.setPosition( width * 3 / 10 - BUTTON_WIDTH / 2, y ); // 5/8 - i*7/40
            y -= inc;
            
            sprites[i] = new DisplaySprite( skin, SPRITE_DELAY, Player.PLAYER_ANIMATIONS.get( n ) );
            sprites[i].setSpriteData( Player.LOADERS.get( n ) );
            sprites[i].createLabels();
            sprites[i].setCenterPosition( width * 3 / 4, height * 3 / 4 );
            final int j = i;
            b.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    spriteToDraw.setVisible( false );
                    spriteToDraw = sprites[j];
                    spriteToDraw.setVisible( true );
                    currentName = n;
                    b.toggle();
                }
            } );
            stage.addActor( b );
            Label[] labels = sprites[i].getLabels();
            scaleLabels( labels );
            for ( Label l : labels ) {
                stage.addActor( l );
            }
        }
        playButton = new TextButton( "Play", skin );
        randomButton = new TextButton( "Random Character", skin );
        backButton = new TextButton( "Back to Main Menu", skin );
        
        setDefaultSizes( playButton, backButton, randomButton );
        
        // position
        playButton.setPosition( width * 3 / 4, height / 24 );
        randomButton.setPosition( width * 3 / 10 - BUTTON_WIDTH / 2, y );
        backButton.setPosition( 0, 0 );
        
        // listeners
        playButton.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y )
            {
                game.setScreen( game.gameScreen.update( currentName ) );
                spriteToDraw = sprites[0];
                currentName = Player.NAMES[0];
                playButton.toggle();
            }
        } );
        randomButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                randLabel.setVisible( false );
                spriteToDraw = null;
                randLabel.setVisible( true );
                currentName = Player.NAMES[(int)(Math.random() * NUMBUTTONS)];
                randomButton.toggle();
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

        scaleLabels( playButton.getLabel(), randomButton.getLabel(), backButton.getLabel() );

        spriteToDraw = sprites[0];
        spriteToDraw.setVisible( true );
        currentName = Player.NAMES[0];
        stage.addActor( playButton );
        stage.addActor( randomButton );
        stage.addActor( backButton );
	}
	
	@Override
	public void render( float delta )
	{
	    super.render( delta );
	    if ( spriteToDraw != sprites[NUMBUTTONS] )
	    {
	        Batch batch = stage.getBatch();
	        batch.begin();
	        spriteToDraw.render( delta );
	        spriteToDraw.draw( batch );
	        batch.end();
	    }
	}
}
