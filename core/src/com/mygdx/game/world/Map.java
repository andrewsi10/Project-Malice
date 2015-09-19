package com.mygdx.game.world;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.Entity;

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
 *  @author  Sources: Libgdx
 */
public class Map
{
    
    // ----------------------- Enums and static finals --------------- //
    /** Package for all images */
    public static final String PACKAGE = "map/";
    
    /** Conversion number from pixels to tiles */
    public static final int TILE_SIZE = 64;
    /** Spawn distance from player in tiles */
    public static final int SPAWN_DISTANCE = 7;
    /** Determines how far the outer border is drawn (meant to cover black space) */
    public static final int OUTER_BORDER = 9;
    
    // generation types
    public enum Generation {
        STORY, DUNGEON, ARENA, RANDOM
    }
    // Biome types
    public enum Biome {
        RANDOM, FOREST, SNOW
    }
    // Tile types
    private enum Tile { // 1st row is blocks, 2nd row is spaces (expansion: 3rd row is decoration)
        BLOCK, SPACE
    }
    
    private static final EnumMap<Biome, TextureRegion[][]> biomeImages 
                           = new EnumMap<Biome, TextureRegion[][]>(Biome.class);
    
    public static void loadBiomes() { // initialize EnumMaps
        Texture texture; 
        for ( Biome b : Biome.values() ) {
            if ( b == Biome.RANDOM ) continue;
            String s = b.toString();
            texture = new Texture( PACKAGE + s.charAt( 0 ) + s.substring( 1 ).toLowerCase() + ".png" );
            
            biomeImages.put( b, TextureRegion.split( texture, TILE_SIZE, TILE_SIZE ) );
            
        }
    }
    public static void disposeBiomes() {
        for ( Biome b : Biome.values() ) {
            if ( b == Biome.RANDOM ) continue;
            biomeImages.get( b )[0][0].getTexture().dispose();
        }
    }
    
    
    // --------------- private variables --------------- //
    private boolean[][] areSpaces;
    private int spawnX;
    private int spawnY;
    
    private TextureRegion[][] biome;
    private MapTile[][] tiles;
    private boolean biomeChanged;

    // -------------------- Constructors ------------------- //
    /**
     * Constructs a map filled with walls
     * 
     * @param rows number of rows in map
     * @param cols number of columns in map
     */
    public Map( int rows, int cols)
    {
        areSpaces = new boolean[rows][cols];
        tiles = new MapTile[rows][cols];
        for ( MapTile[] m : tiles )
        {
            for ( int i = 0; i < m.length; i++ )
            {
                m[i] = new MapTile();
            }
        }
    }

    /**
     * For JUnit Testing only (in order to avoid graphics: Textures and Pixmaps)
     * @param size number of rows and columns in map
     */
    public Map( int size )
    {
        areSpaces = new boolean[size][size];
    }
    
    public void reset()
    {
        areSpaces = new boolean[getMapTileWidth()][getMapTileHeight()];
        biomeChanged = true;
    }
    
    // --------------------- Generating ----------------- //
    /**
     * Generates the map based on type of generation
     * 
     * This method should be used instead of randomGeneration() as it will take 
     * in consideration all types of generations
     * 
     * (this method is mainly for expansion of game generation)
     * @param type of generation
     */
    public void generate( Generation type, Biome b )
    {
        reset();
        if ( b == Biome.RANDOM )
            b = Biome.values()[randomNumber(Biome.values().length - 1) + 1];
//        biomeChanged = ( myBiome != biomeImages.get( b ) );
        
        biome = biomeImages.get( b );
        switch ( type )
        {
//            case STORY:
//                // not implemented
//                break;
//            case ARENA:
//                generateArena();
//                break;
//            case RANDOM:
//                type = Generation.values()[randomNumber(Generation.values().length - 2) + 2];
            default:
                randomGeneration();
                break;
        }
        this.setSpawn( -1, -1 );
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
        for ( int i = 0; i < getMapTileWidth(); i++ )
        {
            for ( int j = getMapTileHeight() - 1; j >= 0; j-- )
            {
                tiles[i][j].reset();
                tiles[i][j].setFloor( biome[Tile.SPACE.ordinal()][randomNumber( biome[0].length )] );
                if ( !areSpaces[i][j] )
                {
                    tiles[i][j].setWall( biome[Tile.BLOCK.ordinal()][randomNumber( biome[0].length )] );
                }
            }
        }
        biomeChanged = false;
    }
    
