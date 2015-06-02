package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;
import com.mygdx.game.MimicGdx;

/**
 *  This class represents a sprite in the game with animations and movement
 *
 *  @author  Christopher Cheung
 *  @author  Other contributors: Andrew Si, Nathan Lui
 *  @version May 31, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Character extends Sprite {
	/**
	 * constant variables which represent 8 various directions
	 */
	public static final int NORTH = 0;
	public static final int NORTHEAST = 1;
	public static final int EAST = 2;
	public static final int SOUTHEAST = 3;
	public static final int SOUTH = 4;
	public static final int SOUTHWEST = 5;
	public static final int WEST = 6;
	public static final int NORTHWEST = 7;
	/**
	 * The Number of Directions
	 */
	public static final int NUMDIRECTIONS = 8;

	/**
	 * Variable used to determine the height in pixels of the status bars
	 */
	public static final int BARHEIGHT = 5;

	/**
	 * variables used to hold animation frames and initialize animations
	 */
	private float stateTime;
	private Animation upAnimation;
	private Animation rightAnimation;
	private Animation downAnimation;
	private Animation leftAnimation;
	private Array<AtlasRegion> upFrames;
	private Array<AtlasRegion> rightFrames;
	private Array<AtlasRegion> downFrames;
	private Array<AtlasRegion> leftFrames;

	private BitmapFont font;

	private int level = 0;
	private GlyphLayout layout = new GlyphLayout();
	private Color hpColor = Color.GREEN;

	private int direction = -1;
	private int maxHp = 50; // max health
	private int currentHp = maxHp; // current health
	private int experience = 0;
	private int expToLevel = -1;
	private int baseDmg = 10; // base damage
	private int randMod = 4; // random damage modifier
	private int reloadSpeed = 500;
	private double previousTime = 0;
	private float moveSpeed;

	/**
	 * The Character constructor for Enemy, which uses the same
	 * Array<AtlasRegion> to create the same Animation for each direction.
	 * 
	 * @param frames
	 *            the animation frame used for every direction
	 */
	public Character(Array<AtlasRegion> frames) {
		this(frames, frames, frames, frames);
	}

	/**
	 * The Character constructor for Player, which takes four Array<AtlasRegion>
	 * and uses them to initialize the animations for each direction. Also
	 * initializes font to display information about the Character on-screen.
	 * Initial frame to be drawn for Character is set.
	 * 
	 * @param up
	 *            north-ward animation frames
	 * @param right
	 *            east-ward animation frames
	 * @param down
	 *            south-ward animation frames
	 * @param left
	 *            west-ward animation frames
	 */
	public Character(Array<AtlasRegion> up, Array<AtlasRegion> right,
			Array<AtlasRegion> down, Array<AtlasRegion> left) {
		upFrames = up;
		rightFrames = right;
		downFrames = down;
		leftFrames = left;
		font = new BitmapFont();
		set(new Sprite(down.get(0)));
		initializeAnimations();
	}

	// ---------------------Animation and Art ----------------------//
	/**
	 * Initialize upAnimation, rightAnimation, downAnimation, and leftAnimation
	 * with upFrames, rightFrames, downFrames, and leftFrames respectively. Each
	 * animation should have a frame duration of .2 seconds. Initialize
	 * stateTime to 0f.
	 */
	private void initializeAnimations() {
		upAnimation = new Animation(.2f, upFrames);
		rightAnimation = new Animation(.2f, rightFrames);
		downAnimation = new Animation(.2f, downFrames);
		leftAnimation = new Animation(.2f, leftFrames);
		stateTime = 0f;
	}

	/**
	 * Uses the value of direction to initialize a new animation to either
	 * upAnimation, rightAnimation, downAnimation, or leftAnimaion, respective
	 * to the values of the constant variables provided two represent the 8
	 * different directions. Use stateTime to display the correct keyFrame and
	 * update stateTime while the animation is not finished. Once the animation
	 * is finished, set stateTime to 0.
	 */
	private void setAnimations() {
		Animation animation = downAnimation;
		switch (direction) {
		case NORTH:
			animation = upAnimation;
			break;
		case NORTHEAST:
		case SOUTHEAST:
		case EAST:
			animation = rightAnimation;
			break;
		case SOUTH:
			animation = downAnimation;
			break;
		case NORTHWEST:
		case SOUTHWEST:
		case WEST:
			animation = leftAnimation;
			break;
		}

		if ( !animation.isAnimationFinished( stateTime ) ) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0;
		}
		this.setRegion( animation.getKeyFrame( stateTime ) );
	}

	/**
	 * Draws the stats of the Character around the sprite.
	 * 
	 * Each bar must have a height of BARHEIGHT and appear below the Character
	 * with hp bar ontop of the exp bar
	 * hp bar and exp bar must only be drawn when hp and exp is less than their 
	 * maximum (maxHp and expToLevel respectively)
	 * 
	 * Uses the BitmapFont to write the level of the Character above the sprite
	 * and to write the hp values next to the hp bar
	 * 
	 * @param batch Batch to draw the words with
	 * @param renderer the ShapeRenderer used to draw rectangles representing 
	 *                 the bars
	 */
	public void drawBars(Batch batch, ShapeRenderer renderer) {
		float hpW = getWidth();
		float hpH = BARHEIGHT;
		float hpX = getX();
		float hpY = getY() - BARHEIGHT;

		// note: merge if statements in order to make them appear at same time
		// suggestion: should we make exp a vertical bar or make hp above
		// sprite?
		if (getCurrentHp() < getMaxHp()) {
			renderer.setColor(Color.GRAY);
			renderer.rect(hpX, hpY, hpW, hpH);
			renderer.setColor(hpColor);
			renderer.rect(hpX + 1, hpY + 1, (hpW - 2) * getCurrentHp()
					/ getMaxHp(), hpH - 2);
			font.setColor(Color.MAROON);
			font.draw(batch, getCurrentHp() + "/" + getMaxHp(), hpX + hpW, hpY);
		}
		if (getExperience() < getExpToLevel() && getExperience() > 0) {
			hpY -= BARHEIGHT - 2;
			renderer.setColor(Color.GRAY);
			renderer.rect(hpX, hpY, hpW, hpH);
			renderer.setColor(Color.CYAN);
			renderer.rect(hpX + 1, hpY + 1, (hpW - 2) * getExperience()
					/ getExpToLevel(), hpH - 2);
		}
		if (level > 0) {
			font.setColor(Color.MAGENTA);
			layout.setText(font, "Level " + level);
			font.draw(batch, "Level " + level, getX() - layout.width / 4,
					getY() + 1.8f * getHeight());
		}
	}

	// ----------------- Environment Interaction ------------------------//
	/**
	 * This method should be overridden for better functionality moves sprite
	 * according to its current direction
	 * 
	 * This method should also be called by overridden method to update animations
	 * 
	 * @param character
	 *            for sub classes to interact with environment
	 * @param projectiles
	 *            list of all projectiles, given to add this Character's
	 *            projectiles to environment (for shooting functions)
	 * @param time
	 *            Time in game (used in order to determine delays in moving or
	 *            shooting)
	 */
	public void move(Character character, ArrayList<Projectile> projectiles,
			long time) {
		move();
		setAnimations();
	}

	/**
	 * Moves sprite according to its current direction. Then translates
	 * Character in the appropriate direction by one unit using the
	 * translate(int, int) method.
	 */
	public void move() {

		int dx = 0;
		int dy = 0;
		switch (direction) {
		case NORTHWEST:
			dx = -1;
		case NORTH:
			dy = 1;
			break;
		case NORTHEAST:
			dy = 1;
		case EAST:
			dx = 1;
			break;
		case SOUTHEAST:
			dx = 1;
		case SOUTH:
			dy = -1;
			break;
		case SOUTHWEST:
			dy = -1;
		case WEST:
			dx = -1;
			break;
		}
		translate(dx, dy);
	}

	/**
	 * uses translateX and translateY to move Character by dx and dy multiplied
	 * by moveSpeed units respectively. If Character moves along a diagonal
	 * (neither dx nor dy are equal to 0), divide the value inside both
	 * translateX and translateY by Math.sqrt(2).
	 * 
	 * @param dx
	 *            change in x-coordinate
	 * @param dy
	 *            change in y-coordinate
	 */
	public void translate(int dx, int dy) {
		if (dx == 0 || dy == 0) {
			translateX((float) (moveSpeed * dx));
			translateY((float) (moveSpeed * dy));
		} else {
			translateX((float) (moveSpeed * dx / Math.sqrt(2)));
			translateY((float) (moveSpeed * dy / Math.sqrt(2)));
		}
	}

	/**
	 * Puts a projectile that this Character shoots into the given Projectile 
	 * array
	 * 
	 * 
	 * 
	 * @param projectiles ArrayList of Projectiles to add this Character's 
	 *                     projectile to
	 * @param xDistance the x distance for the slope that the projectile should 
	 *                     take
	 * @param yDistance the y distance for the slope that the projectile should 
     *                     take
	 * @param time         Current time in milliseconds to determine if this 
	 *                         Character can shoot
	 * @param spriteType   String representing this what type of sprite this 
	 *                             Character is
	 */
	public void shoot(ArrayList<Projectile> projectiles, float xDistance,
			float yDistance, long time, String spriteType) {
		if (time - previousTime >= reloadSpeed) {
			previousTime = time;
			Projectile p = new Projectile(this, getDamage(),
					spriteType, xDistance, yDistance);

			projectiles.add(p);
		}
	}

	// --------------------Setters and Incrementers --------------------//

	/**
	 * Increases the level of the character and uses MimicGdx to play the sound
	 * file for leveling up. Increase baseDmg by 2 and maxHp by 10. Increase the
	 * currentHp to maintain the same ratio of currentHp to maxHp prior to
	 * increasing maxHp.
	 */
	public void increaseCurrentLevel() {
		// might need balancing
		level++;
		MimicGdx.playAudio(MimicGdx.levelUp);
		double temp = getCurrentHp() / getMaxHp();
		increaseBdmg(2);
		increaseMaxHp(10);
		increaseCurrentHp((int) (10 * (temp + 1)));
	}

	/**
	 * Setter method for setDirection. Input will be modded by 8 so that
	 * direction will be a valid integer in the range [0, 8)
	 * 
	 * @param dir
	 */
	public void setDirection(int dir) {
		direction = dir % NUMDIRECTIONS;
	}

	/**
	 * increases maxHp by the input value
	 * 
	 * @param i
	 *            integer to increase maxHp by
	 */
	public void increaseMaxHp(int i) {
		maxHp += i;
	}

	/**
	 * increases currentHp by the input value. If currentHp exceeds maxHp,
	 * currentHp is reduced to equal maxHp.
	 * 
	 * @param i
	 *            integer to increase currentHp by
	 */
	public void increaseCurrentHp(int i) {
		if (currentHp + i > maxHp) {
			currentHp = maxHp;
		} else {
			currentHp += i;
		}
	}

	/**
	 * returns whether character is dead
	 * 
	 * @return true if currentHp <= 0. False otherwise.
	 */
	public boolean isDead() {
		return currentHp <= 0;
	}

	/**
	 * setter for experience
	 * 
	 * @param experience
	 *            new value for experience
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * setter for expToLevel
	 * 
	 * @param exp
	 *            new value for expToLevel
	 */
	public void setExpToLevel(int exp) {
		this.expToLevel = exp;
	}

	/**
	 * decrements currentHp by damage taken
	 * 
	 * @param damage
	 *            value of damage taken
	 */
	public void takeDamage(int damage) {
		currentHp -= damage;
	}

	/**
	 * increments baseDmg by input value
	 * 
	 * @param i
	 *            value to increase baseDmg by
	 */
	public void increaseBdmg(int i) {
		baseDmg += i;
	}

	/**
	 * setter method for moveSpeed
	 * 
	 * @param speed
	 *            new value for moveSpeed
	 */
	public void setSpeed(int speed) {
		moveSpeed = speed;
	}

	/**
	 * setter method for reloadSpeed
	 * 
	 * @param newReloadSpeed
	 *            new value of reloadSpeed
	 */
	public void setReloadSpeed(int newReloadSpeed) {
		reloadSpeed = newReloadSpeed;
	}

	/**
	 * setter method for level
	 * 
	 * @param newLevel
	 *            new value of level
	 */
	public void setLevel(int newLevel) {
		level = newLevel;
	}

	/**
	 * setter method for hpColor
	 * 
	 * @param newColor
	 *            new color of hpColor
	 */
	public void setHpColor(Color newColor) {
		hpColor = newColor;
	}

	// -----------------------Getters -----------------//

	/**
	 * getter method for level
	 * 
	 * @return level
	 */
	public int getCurrentLevel() {
		return level;
	}

	/**
	 * getter method for direction
	 * 
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * getter method for maxHp
	 * 
	 * @return maxHp
	 */
	public int getMaxHp() {
		return maxHp;
	}

	/**
	 * getter method for currentHp
	 * 
	 * @return currentHp
	 */
	public int getCurrentHp() {
		return currentHp;
	}

	/**
	 * getter method for experience
	 * 
	 * @return experience
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * getter method for expToLevel
	 * 
	 * @return expToLevel
	 */
	public int getExpToLevel() {
		return expToLevel;
	}

	/**
	 * getter method for baseDmg
	 * 
	 * @return baseDmg
	 */
	public int getBDmg() {
		return baseDmg;
	}

	/**
	 * damage dealt by Character
	 * 
	 * @return baseDmg with an added random value scaled by randMod
	 */
	public int getDamage() {
		return baseDmg + (int) (randMod * Math.random());
	}

	/**
	 * getter method for reloadSpeed
	 * 
	 * @return reloadSpeed
	 */
	public int getReloadSpeed() {
		return reloadSpeed;
	}

	/**
	 * getter method for moveSpeed
	 * 
	 * @return moveSpeed;
	 */
	public float getSpeed() {
		return moveSpeed;
	}

	// --------------------For Testing --------------//
	/**
	 * @see java.lang.Object#toString()
	 * 
	 * Returns a String that represents this Character:
	 * HP: currentHp / maxHp; Base Damage, RandomMod, Direction, reloadSpeed, 
	 * previousTime, moveSpeed
	 * 
	 * @return a String that represents this Character
	 */
	@Override
	public String toString() {
		String s = "HP: " + currentHp + "/" + maxHp + "; Base Damage: "
				+ baseDmg + ", RandomMod: " + randMod + ", " + "Direction: "
				+ direction + ", Reload; " + reloadSpeed + ", previousTime: "
				+ previousTime + ", moveSpeed: " + moveSpeed;

		return s;
	}

	/**
	 * Constructor for testing. Initializes Character with position (0, 0),
	 * moveSpeed 10, and level 1.
	 */
	public Character() {
		setSpeed(10);
		setLevel(1);
	}

}
