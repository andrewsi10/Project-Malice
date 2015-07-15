package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * An interface that had to be a Stage...
 * 
 * This class provides the base that Controllers need for the player to 
 * function
 *
 *  @author  Nathan Lui
 *  @version Jul 11, 2015
 *  @author  Assignment: Project Malice-core
 */
public abstract class Controller extends Stage
{
    protected final Malice game;
    
    public Controller ( Malice g ) {
        game = g;
    }
    
    /**
     * Returns direction to go based on key input and an array that stores input
     * values (Used by Player class)
     * 
     * @return direction or -1 if no direction
     */
    public abstract double getInputDirection();
    
    /**
     * This method will return the angle that the player should shoot
     * @return direction or -1 if no direction
     */
    public abstract double getShootingDirection();
    
    
    /**
     * Called to reset the controller when the player is switched
     */
    public void reset() {}
}