    public void generateArena()
    {
        for ( int i = 0; i < areSpaces.length; i++ )
        {
            for ( int j = 0; j < areSpaces.length; j++ )
            {
                if ( i == 0 || i == areSpaces.length - 1 
                  || j == 0 || j == areSpaces[0].length - 1 
                  || i % 5 == 0 && j % 5 == 0 )
                    continue;
                areSpaces[i][j] = true;
            }
        }
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
        createRoom( x, y + h - 1, w, 1 ); // top of rectangle
        createRoom( x + w - 1, y, 1, h ); // right of rectangle
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
     * 
     * Creates a copy of the 2D array of booleans to flood fill. 
     * Parameters x and y indicate any coordinate in the room.
     * If starting coordinate is not a space, this method returns 0.
     * 
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @return number of spaces in area
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
        // recursive method
//        if ( !inTileBounds( x, y ) || !map[x][y] ) return 0;
//        map[x][y] = false;
//        return sizeOfArea( x - 1, y, map ) 
//             + sizeOfArea( x + 1, y, map )
//             + sizeOfArea( x, y - 1, map )
//             + sizeOfArea( x, y + 1, map ) + 1;
        // iterative method
        Queue<Integer> queueX = new LinkedList<Integer>();
        Queue<Integer> queueY = new LinkedList<Integer>();
        queueX.add( x );
        queueY.add( y );

        int count = 0;
        while (!queueX.isEmpty()) {
            x = queueX.remove();
            y = queueY.remove();

            if ( map[x][y] ) {
                queueX.add( x + 1 ); queueY.add( y );
                queueX.add( x - 1 ); queueY.add( y );
                queueX.add( x );     queueY.add( y + 1 );
                queueX.add( x );     queueY.add( y - 1 );
                
                map[x][y] = false;
                count++;
            }
        }
        return count;
    }

    /**
     * Fills up all the spaces in a area (uses helper method to flood fill)
     * 
     * Parameters x and y are the starting coordinate of the area to flood fill
     * 
     * @param x starting x coordinate
     * @param y starting y coordinate
     */
    public void fillArea( int x, int y )
    {
        sizeOfArea( x, y, areSpaces ); // reuse sizeOfArea() helper method
    }
    
