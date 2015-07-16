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

        /**
         * Array of Direction objects
         */
        public static final Direction[] DIRECTIONS = Direction.values();
        
        int direction;
        Direction( int direction ) {
            this.direction = direction;
        }
        
        /**
         * Returns this direction's angle in degrees where NORTH is 0 degrees
         * and increases clockwise
         * @return direction
         */
        public int getDirection() {
            return direction;
        }
        
        /**
         * Returns Direction closest to given angle where NORTH is 0 degrees
         * @param dir angle
         * @return nearest Direction
         */
        public static Direction nearestDirection( double dir ) {
            // rounds to nearest diagonal angle
            for ( int i = 0; i < DIRECTIONS.length - 1; i++ )
            {
                if ( i % 2 == 0 )
                {
                    if ( dir == DIRECTIONS[i].getDirection() ) 
                        return DIRECTIONS[i];
                }
                else // if ( i % 2 == 1 )
                {
                    if ( dir < DIRECTIONS[i+1].getDirection() ) 
                        return DIRECTIONS[i];
                }
            }
            return NUMDEGREES;
            
            
            // true rounding:
            // collision will be slightly buggy (currently only affects Enemy) 
            // because all angles not EXACTLY the standard 4 directions should 
            // not be those 4 directions
//            int tol = 22; // tolerance (45 degrees / 2)
//            for ( int i = 1; i / 2 < DIRECTIONS.length - 1; i+=2 ) // TODO check for bugs
//            {
//                if ( dir < tol * i )
//                    return DIRECTIONS[i/2];
//            }
//            return DIRECTIONS[0]; 
        }
    }

    /**
     * variables used to hold animation frames and initialize animations
     */
    private float stateTime;
    private Animation[] animations;
    private Animation animation;

    private Direction prevDirection;
    private double direction;
    private float moveSpeed;
    
    /**
     * Creates an Empty Sprite
     */
    public AnimatedSprite()
    {
        this.animations = new Animation[4];
        direction = -1;
        prevDirection = Direction.SOUTH;
    }
    
    /**
     * Constructor used for the Projectile Class
     * @param dir starting direction going/facing
     * @param a Animations to use
     */
    public AnimatedSprite( double dir, Animation... a )
    {
        this();
        this.initializeAnimations( a );
        this.direction = dir;
    }
    /**
     * Initialize upAnimation, rightAnimation, downAnimation, and leftAnimation
     * with upFrames, rightFrames, downFrames, and leftFrames respectively. Each
     * animation should have a frame duration of .2 seconds. Initialize
     * stateTime to 0f.
     * 
     * @param a Animations to use
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
      * 
      * Assumes no idle animations
      */
     public void setAnimations() {
         if ( direction == -1 ) return; // assumes no idle animations
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
      * 
      * Uses translateX and translateY to move Character according to the 
      * direction multiplied by moveSpeed units respectively.
      * If direction is -1, sprite will not move, else will move according to 
      * the following:
      * 
      * translateY( moveSpeed * Math.sin( Math.toRadians( 90 - direction ) ) );
      * 
      * translateX( moveSpeed * Math.cos( Math.toRadians( 90 - direction ) ) );
      */
     public void translate() {
         if ( direction != -1 ) {
             translateY( (float) ( moveSpeed * Math.sin( Math.toRadians( 90 - direction ) ) ) );
             translateX( (float) ( moveSpeed * Math.cos( Math.toRadians( 90 - direction ) ) ) );
         }
     }

     /**
      * Setter method for setDirection. Input will be modded by 360 so that
      * direction will be a valid integer in the range [0, 360)
      * 
      * if dir < 0, sprite will not move and direction is set to -1
      * 
      * @param dir
      */
     public void setDirection( double dir ) {
         direction = ( dir < 0 ) ? -1 : ( dir % 360 );
     }
     
     /**
      * Resets Direction to -1 and prevDirection to SOUTH and resets the 
      * animation to SOUTH
      */
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
     public void setSpeed( int speed ) {
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
     
     /**
      * Rounds this sprite's direction to the nearest of the basic 8 directions
      * @return integer value of one of the 8 directions in degrees
      */
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
