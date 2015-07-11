package com.mygdx.game.screens;

import java.util.EnumMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Malice;

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
    public static final float FRAME_DURATION = 0.2f;
    
    public enum Name {
        BlackMage( "Dark Wizard", "DarkFire" ), 
        Monk( "Brawler", "Boomerang" ), 
        RedMage( "Crimson Wizard", "Fireball" ), 
        Thief( "Bandit", "PoisonShot" ), 
        Warrior( "Warrior", "Sword1" ), 
        WhiteMage( "Mage of Justice", "HolyCross" );
        
        private String button, projectile;
        
        Name( String button, String projectile ) {
            this.button = button;
            this.projectile = projectile;
        }
        
        public String getButtonName()
        {
            return button;
        }
        
        public String getProjectileName()
        {
            return projectile;
        }
    }
    public static final Name[] NAMES = Name.values();
    public static final EnumMap<Name, Animation[]> PLAYER_ANIMATIONS = new EnumMap<Name, Animation[]>(Name.class);
    public static final EnumMap<Name, Animation> PROJECTILE_ANIMATIONS = new EnumMap<Name, Animation>(Name.class);
    public static void loadMap() {
        String s;
        Array<AtlasRegion> a;
        for ( Name n : NAMES )
        {
            a = new TextureAtlas( "img/sprites/Players/" + n + "/" + n + ".atlas" ).getRegions();
            PLAYER_ANIMATIONS.put( n, new Animation[]{
                new Animation( FRAME_DURATION, a.get( 0 ), a.get( 1 ) ),
                new Animation( FRAME_DURATION, a.get( 2 ), a.get( 3 ) ),
                new Animation( FRAME_DURATION, a.get( 4 ), a.get( 5 ) ),
                new Animation( FRAME_DURATION, a.get( 6 ), a.get( 7 ) ) } );
            
            s = n.getProjectileName() + "/" + n.getProjectileName() + ".atlas";
            a = new TextureAtlas( "img/sprites/Projectiles/" + s ).getRegions();
            PROJECTILE_ANIMATIONS.put( n, new Animation( FRAME_DURATION, a ) );
        }
    }
    
    /**
     * Gets the array storing the names of the characters that will be used for
     * the buttons.
     * 
     * @return characterNames, the array containing the names of the characters
     *         that will be used for the buttons
     */
    private static final int NUMBUTTONS = NAMES.length;

	private TextButton backButton, randomButton;

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
        
        for ( int i = 0; i < NUMBUTTONS; i++ )
        {
            final Name charName = NAMES[i];
            final TextButton b = new TextButton( charName.getButtonName(), skin );
            b.setPosition( 
                Gdx.graphics.getWidth() * ( i < NUMBUTTONS / 2 ? 3 : 7 ) / 10 - b.getWidth() / 2,
                Gdx.graphics.getHeight() * ( 63 - 18 * ( i % ( NUMBUTTONS / 2 ) ) ) / 100 ); // 5/8 - i*7/40
            b.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    game.setScreen( game.gameScreen.update( 
                        charName.getProjectileName(), 
                        PROJECTILE_ANIMATIONS.get( charName ),
                        PLAYER_ANIMATIONS.get( charName ) ) );
                    b.toggle();
                }
            } );
            stage.addActor( b );
        }
        randomButton = new TextButton( "Random Character", skin );
        randomButton.setPosition(
                Gdx.graphics.getWidth() * 3 / 10 - randomButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 12 );
        randomButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Name n = NAMES[(int)(Math.random() * NUMBUTTONS)];
                game.setScreen( game.gameScreen.update( n.getProjectileName(), 
                                            PROJECTILE_ANIMATIONS.get( n ),
                                            PLAYER_ANIMATIONS.get( n ) ) );
                randomButton.toggle();
            }
        } );

        backButton = new TextButton( "Back to Main Menu", skin ); 
        backButton.setPosition(
                Gdx.graphics.getWidth() * 7 / 10 - backButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 12 );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen( game.mainMenu );
                backButton.toggle();
            }
        } );
        
        stage.addActor( backButton );
        stage.addActor( randomButton );
	}
}
