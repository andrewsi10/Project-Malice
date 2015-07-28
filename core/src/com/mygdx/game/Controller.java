package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * An interface that had to be a Stage...
 * 
 * This class provides the base that Controllers need for the player to 
 * function
 * 
 * Used only by GameScreen
 *
 *  @author  Nathan Lui
 *  @version Jul 11, 2015
 *  @author  Assignment: Project Malice-core
 */
public abstract class Controller extends Stage
{
    protected final Malice game;
    
    public Controller ( Malice g ) {
        super( g.viewport );
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
     * Allows controllers to hide and show actors in pausing, called by 
     * GameScreen's pause()
     */
    public abstract void pause();

    /**
     * Allows controllers to hide and show actors in resuming, called by 
     * GameScreen's resume()
     */
    public abstract void resume();
    
    /**
     * Called to reset the controller when the player is switched 
     * (called by player)
     */
    public abstract void reset();
}