    /**
     * Returns whether point has a path to a second point
     * 
     * Uses recursion with helper method to flood fill
     * Creates a copy of the 2D boolean array in order to allow flood fill
     * without modifying the main array.
     * 
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
        // recursive method
//        if ( !inTileBounds( x1, y1 ) || map[x1][y1] != asSpace ) return false;
//        if ( x1 == x2 && y1 == y2 ) return true;
//        map[x1][y1] = !asSpace;
//        return hasPath( x1 - 1, y1, x2, y2, asSpace, map )
//            || hasPath( x1 + 1, y1, x2, y2, asSpace, map )
//            || hasPath( x1, y1 - 1, x2, y2, asSpace, map )
//            || hasPath( x1, y1 + 1, x2, y2, asSpace, map );
        // iterative method
        Queue<Integer> queueX = new LinkedList<Integer>();
        Queue<Integer> queueY = new LinkedList<Integer>();
        queueX.add( x1 );
        queueY.add( y1 );

        int x, y;
        while (!queueX.isEmpty()) {
            x = queueX.remove();
            y = queueY.remove();

            if ( inTileBounds( x, y ) && map[x][y] == asSpace ) {
                if ( x == x2 && y == y2 ) return true;
                
                queueX.add( x + 1 ); queueY.add( y );
                queueX.add( x - 1 ); queueY.add( y );
                queueX.add( x );     queueY.add( y + 1 );
                queueX.add( x );     queueY.add( y - 1 );
                
                map[x][y] = false;
            }
        }
        return false;
    }

    // -----------------------Collision ------------------ //


    private Rectangle check = new Rectangle();
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
        if ( s instanceof Entity && !( (Entity)s ).collidesWithWalls() )
            return false;
        check.setSize( TILE_SIZE );
        int x = pixelToTile( s.getX() );
        int y = pixelToTile( s.getY() );
        for ( int i = x - 1; i <= x + 1; i++ )
        {
            for ( int j = y - 1; j <= y + 1; j++ )
            {
                if ( inTileBounds( i, j ) && !areSpaces[i][j] )
                {
                    check.setPosition( tileToPixel( i ), tileToPixel( j ) );
                    if ( s.getBoundingRectangle().overlaps( check ) ) 
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
    public void draw( Batch batch, float camX, float camY, float camW, float camH )
    {
        if ( biomeChanged )
            createMap();
        float drawX = camX - camW / 2;
        float drawY = camY - camH / 2;
        int x = pixelToTile( drawX ) - 2;
        int y = pixelToTile( drawY ) - 2;
        int w = pixelToTile( camW ) + 4;
        int h = pixelToTile( camH ) + 4;
        for ( int i = x; i < x + w; i++ )
        {
            for ( int j = y; j < y + h; j++ )
            {
                if ( this.inTileBounds( i, j ) ) 
                {
                    tiles[i][j].draw( batch, TILE_SIZE * i, TILE_SIZE * j );
                }
                else //draw expanse to fill black space
                {
                    int a = 3 * Math.abs( i * j ) % biome[Tile.SPACE.ordinal()].length;// pseudo random algorithms
                    int b = 9 * Math.abs( i * j ) % biome[Tile.BLOCK.ordinal()].length;
                    batch.draw( biome[Tile.SPACE.ordinal()][a], TILE_SIZE * i, 
                                                                TILE_SIZE * j );
                    batch.draw( biome[Tile.BLOCK.ordinal()][b], TILE_SIZE * i, 
                                                                TILE_SIZE * j );
                }
            }
        }
    }
    
    /**
     * Dispose of all Resources to prevent memory leaks
     * resources include any Pixmap variables and the map textures
     */
    public void dispose()
    {
        
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
     * Returns the pixel index of a coordinate given the tile coordinate
     * @param tile coordinate
     * @return pixel coordinate
     */
    public static int tileToPixel( int tile )
    {
        return tile * TILE_SIZE;// + block.getWidth() / 2;
    }
    
    /**
     * Returns the tile index of a coordinate given the pixel coordinate
     * @param pixel coordinate
     * @return tile coordinate
     */
    public static int pixelToTile( float pixel )
    {
        return (int)( pixel / TILE_SIZE );
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
    public void randomGeneration()
    {
        boolean method = randomNumber( 2 ) == 0;
        int x, y, w, h, size;
        
        LinkedList<Integer> listX = new LinkedList<Integer>();
        LinkedList<Integer> listY = new LinkedList<Integer>();
        do {
            x = randomNumber( getMapTileWidth() - 3 );
            y = randomNumber( getMapTileHeight() - 3 );
            w = randomNumber( getMapTileWidth() / 3 ) + 3;
            h = randomNumber( getMapTileHeight() / 3 ) + 3;
            createRoom( x, y, w, h );
            listX.add( x + 1 );
            listY.add( y + 1 );
            size = sizeOfArea( x, y );
        } while (// x != this.areSpaces.length - 1
                size < getMapTileWidth() * getMapTileHeight() / 2
               );
        
        // Remove or connect excess rooms based on generation type
        int largest = size;
        int pX = listX.removeLast();
        int pY = listY.removeLast();
        int x1, y1;
        while ( !listX.isEmpty() )
        {
            x1 = listX.remove();
            y1 = listY.remove();
            if ( method )
            {
                size = sizeOfArea( x1, y1 );
                if ( size > largest ) {
                    largest = size;
                    fillArea( pX, pY );
                    pX = x1;
                    pY = y1;
                    continue;

                }
                if ( size < largest ) {
                    fillArea( x1, y1 );
                    continue;
                }
            }
            if ( !hasPath( pX, pY, x1, y1, true ) )// && ( size == largest )
            {
                x = Math.min( pX, x1 );
                y = Math.min( pY, y1 );
                w = Math.abs( pX - x1 );
                h = Math.abs( pY - y1 );
                createRectangle( x, y, w, h );
                largest = sizeOfArea( x, y );
            }
        }
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
            spawnX = randomNumber( getMapTileWidth() - 1 ) + 1;
            spawnY = randomNumber( getMapTileHeight() - 1 ) + 1;
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
                s += ( areSpaces[j][getMapTileWidth() - 1 - i] ? ' ' : 'X' ) + " ";
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
    
    
//    /**
//     * Runs short testing program for visually checking the map on command line
//     * @param arg command line arguments -- not used
//     */
//    public static void main( String[] args )
//    {
//        @SuppressWarnings("resource")
//        Scanner scanUser = new Scanner( System.in );
//        Map map;
//        while ( true ) {
//            map = new Map( 25, 25, true );
//            map.generate( Map.Generation.RANDOM );
//            System.out.println( map );
//            scanUser.nextLine();
//        }
        // testing recursive methods:
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
//      }
//    }
}
