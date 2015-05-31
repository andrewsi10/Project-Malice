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
	public static final int NUMDIRECTIONS = 8;

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
	 * initializes the animations
	 */
	private void initializeAnimations() {
		upAnimation = new Animation(.2f, upFrames);
		rightAnimation = new Animation(.2f, rightFrames);
		downAnimation = new Animation(.2f, downFrames);
		leftAnimation = new Animation(.2f, leftFrames);
		stateTime = 0f;
	}


	/**
	 * uses direction to 
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

		if (!animation.isAnimationFinished(stateTime)) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0;
		}
		this.setRegion(animation.getKeyFrame(stateTime));
	}

	/**
	 * @param batch
	 * @param renderer
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
			getFont().setColor(Color.MAGENTA);
			layout.setText(getFont(), "Level " + level);
			getFont().draw(batch, "Level " + level, getX() - layout.width / 4,
					getY() + 1.8f * getHeight());
		}
	}

	// ----------------- Environment Interaction ------------------------//
	/**
	 * This method should be overridden for better functionality moves sprite
	 * according to its current direction
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
	 * Moves sprite according to its current direction
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


    public void translate(int dx, int dy) {
        if (dx == 0 || dy == 0) {
            translateX((float) (moveSpeed * dx));
            translateY((float) (moveSpeed * dy));
        } else {
            translateX((float) (moveSpeed * dx / Math.sqrt(2)));
            translateY((float) (moveSpeed * dy / Math.sqrt(2)));
        }
    }


    public void shoot(ArrayList<Projectile> projectiles, float xDistance,
            float yDistance, long time, String spriteType) {
        if ( time - previousTime >= reloadSpeed ) {
            previousTime = time;
            Projectile p = new Projectile(this, getDirection(), getDamage(),
                    spriteType, xDistance, yDistance);
            p.setSize(p.getWidth() / 3, p.getHeight() / 3);
            p.setPosition(getX() + getWidth() / 2 - p.getWidth() / 2, 
                          getY() + getHeight() / 2 - p.getHeight() / 2);

            projectiles.add(p);
        }
    }

    // --------------------Setters and Incrementers --------------------//
   
    /**
     * increases the level of the character, as well as increases maxHP and bullet damage
     */
    public void increaseCurrentLevel()
    {
        // might need balancing
        level++;
        MimicGdx.playAudio(MimicGdx.levelUp);
        double temp = getCurrentHp() / getMaxHp();
        increaseBdmg( 2 );
        increaseMaxHp( 10 );
        increaseCurrentHp( (int) ( 10 * (temp + 1) ) );
    }
    
    public void setDirection(int dir) {
        direction = dir % NUMDIRECTIONS;
    }


	public void increaseMaxHp(int i) {
		maxHp += i;
	}

	public void increaseCurrentHp(int i) {
		if (currentHp + i > maxHp) {
			currentHp = maxHp;
		} else {
			currentHp += i;
		}
	}

	public boolean isDead() {
		return currentHp <= 0;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void setExpToLevel(int exp) {
		this.expToLevel = exp;
	}

	public void takeDamage(int damage) {
		currentHp -= damage;
	}

	public void increaseBdmg(int i) {
		baseDmg += i;
	}

	public void setSpeed(int speed) {
		moveSpeed = speed;
	}

	public void setReloadSpeed(int newReloadSpeed) {
		reloadSpeed = newReloadSpeed;
	}

	// -----------------------Getters -----------------//

	public BitmapFont getFont() {
		return font;
	}

	public void setLevel(int newLevel) {
		level = newLevel;
	}

	public void setHpColor(Color newColor) {
		hpColor = newColor;
	}

	public int getCurrentLevel() {
		return level;
	}

	public int getDirection() {
		return direction;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getExperience() {
		return experience;
	}

	public int getExpToLevel() {
		return expToLevel;
	}

	public int getBDmg() {
		return baseDmg;
	}

	public int getDamage() {
		return baseDmg + (int) (randMod * Math.random());
	}

	public int getReloadSpeed() {
		return reloadSpeed;
	}

	public float getSpeed() {
		return moveSpeed;
	}

	// --------------------For Testing --------------//
	@Override
	public String toString() {
		String s = "HP: " + currentHp + "/" + maxHp + "; Base Damage: "
				+ baseDmg + ", RandomMod: " + randMod + ", " + "Direction: "
				+ direction + ", Reload; " + reloadSpeed + ", previousTime: "
				+ previousTime + ", moveSpeed: " + moveSpeed;

		return s;
	}

	/**
	 * Constructor for testing. Initializes Character with position 0, 0 and
	 * moveSpeed 10
	 */
	public Character() {
		setSpeed(10);
		setLevel(1);
	}

}
