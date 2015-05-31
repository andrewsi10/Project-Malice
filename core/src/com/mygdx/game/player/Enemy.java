package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Enemy extends Character {

	private int travelTime;
	private int aggroDistance = 400;
	private int travelTimeScalar = 100;
	private int marginOfDelta = 30;
	private int minTravelTime = 4;
	private String projectile;

	/**
	 * Constructor
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
		projectile = "EnemyBullet";
	}

	/**
	 * Constructor used for testing
	 */
	public Enemy() {
		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);
		setExperience(20); // set amount of exp Player will receive
		setSpeed(3); // set speed of Enemy
		setReloadSpeed(getReloadSpeed() * 2); // set reload speed
	}

	/**
	 * moves Enemy towards the player if within aggroDistance otherwise moves
	 * randomly
	 */
	@Override
	public void move(Character character, ArrayList<Projectile> projectiles,
			long time) {
		if (!inRange(character)) {
			setRandomDirection();
			super.move(character, projectiles, time); // note % NUMDIRECTION if
														// errors
		}
		// moves towards the player
		else {
			setRandomDirection();

			float deltaX = character.getX() - getX();
			float deltaY = character.getY() - getY();
			int newDir = this.getDirection(-deltaX, -deltaY);
			if (newDir != -1) {
				setDirection(newDir);
				shoot(projectiles, deltaX, deltaY, time, projectile);
			}
			super.move(character, projectiles, time);
		}
	}

	/**
	 * if travelTime is less than one, sets a new, random direction for the
	 * enemy
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
	 * 
	 * @param deltaX
	 * @param deltaY
	 * @return
	 */
	private int getDirection(float deltaX, float deltaY) {
		if (deltaX < -marginOfDelta && deltaY < -marginOfDelta)
			return NORTHEAST;
		if (deltaX < -marginOfDelta && deltaY > marginOfDelta)
			return SOUTHEAST;
		if (deltaX > marginOfDelta && deltaY > marginOfDelta)
			return SOUTHWEST;
		if (deltaX > marginOfDelta && deltaY < -marginOfDelta)
			return NORTHWEST;
		if (Math.abs(deltaX) < marginOfDelta && deltaY < -marginOfDelta)
			return NORTH;
		if (deltaX < -marginOfDelta && Math.abs(deltaX) < marginOfDelta)
			return EAST;
		if (Math.abs(deltaX) < marginOfDelta && deltaY > marginOfDelta)
			return SOUTH;
		if (deltaX > marginOfDelta && Math.abs(deltaY) < marginOfDelta)
			return WEST;
		return -1;
	}

	/**
	 * determines whether the enemy is in shooting range of the character,
	 * determined by the variable aggroDistance. This method uses the Distance
	 * Formula.
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
	public int getTravelTimeScalar() {
		return travelTimeScalar;
	}

	public int getMinTravelTime() {
		return minTravelTime;
	}

	public int getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(int time) {
		travelTime = time;
	}

	public int getMarginOfDelta() {
		return marginOfDelta;
	}

	public int getAggroDistance() {
		return aggroDistance;
	}

}
