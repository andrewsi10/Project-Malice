package com.mygdx.game.projectile;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.player.AnimatedSprite;
import com.mygdx.game.player.Character;

public class Entity extends AnimatedSprite
{
    public Entity(  double dir, Animation a )
    {
        super( dir, a );
    }
    
    /**
     * Constructor for testing: Creates empty entity
     */
    public Entity() {}
    
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
        return c.getBoundingRectangle().overlaps( this.getBoundingRectangle() );
    }
    
    // for expansion: returns whether two sprites are of the same Team, should use strings if more than two teams
//    private boolean sameTeam( Character c1, Character c2 )
//    {
//        return ( c1 instanceof Enemy && c2 instanceof Enemy )
//            || ( c1 instanceof Player && c2 instanceof Player );
//    }
}
