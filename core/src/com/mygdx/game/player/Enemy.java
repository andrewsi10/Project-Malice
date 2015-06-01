package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

/**
 *  This class represents an Enemy in the game that will attack the Player when
 *  conditions are met. Uses basic AI programming
 *
 *  @author  Christopher Cheung
 *  @version May 31, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Enemy extends Character {

	private int travelTime;
	private int aggroDistance = 400;
	private int travelTimeScalar = 100;
	private int marginOfDelta = 30;
	private int minTravelTime = 4;
	private String projectile;

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
	public Enemy(String file) {
		super(new TextureAtlas(Gdx.files.internal(file)).getRegions());

		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);
		setExperience(20); // set amount of exp Player will receive
		setSpeed(3); // set speed of Enemy
		setReloadSpeed(getReloadSpeed() * 2); // set reload speed
		setHpColor( Color.RED );
		projectile = "EnemyBullet";
	}

	/**
	 * Constructor used for testing. Initialize experience to 20, speed to 3,
	 * and reloadSpeed to getReloadSpeed() * 2. Initialize direction to a random
	 * integer in the interval [0, 8) and travelTime to a random integer in the
	 * interval [minTravelTime, minTravelTime + travelTimeScalar).
	 */
	public Enemy() {
		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);
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
		if (!inRange(character)) {
			setRandomDirection();
			super.move(character, projectiles, time);
		}
		// moves towards the player
		else {
			setRandomDirection();

			float deltaX = character.getX() - getX();
			float deltaY = character.getY() - getY();
			int newDir = this.getDirection(deltaX, deltaY);
			if (newDir != -1) {
				setDirection(newDir);
				shoot(projectiles, deltaX, deltaY, time, projectile);
			}
			super.move(character, projectiles, time);
		}
	}

	/**
	 * If travelTime < 1, reassign direction to a random number in the interval
	 * [0, 8) and travelTime to a random number in the interval [minTravelTime,
	 * minTravelTime + travelTimeScalar). Otherwise decrement travelTime by one.
	 */
	public void setRandomDirection() {
		if (travelTime < 1) {
			setDirection((int) (Math.random() * 8));
			travelTime = (int) (minTravelTime + Math.random()
					* travelTimeScalar);
		}
		travelTime--;
	}

	/**
	 * Returns the Direction that the given parameters indicate
	 * 
	 * If both deltaX and deltaY are greater than the marginOfDelta,
	 * move diagonal
	 * else if one of them is greater than the marginOfDelta,
	 *   move straight in that direction
	 * else return -1
	 * 
	 * after above conditions are met,
	 * EAST when deltaX is positive and WEST when negative
	 * NORTH when deltaY is positive and SOUTH when negative
	 * 
	 * @param deltaX change in x
	 * @param deltaY change in y
	 * @return the Direction that the given parameters indicate
	 *         -1 if no direction is indicated
	 */
	private int getDirection( float deltaX, float deltaY ) {
        if ( deltaX > marginOfDelta && deltaY > marginOfDelta )
            return NORTHEAST;
        if ( deltaX > marginOfDelta && deltaY < -marginOfDelta )
            return SOUTHEAST;
		if ( deltaX < -marginOfDelta && deltaY < -marginOfDelta )
            return SOUTHWEST;
		if ( deltaX < -marginOfDelta && deltaY > marginOfDelta )
            return NORTHWEST;
        if ( Math.abs(deltaX) < marginOfDelta && deltaY > marginOfDelta )
            return NORTH;
        if ( deltaX > marginOfDelta && Math.abs(deltaY) < marginOfDelta )
            return EAST;
		if ( Math.abs(deltaX) < marginOfDelta && deltaY < -marginOfDelta )
            return SOUTH;
		if ( deltaX < -marginOfDelta && Math.abs(deltaX) < marginOfDelta )
            return WEST;
		return -1;
	}

	/**
	 * Determines whether the enemy is in shooting range of the character. Enemy
	 * is in shooting range when character is inside aggroDistance. Use the
	 * distance formula in relation to Enemy.
	 * 
	 * @param character
	 *            Character enemy will be compared to
	 * @return whether enemy is within aggroDistance
	 */
	public boolean inRange(Character character) {
		float dx = character.getX() - this.getX();
		float dy = character.getY() - this.getY();
		int distance = (int) Math.sqrt(dx * dx + dy * dy);
		return distance <= aggroDistance;
	}

	// ------------- Getters and Setters ----------------- //
	/**
	 * getter method for travelTimeScalar
	 * 
	 * @return travelTimeScalar
	 */
	public int getTravelTimeScalar() {
		return travelTimeScalar;
	}

	/**
	 * getter method for minTravelTime
	 * 
	 * @return minTravelTime
	 */
	public int getMinTravelTime() {
		return minTravelTime;
	}

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

	/**
	 * getter method for marginOfDelta
	 * 
	 * @return marginOfDelta
	 */
	public int getMarginOfDelta() {
		return marginOfDelta;
	}

	/**
	 * getter method for aggroDistance
	 * 
	 * @return aggroDistance
	 */
	public int getAggroDistance() {
		return aggroDistance;
	}

}
