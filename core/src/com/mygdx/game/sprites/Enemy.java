package com.mygdx.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.ItemSprite;
import com.mygdx.game.entities.ItemSprite.Item;

/**
 *  This class represents an Enemy in the game that will attack the Player when
 *  conditions are met. Uses basic AI programming
 *
 *  @author  Christopher Cheung
 *  @author  Other contributors: Andrew Si, Nathan Lui
 *  @version May 31, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Enemy extends Character {
    
    public static final int NUMENEMIES = 7;
    public static final Animation[] ANIMATIONS = new Animation[NUMENEMIES];
    public static final Animation PROJECTILE = new Animation( FRAME_DURATION, 
        new TextureAtlas( "img/sprites/Projectiles/EnemyBullet/EnemyBullet.atlas" ).getRegions() );
    public static final Item[] ITEMS = new Item[NUMENEMIES];
    public static final int DROP_RATE = 25;
    
    public static void loadAnimations()
    {
        String s;
        Array<AtlasRegion> a;
        for ( int i = 1; i <= NUMENEMIES; i++ )
        {
            s = "img/sprites/Enemies/Enemy" + i + "/Enemy" + i + ".atlas";
            a = new TextureAtlas( s ).getRegions();
            ANIMATIONS[i-1] = new Animation( FRAME_DURATION, a );
            ITEMS[i-1] = Item.HealthKit;
        }
    }

    /**
     * The margin of change for the Enemy to choose directions
     */
    public static final int marginOfDelta = 5;
    public static final int travelTimeScalar = 100;
    public static final int minTravelTime = 4;
    
    // TODO may change with a difficulty setting or depending on type of Enemy
    public static final int aggroDistance = 400;
    
	private int travelTime;
	private double tempDirection;
	private Item item;

	/**
	 * Creates an Enemy with default stats:
	 * 
	 * hpBar: red; maxHp: 50; exp: 20; level: 0; speed: 3; reloadSpeed: 1000;
	 * projectile: "EnemyBullet"
	 * 
	 * sets a random starting direction
	 * 
	 * @param index integer index of the Enemy Animations for the type of Enemy.
	 *         Should be in the range from [0,NUMENEMIES)
	 */
	public Enemy( Skin skin, int index ) {
        super( skin, Color.RED, 50, 20, 0, 3, 1000, "EnemyBullet", PROJECTILE, ANIMATIONS[index] );
        item = ITEMS[index];
        setRandomDirection(); // TODO note: travelTime may not be initialized before performing this method
	}
    
	/**
	 * Constructor used for testing. Initialize experience to 20, speed to 3,
	 * and reloadSpeed to getReloadSpeed() * 2. Initialize direction to a random
	 * integer in the interval [0, 8) and travelTime to a random integer in the
	 * interval [minTravelTime, minTravelTime + travelTimeScalar).
	 */
	public Enemy() {
	    setRandomDirection();
		setExperience(20); // set amount of exp Player will receive
		setSpeed(3); // set speed of Enemy
	}

    /**
     * @see com.mygdx.game.sprites.Character#move(com.mygdx.game.sprites.Character, java.util.ArrayList, long)
     * 
     * moves this Character according to a basic AI algorithm
     * 
     * if the Character is within range, move toward that Character and shoot 
     * at it, else get a random direction to move in
     * 
     * @param character main Character to interact with, the Character to attack
     * @param projectiles ArrayList of Projectiles to add this Player's 
     *                     projectile into the environment when shooting
     * @param time Time in game (used in order to determine delays in moving or
     *            shooting)
     */
	@Override
	public void move( Character character, ArrayList<Entity> entities, long time ) {
        setRandomDirection();
        float deltaX = character.getCenterX() - getCenterX();
        float deltaY = character.getCenterY() - getCenterY();
		if ( inRange( deltaX, deltaY ) ) {
	        // move towards the player
			double newDir = 90 + 360 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
            shoot( entities, newDir, time );
            if ( tempDirection < 0 )
                tempDirection *= -4;
            newDir += ( ( tempDirection - 180 ) / marginOfDelta ); // random adjustment
			setDirection( newDir );
		}
        translate();
        setAnimations( true );
	}
	
	/**
	 * @see com.mygdx.game.sprites.Character#die(java.util.ArrayList)
	 */
	@Override
	public void die( ArrayList<Entity> entities ) {
        dropItem( entities ); // TODO drop when dead
	}

	/**
	 * If travelTime < 1, reassign direction to a random number in the interval
	 * [0, 360) and travelTime to a random number in the interval [minTravelTime,
	 * minTravelTime + travelTimeScalar). Otherwise decrement travelTime by one.
	 */
	public void setRandomDirection() {
		if (travelTime < 1) {
		    tempDirection = Math.random() * 450 - 90;
			setDirection( tempDirection );
			travelTime = (int) (minTravelTime + Math.random()
					* travelTimeScalar);
		}
		travelTime--;
	}
	
	/**
	 * Drops this Sprite's items
	 * @param entities ArrayList of items to drop items into
	 */
	public void dropItem( ArrayList<Entity> entities ) {
	    if ( isDead() ) {
	        int temp = (int)( Math.random() * 100 );
	        if ( temp < DROP_RATE )
	            entities.add( new ItemSprite( this, item ) );
	    }
	}

	/**
	 * Determines whether the enemy is in shooting range of the character. Enemy
	 * is in shooting range when character is inside aggroDistance. Use the
	 * distance formula in relation to Enemy.
	 * 
	 * @param dx change in x
     * @param dy change in y
	 * @return whether enemy is within aggroDistance
	 */
	public static boolean inRange( float dx, float dy ) {
		int distance = (int) Math.sqrt(dx * dx + dy * dy);
		return distance <= aggroDistance;
	}

	// ------------- Getters and Setters ----------------- //
	
	/**
	 * getter method for travelTime
	 * 
	 * @return travelTime
	 */
	public int getTravelTime() {
		return travelTime;
	}

	/**
	 * setter method for travelTime
	 * 
	 * @param time
	 *            new value for travelTime
	 */
	public void setTravelTime(int time) {
		travelTime = time;
	}
}
