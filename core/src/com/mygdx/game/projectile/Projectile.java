package com.mygdx.game.projectile;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.player.AnimatedSprite;
import com.mygdx.game.player.Character;

/**
 *  Represents a Projectile in the game. Always moves in a straight line based
 *  on a given slope.
 *
 *  @author  Andrew Si 
 *  @author  Other contributors: Christopher Cheung, Nathan Lui
 *  @version May 31, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Projectile extends AnimatedSprite
{
    private Character myCharacter;

	/**
	 * Constructs a Projectile class
	 * 
	 * DistanceX and distanceY determines the slope of the line that this 
	 * projectile should be moving on.
	 * This projectile will continue to move on that line until removed from
	 * game.
	 * 
	 * @param c Character that this Projectile originates from.
	 * @param damage The damage that this Projectile will deal
	 * @param distanceX the x distance used to determine slope
	 * @param distanceY the y distance used to determine slope
     * @param a continuous Animation for this Projectile
	 */
	public Projectile( Character c, double dir, Animation a )
	{
	    super( dir, a );
        this.myCharacter = c;
        
        setSpeed( 8 );
        setSize(getWidth() / 3, getHeight() / 3);
        setPosition(c.getX() + c.getWidth() / 2 - getWidth() / 2, 
                    c.getY() + c.getHeight() / 2 - getHeight() / 2);
	}
    public Projectile(Character c, float distanceX, float distanceY, Animation a)
    {
        this( c, 90 - Math.toDegrees( Math.atan2( distanceY, distanceX ) ), a );
    }
    
	/**
	 * Constructs this Projectile without animations, For testing only
	 * 
	 * Projectile will be unable to move.
	 * 
	 * @param x -coordinate as starting point
	 * @param y -coordinate as starting point
	 * @param w width of the projectile
	 * @param h height of the projectile
	 * @param d damage that this projectile deals
	 */
	public Projectile( float x, float y, float w, float h, int d )
	{
	    setBounds( x, y, w, h );
	    setDirection( 0 );
	}

	/**
	 * Moves this projectile on the line with slope based on the given 
	 * xDistance and yDistance from constructor
	 * Updates animation based on time
	 * 
	 * Cannot be JUnit tested due to animations
	 */
	public void move()
	{
	    translate();
	    setAnimations();
	}
    
    /**
     * Returns whether this projectile hits a given character based on bounding rectangles
     * if Character is the same as the one that spawned this projectile,
     * this method should return false
     * 
     * Will call c.takeDamage( int damage ) for the Character in the parameter
     * 
     * @param c Character to check with
     * @return whether this projectile collides with Character
     */
    public boolean hitCharacter( Character c )
    {
        if ( c == this.myCharacter ) return false;
        boolean overlaps = c.getBoundingRectangle().overlaps( 
                            this.getBoundingRectangle() );
        if ( overlaps )
            c.takeDamage( myCharacter.getDamageDealt() );
        return overlaps;
    }
    
    // for expansion: returns whether two sprites are of the same Team, should use strings if more than two teams
//    private boolean sameTeam( Character c1, Character c2 )
//    {
//        return ( c1 instanceof Enemy && c2 instanceof Enemy )
//            || ( c1 instanceof Player && c2 instanceof Player );
//    }
}