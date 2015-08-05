package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.sprites.AnimatedSprite;
import com.mygdx.game.sprites.Character;

public abstract class Entity extends AnimatedSprite
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
     * Moves Entity
     */
    public abstract void move();
    
    /**
     * @param c Character to check with
     * @return true if this entity should be removed
     */
    public abstract boolean hitCharacter( Character c );
    
    // for expansion: returns whether two sprites are of the same Team, should use strings if more than two teams
//    private boolean sameTeam( Character c1, Character c2 )
//    {
//        return ( c1 instanceof Enemy && c2 instanceof Enemy )
//            || ( c1 instanceof Player && c2 instanceof Player );
//    }
}
