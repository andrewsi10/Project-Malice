package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.world.Map;
/**
 *  Tests the map class
 *
 * note: all coordinates are based off of a bottom left origin
 *  @author  Nathan Lui
 *  @version May 30, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources:
 */
public class JUnitMapTest
{
    // ----------------------------Test Map ----------------------//
    public static final int MAP_SIZE = 25;
    
    /**
     * Tests the map's static portion of random generation
     * random generation must maintain a border of walls
     * map should start out as all walls when first initialized
     */
    @Test
    public void testMapGeneration() {
        Map map1 = new Map( MAP_SIZE, MAP_SIZE, true );
        Map map2 = new Map( MAP_SIZE, MAP_SIZE, true );
        map2.generate( Map.DUNGEON );
        assertNotNull( "Map testing constructor does no initialize array", map1.getAreSpaces() );
        assertTrue( "Map should start out filled with walls", checkRoom( 0, 0, MAP_SIZE, MAP_SIZE, false, map1.getAreSpaces()) );
        boolean check = false;
        for ( int i = 0; i < MAP_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_SIZE; j++ )
            {
                assertFalse( "Map Should start out filled with walls", map1.getAreSpaces()[i][j] );
                if ( i == 0 || i == MAP_SIZE - 1 
                  || j == 0 || j == MAP_SIZE - 1 )
                    assertFalse( "Map should have a border of walls after generation", map2.getAreSpaces()[i][j] );
                if ( map2.getAreSpaces()[i][j] ) check = true;
            }
        }
        assertTrue( "Generation must have at least one space", check );
    }
    
    /**
     * Tests all the map's recursive methods:
     * createRoom(): creates a rectangular room given x,y,w,h
     * createBox(): creates a rectangle (inside not touched) given x,y,w,h
     * sizeOfArea(): returns the size of Area
     * fillArea: fills an area
     * hasPath(): returns whether 2 points can be connected
     */
    @Test
    public void testMapRecursion()
    {
        int x1 = 5;
        int y1 = 5;
        int w1 = 5;
        int h1 = 5;
        int size1 = w1 * h1;
        int x2 = 15;
        int y2 = 10;
        int w2 = 7;
        int h2 = 5;
        int size2 = w2 * h2;
        int rectX = 1;
        int rectY = 1;
        int rectW = MAP_SIZE - 2;
        int rectH = rectW;
        int rectSize = 2 * rectW + 2 * rectH - 4;
        Map map1 = new Map( MAP_SIZE, MAP_SIZE, true );
        assertEquals( "sizeOfRoom() should return zero at any point map when map is initialized", 
                                            map1.sizeOfArea( 0, 0 ), 0 );
        // ------------------------- createRooms():
        // room1
        map1.createRoom( x1, y1, w1, h1 );
        assertTrue( "createRoom() Failed to create square", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        // room2
        map1.createRoom( x2, y2, w2, h2 );
        assertTrue( "createRoom() Failed to create rectangle", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );
        assertTrue( "createRoom() removed previous room", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        // room3
        map1.createRoom( y2, x2, h2, w2 );
        assertTrue( "createRoom() Failed to create rectangle", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );
        assertTrue( "createRoom() removed previous room", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        assertTrue( "createRoom() removed previous room", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );

        // ------------------------- createRectangle():
        map1.createRectangle( rectX, rectY, rectW, rectH );
        assertTrue( "createRectangle did not properly create a rectangle", 
            checkRectangle( rectX, rectY, rectW, rectH, true, map1.getAreSpaces() ) );
        assertFalse( "createRectangle should not fill the room", 
            checkRoom( rectX + 1, rectY + 1, rectW - 1, rectH - 1, true, map1.getAreSpaces() ) );

        // ------------------------- sizeOfArea():
        // check size of room1
        assertEquals( "sizeOfArea() returned incorrect value", size1, map1.sizeOfArea( x1, y1 ) );
        assertTrue( "sizeOfArea() should not fill rooms", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        // check size of room2
        assertEquals( "sizeOfArea() returned incorrect value", size2, map1.sizeOfArea( x2, y2 ) );
        assertTrue( "sizeOfArea() should not modify rooms", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );
        // check size of room3
        assertEquals( "sizeOfArea() returned incorrect value", size2, map1.sizeOfArea( y2, x2 ) );
        assertTrue( "sizeOfArea() should not modify rooms", checkRoom( y2, x2, h2, w2, true, map1.getAreSpaces() ) );
        // check size of rectangle
        assertEquals( "sizeOfArea() returned incorrect value", rectSize, map1.sizeOfArea( rectX, rectY ) );
        assertTrue( "sizeOfArea() should not modify rectangle", 
            checkRectangle( rectX, rectY, rectW, rectH, true, map1.getAreSpaces() ) );
        
        // ------------------------- hasPath(): -checks pathing between rooms and rectangle
        // room1 to itself
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, x1 + 1, y1 + 1, true ) );
        // room1 to room2
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, x2, y2, true ) );
        // room1 to room3
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, y2, x2, true ) );
        // room2 to room3
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x2, y2, y2, x2, true ) );
        // room1 to rectangle
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, rectX, rectY, true ) );
        
        // RESULTING MAP SO FAR (what it should be at this point):
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X                                               X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X X X X X   room3   X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X X X X X X X X X X               X   X 
//        X   X X X X X X X X X X X X X               X   X 
//        X   X X X X X X X X X X X X X     room2     X   X 
//        X   X X X X X X X X X X X X X               X   X 
//        X   X X X X X X X X X X X X X               X   X 
//        X   X X X           X X X X X X X X X X X X X   X 
//        X   X X X           X X X X X X X X X X X X X   X 
//        X   X X X   room1   X X X X X X X X X X X X X   X 
//        X   X X X           X X X X X X X X X X X X X   X 
//        X   X X X           X X X X X X X X X X X X X   X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X                                               X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
        // note: 0,0 is at bottom left corner
        
        // testing additional conditions (considering overlaping):
        int x3 = 8;
        int y3 = 8;
        int w3 = 7;
        int h3 = 7;
        int rectX2 = 6;
        int rectY2 = 6;
        int rectW2 = 10;
        int rectH2 = 15;
        int size3 = w3 * h3;
        int totalSize = size1 + 2 * size2 + size3 - 4;
        // ------------------------- createRoom(): (with overlap)
        map1.createRoom( x3, y3, w3, h3 );
        assertTrue( "createRoom() did not fully create a room when overlapped", checkRoom( x3, y3, w3, h3, true, map1.getAreSpaces() ) );
        assertTrue( "createRoom() removed previous room", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        // ------------------------- sizeOfArea():
        assertEquals( "sizeOfRoom() did not return size of total area", totalSize, map1.sizeOfArea( x3, y3 ) );

        // ------------------------- createRectangle():
        map1.createRectangle( rectX2, rectY2, rectW2, rectH2 );
        assertTrue( "createRectangle did not properly create a rectangle", 
            checkRectangle( rectX2, rectY2, rectW2, rectH2, true, map1.getAreSpaces() ) );
        assertFalse( "createRectangle should not fill the room", 
            checkRoom( rectX2 + 1, rectY2 + 1, rectW2 - 1, rectH2 - 1, true, map1.getAreSpaces() ) );
        
        // ------------------------- hasPath():
        // all middle rooms should be connected:
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, x2, y2, true ) );
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( y2, x2, x2, y2, true ) );
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, y2, x2, true ) );
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, rectX2, rectY2, true ) );
        // should not be connected to first rectangle
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, rectX, rectY, true ) );
        
        // What map1 should be at this point:
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X                                               X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X   X X X X X X X X           X X X X X X X X   X 
//        X   X X X X                     X X X X X X X   X 
//        X   X X X X   X X X             X X X X X X X   X 
//        X   X X X X   X X X             X X X X X X X   X 
//        X   X X X X   X X X             X X X X X X X   X 
//        X   X X X X   X X X             X X X X X X X   X 
//        X   X X X X   X X X             X X X X X X X   X 
//        X   X X X X   X                             X   X 
//        X   X X X X   X                             X   X 
//        X   X X X X   X                             X   X 
//        X   X X X X   X                             X   X 
//        X   X X X X   X                             X   X 
//        X   X X X                       X X X X X X X   X 
//        X   X X X                       X X X X X X X   X 
//        X   X X X           X X X X X   X X X X X X X   X 
//        X   X X X                       X X X X X X X   X 
//        X   X X X           X X X X X X X X X X X X X   X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X   X X X X X X X X X X X X X X X X X X X X X   X 
//        X                                               X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 

        // ------------------------- fillArea():
        // fill entire middle area (clears all rooms and second rectangle)
        map1.fillArea( x1, y1 );
        assertTrue( "fillArea() failed to fill entire area", checkRoom( x1, y1, w1, h1, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( x2, y2, w2, h2, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( y2, x2, h2, w2, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( x3, y3, w3, h3, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( rectX2, rectY2, rectW2, rectH2, false, map1.getAreSpaces()));
        // fill first rectangle
        map1.fillArea( rectX, rectY );
        assertTrue( "fillArea() failed to fill entire area", checkRoom( rectX, rectY, rectW, rectH, false, map1.getAreSpaces()));
        // entire map should be filled:
        assertTrue( "fillArea() failed to fill entire area", checkRoom( 0, 0, MAP_SIZE, MAP_SIZE, false, map1.getAreSpaces()));
    }
    
    /**
     * Checks whether given array has a room
     * Preconditions: x >= 0 and y >= 0
     * @param x starting coordinate
     * @param y starting coordinate
     * @param w width of room
     * @param h height of room
     * @param shouldBe boolean state that the room should be
     * @param b 2D array of booleans to check
     * @return true if all tiles in designated spots in array are what they 
     *          shouldBe
     */
    public static boolean checkRoom( int x, int y, int w, int h, boolean shouldBe, boolean[][] b )
    {
        for ( int i = x; i < x + w && i < b.length; i++ )
        {
            for ( int j = y; j < y + h && j < b[i].length; j++ )
            {
                if ( b[i][j] != shouldBe ) return false;
            }
        }
        return true;
    }
    
    /**
     * Checks whether given array has a rectangle
     * 
     * Preconditions: rectangle is not outside of array
     * @param x starting coordinate
     * @param y starting coordinate
     * @param w width of rectangle
     * @param h height of rectangle
     * @param shouldBe boolean that the rectangle should be
     * @param b 2D array of booleans to check
     * @return true if all tiles in designated spots in array are what they 
     *          shouldBe
     */
    public static boolean checkRectangle( int x, int y, int w, int h, boolean shouldBe, boolean[][] b )
    {
        int i;
        for ( i = 0; i < Math.min( w, h ); i++ )
        {
            if ( b[x][y + i] != shouldBe || b[x + w - 1][y + i] != shouldBe
              || b[x + i][y] != shouldBe || b[x + i][y + h - 1] != shouldBe ) 
                return false;
        }
        if ( w != h ) { // keep going if not a square
            for ( ; i < Math.max( w, h ); i++ )
            {
                if ( w > h && ( b[x + i][y] != shouldBe || b[x + i][y + h - 1] != shouldBe ) 
                  || w < h && ( b[x][y + i] != shouldBe || b[x + w - 1][y + i] != shouldBe ) )
                        return false;
            }
        }
        return true;
    }
    
    
    /**
     * Tests the tileToPixel() and pixelToTile() conversions
     */
    @Test
    public void testConversion()
    {
        for ( int i = 0; i < MAP_SIZE; i++ )
        {
            assertEquals( "tileToPixel() failed conversion", Map.tileToPixel( i ), i * Map.PIXELS_TO_TILES );
            assertEquals( "pixelToTile() failed conversion", Map.pixelToTile( i*Map.PIXELS_TO_TILES ), i );
        }
    }
    
    /**
     * Limited testing of the spawn x, y -coordinates, makes sure spawn point 
     * is valid (not in a wall) and not too close to given coordinate
     * 
     * tests the setSpawn() method
     */
    @Test
    public void testSpawn()
    {
        int x1 = 5;
        int y1 = 5;
        int w1 = 5;
        int h1 = 5;
        Map map = new Map( MAP_SIZE, MAP_SIZE, true );
        map.createRoom( x1, y1, w1, h1 );
        
        int avoid = Map.tileToPixel( MAP_SIZE / 2 ); // pixel coordinate to avoid
        int spawnX;
        int spawnY;
        for ( int i = 0; i < 10; i++ )
        {
            map.setSpawn( -10f, -10f );
            spawnX = Map.pixelToTile( map.getSpawnX() );
            spawnY = Map.pixelToTile( map.getSpawnY() );
            assertTrue( "Does not set Spawn on valid Tile", 
                map.getAreSpaces()[spawnX][spawnY] );

            map.setSpawn( avoid, avoid );
            spawnX = Map.pixelToTile( map.getSpawnX() );
            spawnY = Map.pixelToTile( map.getSpawnY() );
            assertTrue( "Does not set Spawn on valid Tile", 
                map.getAreSpaces()[spawnX][spawnY] );
            assertFalse( "Should not set Spawn too close to given coordinate", 
                  spawnX > MAP_SIZE / 2 - Map.SPAWN_DISTANCE 
               && spawnX < MAP_SIZE / 2 + Map.SPAWN_DISTANCE
               && spawnY > MAP_SIZE / 2 - Map.SPAWN_DISTANCE
               && spawnY < MAP_SIZE / 2 + Map.SPAWN_DISTANCE);
        }
    }
    
    /**
     * Tests the isCollidingWithWall() method in the map class
     */
    @Test
    public void testWallCollision()
    {
        int x = 5, y = 5, w = 5, h = 5;
        int d = 64; // any dimensions other than 0
        Map map = new Map( MAP_SIZE, MAP_SIZE, true );
        map.createRoom( x, y, w, h );
        
        Sprite sprite = new Sprite();
        // set sprite in the wall
        sprite.setBounds( 0, 0, d, d );
        assertTrue( "isCollidingWithWall() should return true when sprite is in wall", map.isCollidingWithWall( sprite ) );
        
        // set sprite next to wall
        sprite.setPosition( Map.tileToPixel( x ), Map.tileToPixel( y ) ); 
        assertFalse( "isCollidingWithWall() should return false when sprite is not in wall" + sprite.getBoundingRectangle(), map.isCollidingWithWall( sprite ) );
        
        // set sprite inside room
        sprite.setPosition( Map.tileToPixel( x + 1 ), Map.tileToPixel( y + 1 ) );
        assertFalse( "isCollidingWithWall() should return false when sprite is not in wall" + sprite.getBoundingRectangle(), map.isCollidingWithWall( sprite ) );
        
    }

    
    // --------------------- main methods to run test------------------------ //
    /**
     * Returns this testing class for testing
     * @return new JUnit4TestAdapter( JUnitMapTest.class );
     */
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUnitMapTest.class );
    }
    
    /**
     * Runs the JUnit tester
     * @param args String array of command line arguments -- not used
     */
    public static void main( String[] args )
    {
        org.junit.runner.JUnitCore.main( "JUnitMapTester" );
    }

}
