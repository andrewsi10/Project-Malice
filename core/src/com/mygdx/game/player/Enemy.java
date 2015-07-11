package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Options;
import com.mygdx.game.projectile.Projectile;

import static com.mygdx.game.player.AnimatedSprite.Direction.NUMDEGREES;

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

    /**
     * The margin of change for the Enemy to choose directions
     */
    public static final int marginOfDelta = 30;
    public static final int travelTimeScalar = 100;
    public static final int minTravelTime = 4;
    
    // TODO may change with a difficulty setting or depending on type of Enemy
    public static final int aggroDistance = 400;
    
	private int travelTime;

	/**
	 * Enemy constructor. Parameter used to reference the images used for
	 * animation frames. Create an Array<AtlasRegion> and input it in the super
	 * constructor. Initialize experience to 20, speed to 3, reloadSpeed to
	 * getReloadSpeed() * 2, and projectile to the file name of the atlas file
	 * for the enemy projectiles. Initialize direction to a random integer in
	 * the interval [0, 8) and travelTime to a random integer in the interval
	 * [minTravelTime, minTravelTime + travelTimeScalar).
	 * 
	 * @param file
	 *            reference to the atlas file used to get the images for Enemy
	 */
	public Enemy( Animation a ) {
		super( Color.RED, 50, 20, 0, 3, 1000, "EnemyBullet", Options.atlas.get( "EnemyBullet" ), a );
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
		setReloadSpeed(getReloadSpeed() * 2); // set reload speed
	}

    /**
     * @see com.mygdx.game.player.Character#move(com.mygdx.game.player.Character, java.util.ArrayList, long)
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
	public void move(Character character, ArrayList<Projectile> projectiles,
			long time) {
        float deltaX = character.getX() - getX();
        float deltaY = character.getY() - getY();
		if ( !inRange( deltaX, deltaY ) ) {
			setRandomDirection();
		} else {
	        // move towards the player
			double newDir = 90 - Math.toDegrees( Math.atan2( deltaY, deltaX ) );
			setDirection( newDir );
			shoot( projectiles, newDir, time );
		}
        translate();
        setAnimations();
	}

	/**
	 * If travelTime < 1, reassign direction to a random number in the interval
	 * [0, 360) and travelTime to a random number in the interval [minTravelTime,
	 * minTravelTime + travelTimeScalar). Otherwise decrement travelTime by one.
	 */
	public void setRandomDirection() {
		if (travelTime < 1) {
			setDirection( Math.random() * NUMDEGREES.getDirection() );
			travelTime = (int) (minTravelTime + Math.random()
					* travelTimeScalar);
		}
		travelTime--;
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
	public boolean inRange( float dx, float dy ) {
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
