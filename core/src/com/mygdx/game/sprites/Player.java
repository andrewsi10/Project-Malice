package com.mygdx.game.sprites;

import java.util.ArrayList;
import java.util.EnumMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.loaders.StatLoader;
import com.mygdx.game.sprites.SpriteData.Stats;
import com.mygdx.game.AndroidController;
import com.mygdx.game.Controller;

/**
 *  This class represents a Player in the game. Takes in Input from the keyboard
 *  in order to move around and shoot.
 *
 *  @author  Christopher Cheung
 *  @author  Other contributors: Andrew Si, Nathan Lui
 *  @version May 31, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Player extends Character {
    
    public enum Name {
        BlackMage( "Dark Wizard", "DarkFire" ), 
        Monk( "Brawler", "Boomerang" ), 
        RedMage( "Crimson Wizard", "Fireball" ), 
        Thief( "Bandit", "PoisonShot" ), 
        Warrior( "Warrior", "Sword1" ), 
        WhiteMage( "Mage of Justice", "HolyCross" );
        
        public final String buttonName, projectileName;
        
        Name( String button, String projectile ) {
            this.buttonName = button;
            this.projectileName = projectile;
        }
    }
    public static final Name[] NAMES = Name.values();
    public static final EnumMap<Name, Animation[]> PLAYER_ANIMATIONS = new EnumMap<Name, Animation[]>(Name.class);
    public static final EnumMap<Name, Animation> PROJECTILE_ANIMATIONS = new EnumMap<Name, Animation>(Name.class);
    public static final EnumMap<Name, StatLoader> LOADERS = new EnumMap<Name, StatLoader>(Name.class);
    
    /**
     * Loads animations for the player's sprite
     */
    public static void loadMaps() {
        String s;
        Array<AtlasRegion> a;
        for ( Name n : NAMES )
        {
            // Player Animations
            a = new TextureAtlas( "img/sprites/Players/" + n + "/" + n + ".atlas" ).getRegions();
            PLAYER_ANIMATIONS.put( n, new Animation[]{
                new Animation( FRAME_DURATION, a.get( 0 ), a.get( 1 ) ),
                new Animation( FRAME_DURATION, a.get( 2 ), a.get( 3 ) ),
                new Animation( FRAME_DURATION, a.get( 4 ), a.get( 5 ) ),
                new Animation( FRAME_DURATION, a.get( 6 ), a.get( 7 ) ) } );
            // Projectile Animations
            s = n.projectileName + "/" + n.projectileName + ".atlas";
            a = new TextureAtlas( "img/sprites/Projectiles/" + s ).getRegions();
            PROJECTILE_ANIMATIONS.put( n, new Animation( FRAME_DURATION, a ) );
            // Loaders
            LOADERS.put( n, new StatLoader( n + ".txt", n.buttonName ) );
        }
    }

    private Controller controller;
	private int playerPoints;
	
	private Label pointsLabel;

	/**
	 * The constructor for player. The first parameter is used to reference the
	 * file names of the images to create the Arrays for the super constructor.
	 * The second parameter is used to reference the file name for the
	 * projectile. Initialize variable speed to 5, expToLevel to 100, and level
	 * to 1.
	 * 
	 * @param file
	 *            reference for the player sprite atlas file
	 * @param proj
	 *            reference for the projectile atlas file
	 */
	public Player( Skin skin, Controller c )
	{
	    super( skin, Color.GREEN );
        controller = c;
        pointsLabel = new Label( "", skin, "smallLabel" );
        updatePointsLabel(1);
	}

	/**
	 * Constructor used for testing only. It does not use the super constructor
	 * or initialize projectile. Initialize speed, expToLevel, and level as
	 * accordingly with the regular constructor.
	 */
	public Player() {
		setSpeed( 5 );
		getSpriteData().setStat( Stats.EXPTOLEVEL, 100 );
	}
	
	/**
	 * Reloads the Player with default settings:
	 * 
	 * maxHp 50; exp 0; level 1; speed 5; reloadSpeed 500; playerPoints 0;
	 * 
     * Controller.PRESSED = new boolean[Controller.CONTROLS.length];
	 */
	public void reload() // reloads with default settings
	{
	    this.getSpriteData().resetSprite();
        getSpriteData().setStat( Stats.EXPTOLEVEL, 100 );
        this.resetDirection();
        this.updateLabels();
        this.syncSpeeds();
	    this.playerPoints = 0;
	    // poor implementation, if someone thinks of better solution please fix
	    if (controller instanceof AndroidController)
	    {
		    ( (AndroidController) controller ).resetTouchpad();
	    }
	}
	
	/**
	 * Changes this player to a different "texture"
	 * @param projectile projectile name
	 * @param proj Projectile Animation
	 * @param a new Animations
	 */
	public void change( Name n )
	{
        setSpriteData( LOADERS.get( n ) );
	    this.setProjectile( n.projectileName, PROJECTILE_ANIMATIONS.get( n ) );
	    this.initializeAnimations( PLAYER_ANIMATIONS.get( n ) );
	}

	/**
	 * @see com.mygdx.game.sprites.Character#move(com.mygdx.game.sprites.Character, java.util.ArrayList, long)
	 * 
	 * moves the Character according to the input of keyboard gotten from 
	 * MimicGdx class
	 * 
	 * @param character main Character to interact with -- not used in Player
	 * @param projectiles ArrayList of Projectiles to add this Player's 
	 *                     projectile into the environment when shooting
	 * @param time Time in game (used in order to determine delays in moving or
     *            shooting)
	 */
	@Override
	public void move( Character character, ArrayList<Entity> entities, long time ) {
		double dir = controller.getInputDirection();
		setDirection( dir );
		translate();
		setAnimations();
        
        dir = controller.getShootingDirection();
        if ( dir >= 0 )
            shoot( entities, dir, System.currentTimeMillis() );
	}
	
	@Override
	public void die( ArrayList<Entity> entities ) {}

	/**
	 * increases displayed player points by 10 times the player's score multiplier
	 */
	public void increasePoints(int scoreMultiplier) {
		playerPoints += 10 * scoreMultiplier;
		updatePointsLabel(scoreMultiplier);
	}

	/**
	 * Increases the experience of Player (variable inherited by Character). If
	 * experience exceeds expToLevel, increment level by one and set experience
	 * to the remaining experience after reaching expToLevel.
	 * 
	 * @param exp
	 *            current value of experience
	 */
	public void increaseExp(int exp) {
	    SpriteData s = getSpriteData();
	    s.increaseExp( exp );
		while ( s.canLevel() ) {
		    int toNextLevel = s.getExpToLevel() * s.getLevel() / 2;
			increaseCurrentLevel();
			s.setStat( Stats.EXPTOLEVEL, toNextLevel );
		}
	}
	
	/**
	 * Updates the number of points displayed on the screen
	 */
	public void updatePointsLabel(int scoreMultiplier) {
	    pointsLabel.setText( "POINTS: " + playerPoints + "\nx" + scoreMultiplier + " Combo" );
	}

	// --------------------Getters & Setters------------------ //

	/**
	 * returns the amount of points earned by the Player
	 * 
	 * @return points earned by the player
	 */
	public int getPoints() {
		return playerPoints;
	}
	
	/**
	 * Returns a Label where the text of the Label will display:
	 * 
	 * "POINTS: " + this player's points
	 * 
	 * Positioning, drawing, and Color must be managed by Screen
	 * @return pointsLabel
	 */
	public Label getPointsLabel() {
	    return pointsLabel;
	}
}
