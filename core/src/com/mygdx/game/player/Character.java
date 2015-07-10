package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.projectile.Projectile;
import com.mygdx.game.Audio;
import com.mygdx.game.Options;

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
public class Character extends AnimatedSprite {
	/**
	 * Variable used to determine the height in pixels of the status bars
	 */
	public static final int BARHEIGHT = 5;

	private GlyphLayout layout = new GlyphLayout();
	private Color hpColor = Color.GREEN;

	private int maxHp; // max health
	private int currentHp; // current health
	private int experience;
    private int level;
	private int expToLevel = -1;
	private int baseDmg = 20; // base damage
	private int randMod = 4; // random damage modifier
	private int reloadSpeed;
	private double previousTime = 0;
	private String projectile;

	/**
	 * The Character constructor for Enemy, which uses the same
	 * Array<AtlasRegion> to create the same Animation for each direction.
	 * 
	 * @param frames
	 *            the animation frame used for every direction
	 *
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
	public Character( Color hpColor, 
	                  int maxHp, 
	                  int experience, 
	                  int level,
	                  int speed, 
	                  int reloadSpeed, 
	                  String projectile, Animation... a ) {
	    this( hpColor );
        this.initializeAnimations( a );
	    this.hpColor = hpColor;
        this.load( maxHp, experience, level, speed, reloadSpeed );
        this.projectile = projectile;
	}
	
	public Character( Color hpColor ) {
        this.hpColor = hpColor;
	}

    /**
     * loads this Character based on parameters
     * @param maxHp
     * @param experience
     * @param level
     * @param speed
     * @param reloadSpeed
     * @param projectile
     * @param a
     */
    public void load( int maxHp, 
                      int experience, 
                      int level, 
                      int speed, 
                      int reloadSpeed )
    {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.experience = experience;
        this.level = level;
        setSpeed( speed );
        this.reloadSpeed = reloadSpeed;
        resetDirection();
    }

	// ---------------------Animation and Art ----------------------//

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
			Options.FONT.setColor(Color.MAROON);
			Options.FONT.draw(batch, getCurrentHp() + "/" + getMaxHp(), hpX + hpW, hpY);
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
		    Options.FONT.setColor(Color.MAGENTA);
			layout.setText(Options.FONT, "Level " + level);
			Options.FONT.draw(batch, "Level " + level, getX() - layout.width / 4,
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
	 * Puts a projectile that this Character shoots into the given Projectile 
	 * array
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
			float yDistance, long time) {
		if (time - previousTime >= reloadSpeed) {
			previousTime = time;
	        Audio.playAudio( projectile );
			projectiles.add( new Projectile( this, xDistance, yDistance,
                                            Options.atlas.get( projectile ) ) );
		}
	}
    /**
     * Puts a projectile that this Character shoots into the given Projectile 
     * array
     * 
     * @param projectiles ArrayList of Projectiles to add this Character's 
     *                     projectile to
     * @param dir          double representing the angle direction that the 
     *                         projectile should go
     * @param time         Current time in milliseconds to determine if this 
     *                         Character can shoot
     * @param spriteType   String representing this what type of sprite this 
     *                             Character is
     */
    public void shoot(ArrayList<Projectile> projectiles, double dir, long time) 
    {
        if (time - previousTime >= reloadSpeed) {
            previousTime = time;
            Audio.playAudio( projectile );
            projectiles.add( new Projectile( this, dir,
                                            Options.atlas.get( projectile ) ) );
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
		Audio.playAudio("levelup");
		double temp = getCurrentHp() / getMaxHp();
		increaseBdmg(2);
		increaseMaxHp(10);
		increaseCurrentHp((int) (10 * (temp + 1)));
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
	
	public void setProjectile( String s )
	{
	    projectile = s;
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
	public int getDamageDealt() {
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
				+ getDirection() + ", Reload; " + reloadSpeed + ", previousTime: "
				+ previousTime + ", moveSpeed: " + getSpeed();

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
