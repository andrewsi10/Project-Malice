package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedSprite extends Sprite
{
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
    /**
     * The Number of Directions
     */
    public static final int NUMDIRECTIONS = 8;

    /**
     * variables used to hold animation frames and initialize animations
     */
    private float stateTime;
    private Animation[] animations;
    private Animation animation;

    private int prevDirection = -1;
    private int direction = -1;
    private float moveSpeed;
    
    public AnimatedSprite( Animation... a )
    {
        this.animations = new Animation[4];
        this.initializeAnimations( a );
    }
    /**
     * Initialize upAnimation, rightAnimation, downAnimation, and leftAnimation
     * with upFrames, rightFrames, downFrames, and leftFrames respectively. Each
     * animation should have a frame duration of .2 seconds. Initialize
     * stateTime to 0f.
     */
     public void initializeAnimations( Animation... a )
     {
         for ( int i = 0; i < animations.length; i++ )
             animations[i] = a[i%a.length];
         stateTime = 0f;
         animation = a[2%a.length];
//         this.setRegion( a[2%a.length].getKeyFrame( stateTime ) );
         set( new Sprite(a[2%a.length].getKeyFrame( stateTime ) ) );
     }
     
     /**
      * Uses the value of direction to initialize a new animation to either
      * upAnimation, rightAnimation, downAnimation, or leftAnimaion, respective
      * to the values of the constant variables provided two represent the 8
      * different directions. Use stateTime to display the correct keyFrame and
      * update stateTime while the animation is not finished. Once the animation
      * is finished, set stateTime to 0.
      */
     protected void setAnimations() {
         if ( Math.abs( direction - prevDirection ) > 1 ) {
             switch (direction) {
                 case NORTHWEST:
                 case NORTH:
                 case NORTHEAST:
                     prevDirection = NORTH;
                     break;
                 case EAST:
                     prevDirection = EAST;
                     break;
                 case SOUTHEAST:
                 case SOUTH:
                 case SOUTHWEST:
                     prevDirection = SOUTH;
                     break;
                 case WEST:
                     prevDirection = WEST;
                     break;
             }
             animation = animations[prevDirection / 2];
         }

         if ( !animation.isAnimationFinished( stateTime ) ) {
             stateTime += Gdx.graphics.getDeltaTime();
         } else {
             stateTime = 0;
         }
         this.setRegion( animation.getKeyFrame( stateTime ) );
     }

     /**
      * Moves sprite according to its current direction. Then translates
      * Character in the appropriate direction by one unit using the
      * translate(int, int) method.
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

     /**
      * uses translateX and translateY to move Character by dx and dy multiplied
      * by moveSpeed units respectively. If Character moves along a diagonal
      * (neither dx nor dy are equal to 0), divide the value inside both
      * translateX and translateY by Math.sqrt(2).
      * 
      * @param dx
      *            change in x-coordinate
      * @param dy
      *            change in y-coordinate
      */
     private void translate(int dx, int dy) {
         if (dx == 0 || dy == 0) {
             translateX((float) (moveSpeed * dx));
             translateY((float) (moveSpeed * dy));
         } else {
             translateX((float) (moveSpeed * dx / Math.sqrt(2)));
             translateY((float) (moveSpeed * dy / Math.sqrt(2)));
         }
     }

     /**
      * Setter method for setDirection. Input will be modded by 8 so that
      * direction will be a valid integer in the range [0, 8)
      * 
      * @param dir
      */
     public void setDirection(int dir) {
         direction = dir % NUMDIRECTIONS;
     }

     /**
      * setter method for moveSpeed
      * 
      * @param speed
      *            new value for moveSpeed
      */
     public void setSpeed(int speed) {
         moveSpeed = speed;
     }

     /**
      * getter method for direction
      * 
      * @return direction
      */
     public int getDirection() {
         return direction;
     }

     /**
      * getter method for moveSpeed
      * 
      * @return moveSpeed;
      */
     public float getSpeed() {
         return moveSpeed;
     }
}
