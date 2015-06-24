package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedSprite extends Sprite
{
    /**
     * Enum that represents the 8 various directions and stores their degrees 
     * based on standard map directions that ship navigators use
     * (where NORTH = 0 degrees and increases in a clockwise direction)
     */
    public enum Direction {
        NORTH( 0 ), 
        NORTHEAST( 45 ), 
        EAST( 90 ), 
        SOUTHEAST( 135 ), 
        SOUTH( 180 ), 
        SOUTHWEST( 225 ), 
        WEST( 270 ), 
        NORTHWEST( 315 ),
        NUMDEGREES( 360 );

        public static Direction[] DIRECTIONS = Direction.values();
        
        int direction;
        Direction( int direction ) {
            this.direction = direction;
        }
        
        public int getDirection() {
            return direction;
        }
        
        public static Direction nearestDirection( double dir ) {
            int tol = 22; // tolerance (45 degrees / 2)
            for ( int i = 1; i / 2 < DIRECTIONS.length - 1; i+=2 ) // TODO check for bugs
            {
                if ( dir < tol * i )
                    return DIRECTIONS[i/2];
            }
            return DIRECTIONS[0]; 
            // note: collision will be slightly buggy (currently only affects Enemy) 
            // because all angles not EXACTLY the standard 4 directions should 
            // not be those 4 directions
        }
    }

    /**
     * variables used to hold animation frames and initialize animations
     */
    private float stateTime;
    private Animation[] animations;
    private Animation animation;

    private Direction prevDirection = Direction.SOUTH;
    private double direction = -1;
    private float moveSpeed;
    
    public AnimatedSprite( Animation... a )
    {
        this.animations = new Animation[4];
        this.initializeAnimations( a );
    }
    
    public AnimatedSprite( double dir, Animation... a )
    {
        this( a );
        this.direction = dir;
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
//         this.setRegion( animation.getKeyFrame( stateTime ) );
         set( new Sprite(animation.getKeyFrame( stateTime ) ) );
     }
     
     /**
      * Uses the value of direction to initialize a new animation to either
      * upAnimation, rightAnimation, downAnimation, or leftAnimaion, respective
      * to the values of the constant variables provided two represent the 8
      * different directions. Use stateTime to display the correct keyFrame and
      * update stateTime while the animation is not finished. Once the animation
      * is finished, set stateTime to 0.
      */
     public void setAnimations() {
         Direction d = prevDirection;
         
         // can be changed into a loop
         if ( direction > Direction.NORTHWEST.getDirection()
           || direction < Direction.NORTHEAST.getDirection() ) {
             prevDirection = Direction.NORTH;
         }
         else if ( direction > Direction.NORTHEAST.getDirection() 
                && direction < Direction.SOUTHEAST.getDirection() ) {
             prevDirection = Direction.EAST;
         }
         else if ( direction > Direction.SOUTHEAST.getDirection() 
                && direction < Direction.SOUTHWEST.getDirection() ) {
             prevDirection = Direction.SOUTH;
         }
         else if ( direction > Direction.SOUTHWEST.getDirection() 
                && direction < Direction.NORTHWEST.getDirection() ) {
             prevDirection = Direction.WEST;
         }
         
         if ( prevDirection != d )
             animation = animations[prevDirection.getDirection() / 90];

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
         translate();
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
     private void translate() {
         if ( direction != -1 ) {
             translateY( (float) ( moveSpeed * Math.sin( Math.toRadians( 90 - direction ) ) ) );
             translateX( (float) ( moveSpeed * Math.cos( Math.toRadians( 90 - direction ) ) ) );
         }
     }

     /**
      * Setter method for setDirection. Input will be modded by 8 so that
      * direction will be a valid integer in the range [0, 8)
      * 
      * @param dir
      */
     public void setDirection(double dir) {
         direction = dir % 360;
     }
     
     public void resetDirection() 
     {
         direction = -1;
         prevDirection = Direction.SOUTH;
         animation = animations[prevDirection.getDirection() / 90];
         this.setRegion( animation.getKeyFrame( stateTime ) );
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
     public double getDirection() {
         return direction;
     }
     
     public int getRoundedDirection()
     {
         return Direction.nearestDirection( direction ).getDirection();
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
