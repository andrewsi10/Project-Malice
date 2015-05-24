package com.mygdx.game.world;

import java.awt.Point;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Map
{
    public static final int ARENA = 0;
    public static final int DUNGEON = 1;
    public static final int PIXELS_TO_METERS = 49;
    
    private boolean[][] areSpaces;
    private Texture block[][];
    private Texture space;
    private int spawnX;
    private int spawnY;
    
    /**
     * Constructs a map filled with walls
     * @param rows number of rows in map
     * @param cols number of columns in map
     */
    public Map( int rows, int cols)
    {
        space = new Texture( "map/GrassTile.png" );
        areSpaces = new boolean[rows][cols];
        block = new Texture[rows][cols];
        for (int i = 0; i < rows; i++) {
        	for (int j = 0; j < cols; j++) {
        		block[i][j] = new Texture( "map/Trees/Tree" + (int)(Math.random()*10) + ".png");
        	}
        }
    }

    /**
     * Constructs a map and generates it according to type
     * @param rows number of rows in map
     * @param cols number of columns in map
     * @param generation type of generation
     */
    public Map( int rows, int cols, int generation)
    {
        this( rows, cols );
        generate( generation );
    }
    
    /**
     * Generates the map based on type of generation
     * @param type of generation
     */
    public void generate( int type )
    {
        switch ( type )
        {
            case ARENA:
                setSpawn();
                createRoom( 0, 0, areSpaces.length, areSpaces[0].length );
                break;
            case DUNGEON:
                randomGeneration();
                break;
        }
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
    
    /**
     * Returns the size of a room
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @return number of spaces in room
     */
    public int sizeOfRoom( int x, int y )
    {
        boolean[][] b = new boolean[areSpaces.length][areSpaces[0].length];
        for ( int i = 0; i < b.length; i++ )
            for ( int j = 0; j < b[i].length; j++ )
                b[i][j] = areSpaces[i][j];
        return sizeOfRoom( x, y, b );
        // did not work as above code
//        return sizeOfRoom( x, y, areSpaces.clone() );
    }

    /**
     * Returns the size of a room recursively
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param map Map to flood fill
     * @return number of spaces in room
     */
    private static int sizeOfRoom( int x, int y, boolean[][] map )
    {
        if ( !map[x][y] ) return 0;
        map[x][y] = false;
        return sizeOfRoom( x - 1, y, map ) 
             + sizeOfRoom( x + 1, y, map )
             + sizeOfRoom( x, y - 1, map )
             + sizeOfRoom( x, y + 1, map ) + 1;
    }
    
    /**
     * Fills up all the spaces in a room (uses sizeOfRoom() method)
     * @param x starting x coordinate
     * @param y starting y coordinate
     */
    public void fillRoom( int x, int y )
    {
        sizeOfRoom( x, y, areSpaces );
    }
    
    // -----------------------Collision ------------------ //
    
    /**
     * Returns true if Sprite is in a wall
     * @param s Sprite to check
     * @return true if Sprite is in a wall
     */
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
    

    // ---------------------Libgdx management--------------------//
    /**
     * Draws the map
     * @param batch SpriteBatch used to draw the map
     */
    public void draw( SpriteBatch batch )
    {
    	initialDraw(batch);
    	drawBlocks(batch);
    	
//        for ( int i = 0; i < areSpaces.length; i++ )
//        {
//            for ( int j = 0; j < areSpaces[i].length; j++ )
//            {
//                if (areSpaces[i][j])
//                {
//                	batch.draw(space, i*space.getWidth(), j*space.getHeight());
//                }
//                else {
//                	batch.draw(block, i*block.getWidth(), j*space.getHeight());
//                }
//            }
//        }
    }
    
    /**
     * covers the map in the texture used for spaces
     * @param batch used to draw the map
     */
    void initialDraw(SpriteBatch batch)
    {
    	for (int i = 0; i < areSpaces.length; i++)
    	{
    		for (int j = 0; j < areSpaces[i].length; j++)
    		{
    			batch.draw(space, i*PIXELS_TO_METERS, j*PIXELS_TO_METERS);
    		}
    	}
    }
    
    /**
     * places blocks where necessary
     * @param batch
     */
    void drawBlocks(SpriteBatch batch)
    {
    	for (int i = 0; i < areSpaces.length; i++)
    	{
    		for (int j = 0; j < areSpaces[i].length; j++)
    		{
    			if (!areSpaces[i][j])
    			{
    				batch.draw(block[i][j], i*PIXELS_TO_METERS, j*PIXELS_TO_METERS);
    			}
    		}
    	}
    }
    
    /**
     * Dispose of all Resources to prevent memory leaks
     */
    public void dispose()
    {
        for (int i = 0; i < block.length; i++) {
        	for (int j = 0 ; j < block[i].length; j++) {
        		block[i][j].dispose();
        	}
        }
        this.space.dispose();
    }
    
    
    // ----------------- x,y -coordinate conversions ---------------------//
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
    
    public boolean inTileBounds( int x, int y )
    {
        return ( x >= 0 && x < areSpaces.length && y >= 0 && y < areSpaces[0].length );
    }

    // --------------------Random Generators----------------//
    
    /**
     * Generates Random number from 0 inclusive to width in meters of map 
     * exclusive
     * @return random number
     */
    private int randomTileCoordinate()
    {
        return randomNumber( this.areSpaces.length - 1 ) + 1;
    }

    /**
     * Generates Random number from 0 inclusive to given limit exclusive
     * @param limit 
     * @return random number
     */
    public static int randomNumber( int limit )
    {
        return (int)( Math.random() * limit );
    }
    
    /**
     * Randomly Generates "rooms" in map where all the edges of areSpaces[][] 
     * remain "false" or walls
     */
    public void randomGeneration()
    {
        int x = randomTileCoordinate();
        int y = randomTileCoordinate();
        int w = randomNumber( this.areSpaces.length / 3 ) + 3;
        int h = randomNumber( this.areSpaces.length / 3 ) + 3;
        
        LinkedList<Point> list = new LinkedList<Point>();
        int count = 0;
        do {
            createRoom( x, y, w, h );
            x = randomTileCoordinate();
            y = randomTileCoordinate();
            w = randomNumber( this.areSpaces.length / 3 ) + 3;
            h = randomNumber( this.areSpaces.length / 3 ) + 3;
            list.add( new Point( x, y ) );
            count++;
        } while ( x != this.areSpaces.length - 1
               || count < this.areSpaces.length / 5 );
        // System.out.println( x ); // TODO remove
        // TODO options: connect rooms not part of the big room or remove them.
        int largest = 0;
        Point point = new Point();
        for ( Point p : list )
        {
            int size = sizeOfRoom( p.x, p.y );
            if ( size > largest )
            {
                largest = size;
                fillRoom( point.x, point.y );
                point = p;
                
            }
            else if ( size < largest ) {
                fillRoom( p.x, p.y );
            }
        }
        this.setSpawn();
        // System.out.println( this ); // TODO remove
    }

    // -------------- Spawn methods -------------------- //
    /**
     * Sets the spawnX and spawnY, called with the generate(int) method
     */
    public void setSpawn()
    {
        do {
            spawnX = this.randomTileCoordinate();
            spawnY = this.randomTileCoordinate();
        } while ( !areSpaces[spawnX][spawnY] );
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
    
    // --------------------For Testing ------------------ //
    @Override
    public String toString()
    {
        String s = "";
        char[][] map = this.showMap();
        for ( char[] array : map ) {
            for ( char c : array )
                s += c + " ";
            s += "\n";
        }
        return s;
    }
    
    public char[][] showMap() // TODO remove getter method
    {
        char[][] map = new char[areSpaces.length][areSpaces[0].length];
        for ( int i = 0; i < areSpaces.length; i++ )
            for ( int j = 0; j < areSpaces[0].length; j++ )
                map[i][j] = areSpaces[i][j] ? ' ' : 'X';
        return map;
    }
    
    // -----------------Deprecating methods ------------------//

    
//  public boolean hasPath( int x1, int y1, int x2, int y2, boolean asSpace )
//  {
//      boolean[][] b = new boolean[areSpaces.length][areSpaces[0].length];
//      for ( int i = 0; i < b.length; i++ )
//          for ( int j = 0; j < b[i].length; j++ )
//              b[i][j] = areSpaces[i][j];
//      return hasPath( x1, y1, x2, y2, asSpace, b );
//      // does not work as above code
////      return hasPath( x1, y1, x2, y2, asSpace, areSpaces.clone() );
//  }
//  
//  private static boolean hasPath( int x1, int y1, int x2, int y2, boolean asSpace, boolean[][] map )
//  {
//      if ( map[x1][y1] != asSpace
//        || x1 < 1 || x1 >= map.length - 1
//        || y1 < 1 || y1 >= map[0].length - 1 ) return false;
//      if ( x1 == x2 && y1 == y2 ) return true;
//      map[x1][y1] = !asSpace;
//      return hasPath( x1 - 1, y1, x2, y2, asSpace, map)
//          || hasPath( x1 + 1, y1, x2, y2, asSpace, map)
//          || hasPath( x1, y1 - 1, x2, y2, asSpace, map)
//          || hasPath( x1, y1 + 1, x2, y2, asSpace, map);
//  }
}
