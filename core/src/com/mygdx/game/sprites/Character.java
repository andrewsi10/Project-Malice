package com.mygdx.game.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Projectile;
import com.mygdx.game.screens.StagedScreen;
import com.mygdx.game.sprites.SpriteData.Stats;
import com.mygdx.game.Audio;

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
public abstract class Character extends StatsSprite {
	/**
	 * Variable used to determine the height in pixels of the status bars
	 */
	public static final int BARHEIGHT = 7;
	public static final int BARWIDTH = 80;

	private Color hpColor;
	
	private Label levelLabel, hpLabel;

	private double previousTime = 0;
	private String projectile;
    private Animation projectileAnimation;
	
	/**
	 * Creates a Character with stats based on the parameters
	 * 
	 * Used by Enemy
	 * 
	 * @param hpColor Color of hp bar
     * @param maxHp for both currentHp and maxHp
	 * @param experience
	 * @param level
	 * @param speed
	 * @param reloadSpeed
	 * @param projectile String representing the projectile name
	 * @param proj Animation of Projectile
	 * @param a Animations for this Character
	 */
	public Character( Skin skin, Color hpColor, 
	                  int maxHp, 
	                  int experience, 
	                  int level,
	                  int speed, 
	                  int reloadSpeed, 
	                  String projectile, Animation proj, Animation... a ) {
	    this( skin, hpColor );
        this.initializeAnimations( a );
        this.load( maxHp, experience, level, speed, reloadSpeed );
        this.projectile = projectile;
        this.projectileAnimation = proj;
	}
	
	/**
	 * Creates an empty Character setting only the hp bar color
	 * 
	 * Used by Player to allow constant changing of stats (may be buggy though)
	 * 
     * @param hpColor Color of hp bar
	 */
	public Character( Skin skin, Color hpColor ) {
        this.hpColor = hpColor;
        levelLabel = new Label( "", skin, "label" );
        levelLabel.setColor( Color.MAGENTA );
        hpLabel = new Label( "", skin, "label" );
        hpLabel.setColor( Color.MAROON );
        
        StagedScreen.scaleLabels( levelLabel, hpLabel );
	}

    /**
     * Sets this Character's stats based on parameters
     * @param maxHp for both currentHp and maxHp
     * @param experience
     * @param level
     * @param speed
     * @param reloadSpeed
     */
    public void load( int maxHp, 
                      int experience, 
                      int level, 
                      int speed, 
                      int reloadSpeed )
    {
        SpriteData s = getSpriteData();
        s.setStat( Stats.MAXHP, maxHp );
        s.resetHp();
        s.setStat( Stats.EXPERIENCE, experience );
        s.setStat( Stats.LEVEL, level );
        setSpeed( speed );
        s.setStat( Stats.RELOADSPEED, reloadSpeed );
        resetDirection();

        updateLabels();
    }

	// --------------------- Art ----------------------//

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
		float hpW = BARWIDTH;
		float hpH = BARHEIGHT;
		float hpX = getCenterX() - hpW / 2;
		float hpY = getY() - BARHEIGHT;

