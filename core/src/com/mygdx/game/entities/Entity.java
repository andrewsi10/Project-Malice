package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.sprites.AnimatedSprite;
import com.mygdx.game.sprites.Character;

public abstract class Entity extends AnimatedSprite
{
    protected Character myCharacter;
    private boolean wallCollides = true;
    
    public Entity( Character c, double dir, Animation a )
    {
        super( dir, a );
        myCharacter = c;
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
    /**
     * Returns whether two sprites are of the same Team, should use strings or
     * integers if not determined by Class
     * 
     * @param c Character to compare with
     * @return
     */
    public boolean sameTeamWith( Character c )
    {
        return c.getClass().equals( myCharacter.getClass() );
    }
    
    public void setWallCollision( boolean collides ) 
    {
        wallCollides = collides;
    }
    
    public boolean collidesWithWalls()
    {
        return wallCollides;
    }
}
