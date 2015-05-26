package com.mygdx.game.world;

import java.awt.Point;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Map
{
    /**
     * Package for all images
     */
    public static final String PACKAGE = "map/";
    public static final int ARENA = 0;
    public static final int DUNGEON = 1;
    /**
     * Conversion number from pixels to tiles
     */
    public static final int PIXELS_TO_TILES = 64;
    /**
     * Spawn distance from player in tiles
     */
    public static final int SPAWN_DISTANCE = 7;
    public static final int OUTER_BORDER = 7;
    
    private boolean[][] areSpaces;
    private Pixmap[] blocks;
    private Pixmap[] spaces;
    private int spawnX;
    private int spawnY;
    
    private Texture map;
    private Texture expanse;
    
    /**
     * Constructs a map filled with walls
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
        for ( int i = 0; i < blocks.length; i++ )
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
     * For Testing
     * @param rows number of rows in map
     * @param cols number of columns in map
     * @param b to differentiate in testing
     */
    public Map( int rows, int cols, boolean b)
    {
        areSpaces = new boolean[rows][cols];
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
                createRoom( 0, 0, getMapTileWidth(), getMapTileHeight() );
                setSpawn( -1, -1 );
                break;
            case DUNGEON:
                randomGeneration();
                break;
        }
    }
    
    /**
     * Creates the map image to be displayed
     */
    public void createMap()
    {
        Pixmap pixmap1 = new Pixmap( getMapPixelWidth(), getMapPixelHeight(), Format.RGB888 );
        Pixmap pixmap2 = new Pixmap( getMapPixelWidth(), getMapPixelHeight(), Format.RGB888 );
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
        if ( w <= 0 || x >= getMapTileWidth() - 1
          || h <= 0 || y >= getMapTileHeight() - 1 ) return;
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
        return sizeOfRoom( x, y, copyAreSpaces() );
        // did not work as above code
        // return sizeOfRoom( x, y, areSpaces.clone() );
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
    
    
    
    public boolean hasPath( int x1, int y1, int x2, int y2, boolean asSpace )
    {
        return hasPath( x1, y1, x2, y2, asSpace, copyAreSpaces() );
        // does not work as above code
        // return hasPath( x1, y1, x2, y2, asSpace, areSpaces.clone() );
    }

    
    /**
     * hasPath() helper method recursively finds if a point reaches another point
     * @param x1 -coordinate of Point1
     * @param y1 -coordinate of Point1
     * @param x2 -coordinate of Point2
     * @param y2 -coordinate of Point2
     * @param asSpace 
     * @param map
     * @return
     */
    private static boolean hasPath( int x1, int y1, int x2, int y2, boolean asSpace, boolean[][] map )
    {
        if ( map[x1][y1] != asSpace
                        || x1 < 1 || x1 >= map.length - 1
                        || y1 < 1 || y1 >= map[0].length - 1 ) 
            return false;
        if ( x1 == x2 && y1 == y2 ) return true;
        map[x1][y1] = !asSpace;
        return hasPath( x1 - 1, y1, x2, y2, asSpace, map)
            || hasPath( x1 + 1, y1, x2, y2, asSpace, map)
            || hasPath( x1, y1 - 1, x2, y2, asSpace, map)
            || hasPath( x1, y1 + 1, x2, y2, asSpace, map);
    }

    // -----------------------Collision ------------------ //

    /**
     * Returns true if Sprite is in a wall
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
     * Draws the map
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
    
    private boolean[][] copyAreSpaces()
    {
        boolean[][] b = new boolean[getMapTileWidth()][getMapTileHeight()];
        for ( int i = 0; i < b.length; i++ )
            for ( int j = 0; j < b[i].length; j++ )
                b[i][j] = areSpaces[i][j];
        return b;
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
            else if ( !hasPath(point.x, point.y, p.x, p.y, true) )// && ( size == largest )
            {
                x = Math.min( point.x, p.x );
                y = Math.min( point.y, p.y );
                w = Math.abs( point.x - p.x );
                h = Math.abs( point.y - p.y );
                createRoom( x, y + h, w, 2 );
                createRoom( x, y, 2, h );
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
    @Override
    public String toString()
    {
        String s = "";
        for ( int i = 0; i < areSpaces.length; i++ ) {
            for ( int j = 0; j < areSpaces[0].length; j++ )
                s += ( areSpaces[i][j] ? ' ' : 'X' ) + " ";
            s += "\n";
        }
        return s;
    }
    
    public boolean[][] getAreSpaces()
    {
        return areSpaces;
    }
}
