package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;

public abstract class Character extends Sprite {
	public static final int NORTH = 0;
	public static final int NORTHEAST = 1;
	public static final int EAST = 2;
	public static final int SOUTHEAST = 3;
	public static final int SOUTH = 4;
	public static final int SOUTHWEST = 5;
	public static final int WEST = 6;
	public static final int NORTHWEST = 7;
	public static final int NUMDIRECTIONS = 8;

	private int maxHp = 50; // max health
	private int currentHp = maxHp; // current health
	private int baseDmg = 10; // base damage
	private int randMod = 4; // random damage modifier
	private int direction = -1;
	private int reloadSpeed = 500;
	private double previousTime = 0;
	private float moveSpeed;

	private TextureAtlas textureAtlas;

	float stateTime;
	Animation upAnimation;
	Animation rightAnimation;
	Animation downAnimation;
	Animation leftAnimation;
	Array<AtlasRegion> upFrames;
	Array<AtlasRegion> rightFrames;
	Array<AtlasRegion> downFrames;
	Array<AtlasRegion> leftFrames;
	private String type;

	// constructor for enemies
	public Character(Array<AtlasRegion> frames) {
		upFrames = frames;
		rightFrames = frames;
		downFrames = frames;
		leftFrames = frames;
		set(new Sprite(frames.get(0)));
		initializeAnimations();
	}

	// constructor for player
	public Character(Array<AtlasRegion> up, Array<AtlasRegion> right,
			Array<AtlasRegion> down, Array<AtlasRegion> left) {
		upFrames = up;
		rightFrames = right;
		downFrames = down;
		leftFrames = left;
		set(new Sprite(down.get(0)));
		initializeAnimations();
	}
	
	//initializes the animations
	void initializeAnimations() {
		upAnimation = new Animation(.2f, upFrames);
		rightAnimation = new Animation(.2f, rightFrames);
		downAnimation = new Animation(.2f, downFrames);
		leftAnimation = new Animation(.2f, leftFrames);
		stateTime = 0f;
	}
    
    /**
     * Sets animation based on direction
     * @param dir
     */
    private void setAnimations( int dir )
    {
        Animation animation = downAnimation;
        switch ( dir )
        {
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

	public abstract void move( Character character, 
	                           ArrayList<Projectile> projectiles, 
	                           long time);
	
	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	protected void move(int dir) {

		setDirection(dir);
		int dx = 0;
		int dy = 0;
		switch (dir) {
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
		setAnimations( dir );
		translate(dx, dy);
	}

    protected void translate(int dx, int dy) {
        if (dx == 0 || dy == 0) {
            translateX((float) (moveSpeed * dx));
            translateY((float) (moveSpeed * dy));
        } else {
            translateX((float) (moveSpeed * dx / Math.sqrt(2)));
            translateY((float) (moveSpeed * dy / Math.sqrt(2)));
        }
    }
    
    public void drawHp( ShapeRenderer renderer )
    {
        if ( currentHp < maxHp && !this.isDead() ) {
            float hpW = getWidth();
            float hpH = 5;
            float hpX = getX();
            float hpY = getY() - hpH;
            renderer.setColor( Color.GRAY );
            renderer.rect( hpX, hpY, hpW, hpH );
            renderer.setColor( Color.GREEN );
            renderer.rect( hpX + 1, hpY + 1, ( hpW - 2 ) * currentHp / maxHp, hpH - 2 );
        }
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

	public void takeDamage(int damage) {
		currentHp -= damage;
	}

	public void increaseBdmg(int i) {
		baseDmg += i;
	}

	public void shoot(ArrayList<Projectile> projectiles, float xDistance,
			float yDistance, long time, String spriteType) {
		if ( time - previousTime >= reloadSpeed ) {
			previousTime = time;
			Projectile p = new Projectile(this, getDirection(), getDamage(),
					spriteType, xDistance, yDistance);
			p.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 3);
			p.setSize(p.getWidth() / 3, p.getHeight() / 3);

			projectiles.add(p);
		}
	}

	public void setSpeed(int speed) {
		moveSpeed = speed;
	}

    public void setDirection(int dir) {
        if (dir >= 0 && dir < NUMDIRECTIONS) {
            direction = dir;
        }
    }

	public void setReloadSpeed(int newReloadSpeed) {
		reloadSpeed = newReloadSpeed;
	}

	public int getReloadSpeed() {
		return reloadSpeed;
	}

	public TextureAtlas getAtlas() {
		return textureAtlas;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getDamage() {
		return baseDmg + (int) (randMod * Math.random());
	}

	public int getDirection() {
		return direction;
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
}
