package com.mygdx.game.world;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 *  Map class displays a map using tiles in a 2D array. Does not use a separate 
 *  class for tiles, instead it uses a 2D array of boolean to tell between a 
 *  space and a wall/block
 *  
 *  note: all coordinates are based on a bottom left origin
 *        Pixmap uses top-left-origin coordinates so adjustments are made
 *
 *  @author  Nathan Lui
 *  @version May 30, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Map
{
    /**
     * Package for all images
     */
    public static final String PACKAGE = "map/";
    // generation types
    // public static final int STORY = -2;
    public static final int RANDOM = -1;
    public static final int DUNGEON = 0;
    public static final int ARENA = 1;
    public static final int NUM_GENERATION_TYPES = 2;
    /**
     * Conversion number from pixels to tiles
     */
    public static final int PIXELS_TO_TILES = 64;
    /**
     * Spawn distance from player in tiles
     */
    public static final int SPAWN_DISTANCE = 7;
    public static final int OUTER_BORDER = 8;
    
    private boolean[][] areSpaces;
    
    private Pixmap[] blocks;
    private Pixmap[] spaces;
    private int spawnX;
    private int spawnY;
    
    private Texture map;
    private Texture expanse;
    
    /**
     * Constructs a map filled with walls
     * Prepares Pixmap arrays for use in createMap() from predetermined files
     * @param rows number of rows in map
     * @param cols number of columns in map
     */
    public Map( int rows, int cols)
    {
        areSpaces = new boolean[rows][cols];
        spaces = new Pixmap[1];
        Texture texture = new Texture( PACKAGE + "GrassTile.png" );
        texture.getTextureData().prepare();
        spaces[0] = texture.getTextureData().consumePixmap();
        texture.dispose();
        TextureRegion[] trees = new TextureRegion[10];
        blocks = new Pixmap[trees.length];
        for ( int i = 0; i < blocks.length; i++ ) // stores textures in pixmaps
        {
            trees[i] = new TextureRegion( new Texture( PACKAGE + "Trees/Tree" + i + ".png" ) );
            Texture t = trees[i].getTexture();
            t.getTextureData().prepare();
            blocks[i] = t.getTextureData().consumePixmap();
            t.dispose();
        }
            
// note: ideal method of loading in tree (doesn't work)
//      TextureRegion[][] trees = TextureRegion.split( new Texture( PACKAGE + "Trees.png" ), PIXELS_TO_METERS, PIXELS_TO_METERS );
//        texture = new Texture( PACKAGE + "Trees.png" );
//        blocks = new Pixmap[trees.length * trees[0].length];
//        for (int i = 0; i < trees.length; i++) {
//        	for (int j = 0; j < trees[i].length; j++) {
//        	    Texture t = trees[i][j].getTexture();
//        	    t.getTextureData().prepare();
//        		blocks[i*trees.length + j] = t.getTextureData().consumePixmap();
//        		t.dispose();
//        	}
//        }
    }

    /**
     * For JUnit Testing only (in order to avoid graphics: Textures and Pixmaps)
     * @param rows number of rows in map
     * @param cols number of columns in map
     * @param b to differentiate from other constructor for testing
     */
    public Map( int rows, int cols, boolean b)
    {
        areSpaces = new boolean[rows][cols];
    }
    
    /**
     * Generates the map based on type of generation
     * 
     * This method should be used instead of randomGeneration() as it will take 
     * in consideration all types of generations
     * 
     * (this method is mainly for expansion of game generation)
     * @param type of generation
     */
    public void generate( int type )
    {
        if ( type == RANDOM )
            type = randomNumber( NUM_GENERATION_TYPES );
        // if ( type >= 0 )
        randomGeneration( type );
        // else if ( type == STORY )
        // createStoryMap();
    }
    
    /**
     * Creates the map image to be displayed based on areSpaces and Pixmap 
     * images created in the constructor
     * 
     * Takes in consideration that Pixmap is based on a top left origin 
     * coordinate system and that rest of map is based on a bottom left origin 
     * coordinate system.
     */
    public void createMap()
    {
        Pixmap pixmap1 = new Pixmap( getMapPixelWidth(), getMapPixelHeight(), Format.RGBA8888 );
        Pixmap pixmap2 = new Pixmap( getMapPixelWidth(), getMapPixelHeight(), Format.RGBA8888 );
        for ( int i = 0; i < getMapTileWidth(); i++ )
        {
            for ( int j = 0; j < getMapTileHeight(); j++ )
            {
                int x = tileToPixel(i);
                int y = this.getMapPixelHeight() - tileToPixel(j+1);
                pixmap1.drawPixmap(spaces[0], x, y);
                if (!areSpaces[i][j])
                {
                    pixmap1.drawPixmap(blocks[randomNumber(blocks.length)], x, y);
                }
                pixmap2.drawPixmap(spaces[0], x, y);
                pixmap2.drawPixmap(blocks[randomNumber(blocks.length)], x, y);
            }
        }
        map = new Texture( pixmap1 );
        expanse = new Texture( pixmap2 );
        pixmap1.dispose();
        pixmap2.dispose();
    }

    // --------------------------Recursive Methods ---------------------//
    /**
     * Recursively builds the room of the map where x and y are the starting 
     * points and w is the width and h is the height, uses a helper method with 
     * a new boolean[][] object
     * 
     * @param x -coordinate of top left of room
     * @param y -coordinate of top left of room
     * @param w width of room
     * @param h height of room
     */
    public void createRoom( int x, int y, int w, int h )
    {
        createRoom( x, y, w, h, new boolean[getMapTileWidth()][getMapTileHeight()] );
    }
    
    /**
     * Sets all tiles in areSpaces in the outline of a box starting at x and y
     * and formed by w and h to true.
     * @param x 
     * @param y
     * @param w
     * @param h
     */
    public void createRectangle( int x, int y, int w, int h )
    {
        createRoom( x, y, w, 1 ); // bottom of rectangle
        createRoom( x, y, 1, h ); // left of rectangle
        createRoom( x, y + h - 1, w, 1 ); // top of rectangle // did not work
        createRoom( x + w - 1, y, 1, h ); // right of rectangle // did not work
    }
    
    /**
     * Recursively builds the room of the map where x and y are the starting 
     * points and w is the width and h is the height
     *
     * @param x -coordinate of top left of room
     * @param y -coordinate of top left of room
     * @param w width of room
     * @param h height of room
     * @param b 
     */
    private void createRoom( int x, int y, int w, int h, boolean[][] b )
    {
        if ( w <= 0 || x >= getMapTileWidth() - 1
          || h <= 0 || y >= getMapTileHeight() - 1 ) return;
        if ( x <= 0 ) { createRoom( 1, y, w + x - 1, h, b ); return; }
        if ( y <= 0 ) { createRoom( x, 1, w, h + y - 1, b ); return; }
        if ( !b[x][y] ) createRoom( x + 1, y, w - 1, h, b );
        this.areSpaces[x][y] = true;
        b[x][y] = true;
        createRoom( x, y + 1, w, h - 1, b );
    }
    
    /**
     * Returns the size of a area recursively using a helper method and flood 
     * fills a copy of areSpaces
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @return number of spaces in room
     */
    public int sizeOfArea( int x, int y )
    {
        return sizeOfArea( x, y, copyAreSpaces() );
    }

    /**
     * Returns the size of a area recursively (Helper Method)
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param map Map to flood fill
     * @return number of spaces in room
     */
    private int sizeOfArea( int x, int y, boolean[][] map )
    {
        if ( !inTileBounds( x, y ) || !map[x][y] ) return 0;
        map[x][y] = false;
        return sizeOfArea( x - 1, y, map ) 
             + sizeOfArea( x + 1, y, map )
             + sizeOfArea( x, y - 1, map )
             + sizeOfArea( x, y + 1, map ) + 1;
    }

    /**
     * Fills up all the spaces in a area (uses helper method to flood fill)
     * @param x starting x coordinate
     * @param y starting y coordinate
     */
    public void fillArea( int x, int y )
    {
        sizeOfArea( x, y, areSpaces ); // reuse sizeOfArea() helper method
    }
    
    /**
     * Returns whether point has a path to a second point
     * Uses recursion with helper method to flood fill a copy of areSpaces
     * @param x1 x-coordinate of point 1
     * @param y1 y-coordinate of point 1
     * @param x2 x-coordinate of point 2
     * @param y2 y-coordinate of point 2
     * @param asSpace whether path should be a space or not
     *          - this parameter is only used as true currently, it is for 
     *            expansion of generation capability purposes
     * @return if there is a path from point 1 to point 2
     */
    public boolean hasPath( int x1, int y1, int x2, int y2, boolean asSpace )
    {
        return hasPath( x1, y1, x2, y2, asSpace, copyAreSpaces() );
    }

    
    /**
     * hasPath() helper method recursively finds if a point reaches another point
     * @param x1 -coordinate of Point1
     * @param y1 -coordinate of Point1
     * @param x2 -coordinate of Point2
     * @param y2 -coordinate of Point2
     * @param asSpace 
     * @param map that is flood filled
     * @return true if point1 has a path to point2 with asSpaces
     */
    private boolean hasPath( int x1, int y1, int x2, int y2, boolean asSpace, boolean[][] map )
    {
        if ( !inTileBounds( x1, y1 ) || map[x1][y1] != asSpace ) return false;
        if ( x1 == x2 && y1 == y2 ) return true;
        map[x1][y1] = !asSpace;
        return hasPath( x1 - 1, y1, x2, y2, asSpace, map)
            || hasPath( x1 + 1, y1, x2, y2, asSpace, map)
            || hasPath( x1, y1 - 1, x2, y2, asSpace, map)
            || hasPath( x1, y1 + 1, x2, y2, asSpace, map);
    }

    // -----------------------Collision ------------------ //

    /**
     * Returns true if Sprite is in a wall, checks the sprite's bounding 
     * Rectangle with a created Rectangle to represent the walls near that 
     * sprite
     * 
     * @param s Sprite to check
     * @return true if Sprite is in a wall
     */
    public boolean isCollidingWithWall( Sprite s )
    {
        Rectangle check = new Rectangle();
        check.setSize( PIXELS_TO_TILES );
        int x = pixelToTile( s.getX() );
        int y = pixelToTile( s.getY() );
        for ( int i = x - 1; i <= x + 1; i++ )
        {
            for ( int j = y - 1; j <= y + 1; j++ )
            {
                if ( inTileBounds( i, j ) && !areSpaces[i][j] )
                {
                    check.setPosition( tileToPixel( i ), tileToPixel( j ) );
                    if ( s.getBoundingRectangle().overlaps( check )) 
                        return true;
                }
            }
        }
        return false;
    }
    

    // ---------------------Libgdx management--------------------//
    /**
     * Draws the map, creates map if map Texture has not been created.
     * Uses OUTER_BORDER and a texture of the outer border to create an expanse
     * (expanse covers dark areas outside of map with walls
     * @param batch SpriteBatch used to draw the map
     */
    public void draw( SpriteBatch batch )
    {
        if ( map == null )
            createMap();
        int x = tileToPixel( OUTER_BORDER );
        int y = tileToPixel( OUTER_BORDER );
        batch.draw( expanse, -x, -y );
        batch.draw( expanse, x, -y );
        batch.draw( expanse, -x, y );
        batch.draw( expanse, x, y );
        batch.draw( map, 0, 0 );
    }
    
    /**
     * Dispose of all Resources to prevent memory leaks
     * resources include any Pixmap variables and the map textures
     */
    public void dispose()
    {
        map.dispose();
        expanse.dispose();
        for ( Pixmap p : blocks )
            p.dispose();
        for ( Pixmap p : spaces )
            p.dispose();
    }
    
    
    // ----------------- Getters and Converters ---------------------//
    
    /**
     * Returns a copy of the field areSpaces .
     * @return copy of areSpaces
     */
    private boolean[][] copyAreSpaces()
    {
        boolean[][] b = new boolean[getMapTileWidth()][getMapTileHeight()];
        for ( int i = 0; i < getMapTileWidth(); i++ )
            for ( int j = 0; j < getMapTileHeight(); j++ )
                b[i][j] = areSpaces[i][j];
        return b; // areSpaces[i][j].clone() does not work properly
    }
    /**
     * Returns Width of Map in tiles
     * Private because Tile coordinates should only be managed in Map Class
     * @return Width of Map in tiles
     */
    private int getMapTileWidth()
    {
        return areSpaces.length;
    }

    /**
     * Returns Height of Map in tiles
     * Private because Tile coordinates should only be managed in Map Class
     * @return Height of Map in tiles
     */
    private int getMapTileHeight()
    {
        return areSpaces[0].length;
    }
    
    /**
     * Returns the map's width in pixels
     * @return the map's width in pixels
     */
    public int getMapPixelWidth()
    {
        return tileToPixel( getMapTileWidth() );
    }

    /**
     * Returns the map's height in pixels
     * @return the map's height in pixels
     */
    public int getMapPixelHeight()
    {
        return tileToPixel( getMapTileHeight() );
    }
    

    /**
     * Returns the pixel index of a coordinate
     * @param tile coordinate
     * @return pixel coordinate
     */
    public static int tileToPixel( int tile )
    {
        return tile * PIXELS_TO_TILES;// + block.getWidth() / 2;
    }
    
    /**
     * Returns the tile index of a coordinate
     * @param pixel coordinate
     * @return tile coordinate
     */
    public static int pixelToTile( float pixel )
    {
        return (int)( pixel / PIXELS_TO_TILES );
    }
    
    /**
     * Returns whether given pixel is in the map
     * @param x -coordinate of pixel
     * @param y -coordinate of pixel
     * @return true if pixel is in map
     */
    public boolean inPixelBounds( float x, float y )
    {
        return x >= 0 
            && y >= 0 
            && ( x <= this.getMapPixelWidth() )
            && ( y <= this.getMapPixelHeight() );
    }

    /**
     * Returns whether given tile is in the map
     * @param x -coordinate of tile
     * @param y -coordinate of tile
     * @return true if tile is in map
     */
    public boolean inTileBounds( int x, int y )
    {
        return ( x >= 0 && x < this.getMapTileWidth()
              && y >= 0 && y < this.getMapTileHeight() );
    }

    // --------------------Random Generators----------------//
    
    /**
     * Generates Random number from 0 inclusive to width in tiles of map 
     * exclusive
     * Private because Tile coordinates should only be managed in Map Class
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
     * @param type of generation
     */
    public void randomGeneration(int type )
    {
        int x, y, w, h;
        
        LinkedList<Point> list = new LinkedList<Point>();
        int size;
        do {
            x = randomTileCoordinate();
            y = randomTileCoordinate();
            w = randomNumber( getMapTileWidth() / 3 ) + 3;
            h = randomNumber( getMapTileHeight() / 3 ) + 3;
            createRoom( x, y, w, h );
            list.add( new Point( x + 1, y + 1 ) );
            size = sizeOfArea( x, y );
        } while (// x != this.areSpaces.length - 1
                size < getMapTileWidth() * getMapTileHeight() / 2
               );
        
        // Remove excess rooms
        int largest = size;
        Point point = list.removeLast();
        for ( Point p : list )
        {
            if ( type == ARENA )
            {
                size = sizeOfArea( p.x, p.y );
                if ( size > largest )
                {
                    largest = size;
                    fillArea( point.x, point.y );
                    point = p;
                    continue;

                }
                if ( size < largest ) {
                    fillArea( p.x, p.y );
                    continue;
                }
            }
            if ( !hasPath(point.x, point.y, p.x, p.y, true) )// && ( size == largest )
            {
                x = Math.min( point.x, p.x );
                y = Math.min( point.y, p.y );
                w = Math.abs( point.x - p.x );
                h = Math.abs( point.y - p.y );
                createRectangle( x, y, w, h );
            }
        }
        this.setSpawn( -1, -1 );
    }

    // -------------- Spawn methods -------------------- //
    
    /**
     * Sets the spawnX and spawnY, should be called before calling 
     * getSpawnX() and getSpawnY()
     * @param x float location based on pixels to avoid
     * @param y float location based on pixels to avoid
     */
    public void setSpawn( float x, float y )
    {
        setSpawn( pixelToTile( x ), pixelToTile( y ) );
    }
    
    /**
     * Sets the spawnX and spawnY, called with the generate(int) method
     * Private because Tile coordinates should only be managed in Map Class
     * @param x int Tile location to avoid
     * @param y int Tile location to avoid
     */
    private void setSpawn( int x, int y )
    {
        do {
            spawnX = this.randomTileCoordinate();
            spawnY = this.randomTileCoordinate();
        } while ( !areSpaces[spawnX][spawnY] 
               || ( Math.abs( spawnX - x ) < SPAWN_DISTANCE 
                 && Math.abs( spawnY - y ) < SPAWN_DISTANCE ) );
    }
    
    /**
     * Returns suggested spawn location for player
     * @return x-coordinate of spawn location
     */
    public int getSpawnX()
    {
        return tileToPixel( spawnX );
    }

    /**
     * Returns suggested spawn location for player
     * @return y-coordinate of spawn location
     */
    public int getSpawnY()
    {
        return tileToPixel( spawnY );
    }
    
    // --------------------For Testing ------------------ //

    /**
     * Written mainly for testing
     * Returns String representation of this map with X representing walls and 
     * ' ' representing spaces.
     * @return String representation of this map
     */
    @Override
    public String toString()
    {
        String s = "";
        for ( int i = 0; i < areSpaces.length; i++ ) {
            for ( int j = 0; j < areSpaces[0].length; j++ )
                s += ( areSpaces[getMapTileWidth() - 1 - i][j] ? ' ' : 'X' ) + " ";
            s += "\n";
        }
        return s;
    }
    
    /**
     * Method for testing only
     * @return the tile array of the map (areSpaces)
     */
    public boolean[][] getAreSpaces()
    {
        return areSpaces;
    }
    
    
    /**
     * Runs short testing program for visually checking the map on command line
     * @param arg command line arguments -- not used
     */
    public static void main( String[] args )
    {
        @SuppressWarnings("resource")
        Scanner scanUser = new Scanner( System.in );
        Map map;
        while ( true ) {
            map = new Map( 25, 25, true );
            map.generate( Map.RANDOM );
            System.out.println( map );
            scanUser.nextLine();
        }
        // testing recursive methods: buggy
//      Map map1 = new Map( 25,25,true);
//      while ( scanUser.hasNext() )
//      {
//            System.out.println( map1 );
//          s= scanUser.next();
//          try {
//              switch( s.charAt( 0 ) ) {
//                  case 'c':
//                      map1.createRoom( scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt() );
//                      break;
//                  case 'p':
//                      map1.hasPath( scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt(), true );
//                      break;
//                  case 's':
//                      System.out.println( map1.sizeOfRoom( scanUser.nextInt(), scanUser.nextInt() ) );
//                      break;
//                  case 'g':
//                      map1.generate( scanUser.nextInt() );
//                      break;
//              }
//          } catch( Exception e )
//          {
//              
//          }
//      Map map1 = new Map( 25,25, true );
//      int x1 = 5;
//      int y1 = 5;
//      int w1 = 5;
//      int h1 = 5;
//      int x2 = 15;
//      int y2 = 10;
//      int w2 = 7;
//      int h2 = 5;
//      int x3 = 8;
//      int y3 = 8;
//      int w3 = 7;
//      int h3 = 7;
//      int rectX2 = 6;
//      int rectY2 = 6;
//      int rectW2 = 10;
//      int rectH2 = 15;
//      map1.createRoom( x1, y1, w1, h1 );
//      map1.createRoom( x2, y2, w2, h2 );
//      map1.createRoom( y2, x2, h2, w2 );
//      map1.createRoom( x3, y3, w3, h3 );
//      map1.createRectangle( 1, 1, 23, 23 );
//      map1.createRectangle( rectX2, rectY2, rectW2, rectH2 );
//      System.out.println( map1 );
    }
}
