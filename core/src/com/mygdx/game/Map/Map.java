package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Map
{
    public static final int ARENA = 0;
    public static final int DUNGEON = 1;
    public static final int PIXELS_TO_METERS = 64;
    
    private boolean[][] areSpaces;
    private Texture block;
    private Texture space;
    private int spawnX;
    private int spawnY;
//    public Map()
//    {
//        this( 25,25 );
//    }
    
    public Map( int rows, int cols)
    {
        block = new Texture( "map/DarkGreen.png" );
        space = new Texture( "map/background.png" );
        areSpaces = new boolean[25][25];
//      for ( int r = 0; r < areSpaces.length; r++ )
//      {
//          for ( int c = 0; c < areSpaces[r].length; c++ )
//          {
//              areSpaces[r][c] = ( r + c ) % 2 == 0 ? true : false;
//          }
//      }
    }
    
    /**
     * Generates the map based on type of generation
     * @param type of generation
     */
    public void generate( int type )
    {
        this.randomGeneration();
//        switch ( type )
//        {
//            case ARENA:
//                spawnX = 1;
//                spawnY = 1;
//                createRoom( 0, 0, areSpaces.length, areSpaces[0].length );
//                break;
//            case DUNGEON:
//                randomGeneration();
//                break;
//        }
//        createRoom( 0, 0, areSpaces.length, areSpaces[0].length );
//        createRoom( 0, 0, 5, 5 );
//        createRoom( 5, 4, 5, 5 );
//        createRoom( 10, 8, 5, 5 );
//        createRoom( 15, 12, 5, 5 );
    }

    /**
     * Recursively builds the room of the map where x and y are the top left 
     * corner of the room and w is the width and h is the height
     * 
     * @param x -coordinate of top left of room
     * @param y -coordinate of top left of room
     * @param w width of room
     * @param h height of room
     */
    public void createRoom( int x, int y, int w, int h )
    {
        if ( w <= 0 || x >= areSpaces.length - 1
          || h <= 0 || y >= areSpaces[0].length - 1 ) return;
        if ( x <= 0 ) { createRoom( 1, y, w + x - 1, h ); return; }
        if ( y <= 0 ) { createRoom( x, 1, w, h + y - 1 ); return; }
        if ( !this.areSpaces[x][y] ) createRoom( x + 1, y, w - 1, h );
        this.areSpaces[x][y] = true;
        createRoom( x, y + 1, w, h - 1 );
    }
    
//    private void createRoom( int x, int y, int w, int h, boolean fillRight )
//    {
//        if ( w <= 0 || x >= areSpaces.length - 1
//          || h <= 0 || y >= areSpaces[0].length - 1 ) return;
//        if ( x <= 0 ) { createRoom( 1, y, w + x - 1, h, true ); return; }
//        if ( y <= 0 ) { createRoom( x, 1, w, h + y - 1, true ); return; }
//        this.areSpaces[x][y] = true;
//        if ( fillRight ) createRoom( x + 1, y, w - 1, h, true );
//        createRoom( x, y + 1, w, h - 1, false );
//    }
    
    /**
     * Randomly Generates "rooms" in map
     */
    public void randomGeneration()
    {
        int x = randomCoordinate();
        int y = randomCoordinate();
        int w = randomNumber( this.areSpaces.length / 3 ) + 3;
        int h = randomNumber( this.areSpaces.length / 3 ) + 3;
        spawnX = x;
        spawnY = y;
        
        int count = 0;
        do {
            createRoom( x, y, w, h );
            x = randomCoordinate();
            y = randomCoordinate();
            w = randomNumber( this.areSpaces.length / 3 ) + 3;
            h = randomNumber( this.areSpaces.length / 3 ) + 3;
            count++;
        } while ( x != this.areSpaces.length - 1
               || count < this.areSpaces.length / 3 );
        System.out.println( x );
    }
    
    /**
     * Generates Random number from 0 inclusive to width in meters of map 
     * exclusive
     * @return random number
     */
    private int randomCoordinate()
    {
        return randomNumber( this.areSpaces.length );
    }

    /**
     * Generates Random number from 0 inclusive to given limit exclusive
     * @param limit 
     * @return random number
     */
    private int randomNumber( int limit )
    {
        return (int)( Math.random() * limit );
    }
    
    
    public boolean isCollidingWithWall( Sprite s )
    {
        Rectangle sprite = new Rectangle( s.getX(), s.getY(), s.getWidth(), s.getHeight() );
        Rectangle check = new Rectangle();
        check.setSize( PIXELS_TO_METERS );
        int x = getTileX( s.getX() );
        int y = getTileY( s.getY() );
        for ( int i = x - 1; i <= x + 1; i++ )
        {
            for ( int j = y - 1; j <= y + 1; j++ )
            {
                if ( inTileBounds( i, j ) && !areSpaces[i][j] )
                {
                    check.setPosition( getX( i ), getY( j ) );
                    if ( sprite.overlaps( check )) return true;
                }
            }
        }
        
        
        return false;
    }
    
    public boolean inTileBounds( int x, int y )
    {
        return ( x >= 0 && x < areSpaces.length && y >= 0 && y < areSpaces[0].length );
    }
    
    /**
     * Draws the map
     * @param batch SpriteBatch used to draw the map
     */
    public void draw( SpriteBatch batch )
    {
        for ( int i = 0; i < areSpaces.length; i++ )
        {
            for ( int j = 0; j < areSpaces[i].length; j++ )
            {
                batch.draw( areSpaces[i][j]?space:block, i*PIXELS_TO_METERS,j*PIXELS_TO_METERS);
            }
        }
    }
    
    /**
     * Dispose of all Resources to prevent memory leaks
     */
    public void dispose()
    {
        this.block.dispose();
        this.space.dispose();
    }
    
    /**
     * Get Real x value based on x Tile location
     * @param x Tile Location
     * @return Real x value
     */
    public static int getX( int x )
    {
        return x * PIXELS_TO_METERS;// + block.getWidth() / 2;
    }

    /**
     * Get Real y value based on y Tile location
     * @param y Tile Location
     * @return Real y value
     */
    public static int getY( int y )
    {
        return y * PIXELS_TO_METERS;// + block.getHeight() / 2;
    }
    
    public static int getTileX( float x )
    {
        return (int)( x / PIXELS_TO_METERS );
    }
    
    public static int getTileY( float y )
    {
        return (int)( y / PIXELS_TO_METERS );
    }
    /**
     * Returns suggested spawn location for player
     * @return x-coordinate of spawn location
     */
    public int getSpawnX()
    {
        return getX( spawnX );
    }

    /**
     * Returns suggested spawn location for player
     * @return y-coordinate of spawn location
     */
    public int getSpawnY()
    {
        return getY( spawnY );
    }
}
