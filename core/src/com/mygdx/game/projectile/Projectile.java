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

	final private int damage;


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
	 * @param type the type of projectile, used for file input to get animation pictures
	 * @param distanceX the x distance used to determine slope
	 * @param distanceY the y distance used to determine slope
	 */
	public Projectile(Character c, int damage, 
	                        float distanceX, float distanceY, Animation a)
	{
	    super( Math.toDegrees( Math.atan2( distanceX, distanceY ) ), a );
        this.myCharacter = c;

//		TextureRegion[][] temp = TextureRegion.split( projectileTexture,
//				projectileTexture.getWidth() / col,
//				projectileTexture.getHeight() / row );
//		frames = new TextureRegion[col * row];
//
//		int index = 0;
//		for ( int i = 0; i < row; i++ )
//		{
//			for ( int j = 0; j < col; j++ )
//			{
//				frames[index++] = temp[i][j];
//			}
//		}
		
		this.damage = damage;
		setSpeed( 8 );
        setSize(getWidth() / 3, getHeight() / 3);
        setPosition(c.getX() + c.getWidth() / 2 - getWidth() / 2, 
                    c.getY() + c.getHeight() / 2 - getHeight() / 2);
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
	    damage = d;
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
	    super.move();
	    setAnimations();
	}
    
    /**
     * Returns whether this projectile hits a given character based on bounding rectangles
     * if Character is the same as the one that spawned this projectile,
     * this method should return false
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
            c.takeDamage( this.damage );
        return overlaps;
    }
    
    // for expansion: returns whether two sprites are of the same Team, should use strings if more than two teams
//    private boolean sameTeam( Character c1, Character c2 )
//    {
//        return ( c1 instanceof Enemy && c2 instanceof Enemy )
//            || ( c1 instanceof Player && c2 instanceof Player );
//    }

	/**
	 * Returns the damage that this projectile deals
	 * @return the damage that this projectile deals 
	 */
	public int getDamage()
	{
		return damage;
	}

}