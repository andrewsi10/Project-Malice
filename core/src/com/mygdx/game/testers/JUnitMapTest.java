package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.mygdx.game.world.Map;
public class JUnitMapTest
{
    
    @Before
    public void setUp() {
    }
    
    
    // ----------------------------Test Map ----------------------//
    public static final int MAP_SIZE = 25;

    public static final int x1 = 5;
    public static final int y1 = 5;
    public static final int w1 = 5;
    public static final int h1 = 5;
    
    // "\n" + s1 + "\n\n" + s2 + "\n\n" + s3 + "\n\n" + map1
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
        int size1 = w1 * h1;
        int x2 = 15;
        int y2 = 10;
        int w2 = 8;
        int h2 = 5;
        int size2 = w2 * h2;
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

        // ------------------------- sizeOfArea():
        // check size of room1
        assertTrue( "sizeOfArea() should not fill rooms", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        assertEquals( "sizeOfArea() returned incorrect value", size1, map1.sizeOfArea( x1, y1 ) );
        // check size of room2
        assertTrue( "sizeOfArea() should not modify rooms", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );
        assertEquals( "sizeOfArea() returned incorrect value", size2, map1.sizeOfArea( x2, y2 ) );
        // check size of room3
        assertTrue( "sizeOfArea() should not modify rooms", checkRoom( y2, x2, h2, w2, true, map1.getAreSpaces() ) );
        assertEquals( "sizeOfArea() returned incorrect value", size2, map1.sizeOfArea( y2, x2 ) );
        
        // ------------------------- hasPath():
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, x1 + 1, y1 + 1, true ) );
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, x2, y2, true ) );
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, y2, x2, true ) );
        
        // resulting map for above part (part 1):
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X   room3   X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X X X X X X               X X X 
//        X X X X X X X X X X X X X X X               X X X 
//        X X X X X X X X X X X X X X X     room2     X X X 
//        X X X X X X X X X X X X X X X               X X X 
//        X X X X X X X X X X X X X X X               X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X   room1   X X X X X X X X X X X X X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
        
        // testing additional conditions (not only isolated rectangles):
        int x3 = 8;
        int y3 = 8;
        int w3 = 7;
        int h3 = 7;
        int size3 = w3 * h3;
        int totalSize = size1 + 2 * size2 + size3 - 4;
        // ------------------------- createRoom(): (with overlap)
        map1.createRoom( x3, y3, w3, h3 );
        assertTrue( "createRoom() did not fully create a room when overlapped", checkRoom( x3, y3, w3, h3, true, map1.getAreSpaces() ) );
        assertTrue( "createRoom() removed previous room", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        // ------------------------- sizeOfArea():
        assertEquals( "sizeOfRoom() did not return size of total area", totalSize, map1.sizeOfArea( x3, y3 ) );
        // ------------------------- hasPath():
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, x2, y2, true ) );
        // resulting map for part 2 of testing:
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X X X           X X X X X X X X X X 
//        X X X X X X X X                             X X X 
//        X X X X X X X X                             X X X 
//        X X X X X X X X                             X X X 
//        X X X X X X X X                             X X X 
//        X X X X X X X X                             X X X 
//        X X X X X                     X X X X X X X X X X 
//        X X X X X                     X X X X X X X X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X           X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 
//        X X X X X X X X X X X X X X X X X X X X X X X X X 


        

        // ------------------------- fillArea():
        map1.fillArea( x1, y1 );
        assertTrue( "fillArea() failed to fill entire area", checkRoom( x1, y1, w1, h1, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( x2, y2, w2, h2, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( y2, x2, h2, w2, false, map1.getAreSpaces()));
        assertTrue( "fillArea() failed to fill entire area", checkRoom( x3, y3, w3, h3, false, map1.getAreSpaces()));
    }
    
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
        assertNotNull( map1.getAreSpaces() );
        assertEquals( map1.toString(), map1.toString() );
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
     * Checks whether given array has a room
     * @param x starting coordinate
     * @param y starting coordinate
     * @param w width of room
     * @param h height of room
     * @param shouldBe boolean that the room should be
     * @param b 2D array of booleans to check
     * @return true if all tiles in designated spots in array are what they 
     *          shouldBe
     */
    public static boolean checkRoom( int x, int y, int w, int h, boolean shouldBe, boolean[][] b )
    {
        for ( int i = x; i < x + w && i < b.length; i++ )
        {
            for ( int j = y; j < y + h; j++ )
            {
                if ( !b[i][j] ) return !shouldBe;
            }
        }
        return shouldBe;
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
     */
    @Test
    public void testSpawn()
    {
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