		// note: merge if statements in order to make them appear at same time
		// suggestion: should we make exp a vertical bar or make hp above
		// sprite?
		if ( hpLabel.isVisible() ) { // currenHp < maxHp
			renderer.setColor( Color.GRAY );
			renderer.rect( hpX, hpY, hpW, hpH );
			renderer.setColor( hpColor );
			renderer.rect( hpX + 1, hpY + 1, 
			        ( hpW - 2 ) * getSpriteData().getHpRatio(), 
			        hpH - 2 );
			hpLabel.setPosition( hpX + hpW + 2, hpY + BARHEIGHT / 2 );
			hpLabel.draw( batch, 1 );
		}
		// if experience is between (0,expToLevel)
		if ( getSpriteData().expIsInRange() ) {
			hpY -= BARHEIGHT + 3;
			renderer.setColor(Color.GRAY);
			renderer.rect(hpX, hpY, hpW, hpH);
			renderer.setColor(Color.CYAN);
			renderer.rect(hpX + 1, hpY + 1, (hpW - 2) * getSpriteData().getExpRatio(), hpH - 2);
		}
		if ( levelLabel.isVisible() ) { // level > 0
		    levelLabel.setPosition( getCenterX() - levelLabel.getPrefWidth() / 2, 
		                            getY() + 1.8f * getHeight() );
		    levelLabel.draw( batch, 1 );
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
	public abstract void move( Character character, 
	                           ArrayList<Entity> entities,
	                           long time );
	
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
    public void shoot( ArrayList<Entity> entities, double dir, long time ) 
    {
        if ( time - previousTime >= getSpriteData().getReloadSpeed() ) {
            previousTime = time;
            Audio.playAudio( projectile );
            entities.add( new Projectile( this, dir, projectileAnimation ) );
        }
    }
    
    /**
     * Called when Character dies
     * @param entities
     */
    public abstract void die( ArrayList<Entity> entities );

	// --------------------Setters and Incrementers --------------------//

	/**
	 * Increases the level of the character and uses MimicGdx to play the sound
	 * file for leveling up. Increase baseDmg by 2 and maxHp by 10. Increase the
	 * currentHp to maintain the same ratio of currentHp to maxHp prior to
	 * increasing maxHp.
	 */
	public void increaseCurrentLevel() {
		// might need balancing
	    this.levelUp( true );
        updateLabels();
        Audio.playAudio( "levelup" );
	}

	/**
	 * increases maxHp by the input value
	 * 
	 * @param i
	 *            integer to increase maxHp by
	 */
	public void increaseMaxHp( int i ) {
	    getSpriteData().increaseStat( Stats.MAXHP, i );
        updateHpLabel();
	}

	/**
	 * increases currentHp by the input value. If currentHp exceeds maxHp,
	 * currentHp is reduced to equal maxHp.
	 * 
	 * @param i
	 *            absolute value of integer to increase currentHp by
	 */
	public void heal( int i ) {
	    getSpriteData().increaseHp( i );
        updateHpLabel();
	}

    /**
     * decrements currentHp by damage taken
     * 
     * @param damage
     *            absolute value of damage taken
     */
    public void takeDamage( int damage ) {
        heal( -damage );
    }

	/**
	 * returns whether character is dead
	 * 
	 * @return true if currentHp <= 0. False otherwise.
	 */
	public boolean isDead() {
		return getSpriteData().getHp() <= 0;
	}

	/**
	 * increments baseDmg by input value
	 * 
	 * @param i
	 *            value to increase baseDmg by
	 */
	public void increaseBdmg(int i) {
	    getSpriteData().increaseStat( Stats.ATTACK, i );
	}
	
	/**
	 * Sets this Character's projectile type
	 * @param s String representing the new projectile type
	 */
	public void setProjectile( String s, Animation a ) {
	    projectile = s;
	    projectileAnimation = a;
	}
	
	// ----------------------- Updaters ---------------------//
	
	public void updateLabels() {
	    this.updateLevelLabel();
	    this.updateHpLabel();
	}
	
	/**
	 * Updates the text and visibility of LevelLabel
	 */
	private void updateLevelLabel() {
	    int level = getSpriteData().getLevel();
        levelLabel.setText( "Level " + level );
        levelLabel.setVisible( level > 0 );
	}

    /**
     * Updates the text and visibility of hpLabel
     */
	private void updateHpLabel() {
	    int currentHp = getSpriteData().getHp();
	    int maxHp = getSpriteData().getMaxHp();
        hpLabel.setText( currentHp + "/" + maxHp );
        hpLabel.setVisible( currentHp < maxHp );
	}

	// -----------------------Getters -----------------//

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
		String s = "Direction: " + getDirection();
		for ( Stats stat : Stats.values() ) {
		    s += stat + ": " + getSpriteData().getStat( stat );
		}
		s += "Previous Time: " + previousTime;

		return s;
	}

	/**
	 * Constructor for testing. Initializes Character with position (0, 0),
	 * moveSpeed 10, and level 1.
	 */
	public Character() {
	    SpriteData s = getSpriteData();
		setSpeed(10);
		s.setStat( Stats.LEVEL, 1 );
		s.setStat( Stats.RELOADSPEED, 1000 );
	}

    /**
     * getter method for currentHp
     * 
     * @return currentHp
     */
    public int getCurrentHp() {
        return getSpriteData().getHp();
    }

    /**
     * getter method for baseDmg
     * 
     * @return baseDmg
     */
    public int getBDmg() {
        return getSpriteData().getAttack();
    }

}
