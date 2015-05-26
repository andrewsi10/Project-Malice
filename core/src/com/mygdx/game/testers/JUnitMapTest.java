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
    
//    @Test
//    public void sample1() {
//        Gdx.files.equals( getClass() );
//        assertTrue( true );
//    }
//    @Test
//    public void badlogicLogoExists() {
//        assertTrue(Gdx.files.internal("../../core/assets/img/splashscreen.png").exists());
//    }
    
    
    // ----------------------------Test Map ----------------------//
    public static final int MAP_SIZE = 25;

    public static final int x1 = 5;
    public static final int y1 = 5;
    public static final int w1 = 5;
    public static final int h1 = 5;
    
    @Test
    public void testMapRecursion()
    {
        Map map1 = new Map( MAP_SIZE, MAP_SIZE, true );
        assertEquals( "sizeOfRoom() should return zero at any point map when map is initialized", 
                                            map1.sizeOfRoom( 0, 0 ), 0 );
        map1.createRoom( x1, y1, w1, h1 );
        String s1 = map1 + "";
        assertTrue( "createRoom() Failed to create square", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        int size1 = map1.sizeOfRoom( x1, y1 );
        assertTrue( "sizeOfRoom() should not fill rooms", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        assertEquals( "sizeOfRoom() return incorrect value", size1, w1 * h1 );
        
        int x2 = 15;
        int y2 = 15;
        int w2 = 7;
        int h2 = 5;
        map1.createRoom( x2, y2, w2, h2 );
        String s2 = map1 + "";
        assertTrue( "createRoom() Failed to create rectangle", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );
        assertTrue( "createRoom() removed previous room", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        assertFalse( "hasPath() connected 2 points, Should not be connected", map1.hasPath( x1, y1, x2, y2, true ) );
        assertTrue( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, x1 + 1, y1 + 1, true ) );
        size1 = map1.sizeOfRoom( x1, y1 );
        assertTrue( "sizeOfRoom() should not modify rooms", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        assertEquals( "sizeOfRoom() return incorrect value", size1, w1 * h1 );
        int size2 = map1.sizeOfRoom( x2, y2 );
        assertTrue( "sizeOfRoom() should not modify rooms", checkRoom( x2, y2, w2, h2, true, map1.getAreSpaces() ) );
        assertEquals( "sizeOfRoom() return incorrect value", size2, w2 * h2 );
        
        map1.createRoom( x1 + w1, y1 + h1, w2, h2 );
        String s3 = map1 + "";
        assertTrue( "createRoom() removed previous room", checkRoom( x1, y1, w1, h1, true, map1.getAreSpaces() ) );
        assertFalse( "hasPath() did not connected 2 points, should be connected", map1.hasPath( x1, y1, x2, y2, true ) );
//        int size3 = map1.sizeOfRoom( x1, y1 );
//        assertTrue( "sizeOfRoom() should not modify rooms", checkRoom( x1, y1, w1, h1, map1.getAreSpaces() ) );
//        assertEquals( "sizeOfRoom() returned " + size3 + ", should be \n" + s1 + "\n\n" + s2 + "\n\n" + s3, size3, 2 * size1 + size2 );
        
        map1.fillRoom( x1, y1 );
        assertFalse( checkRoom( x1, y1, w1, h1, false, map1.getAreSpaces()));
//        map1.createRoom( x1, y1, w1, h1 );
//        assertTrue( checkRoom( x1, y1, w1, h1, map1.getAreSpaces() ) );
//        map1.createRoom( x1, y1, w1, h1 );
//        assertTrue( checkRoom( x1, y1, w1, h1, map1.getAreSpaces() ) );
    }
    
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
    
    public void testConversion()
    {
        for ( int i = 0; i < MAP_SIZE; i++ )
        {
            assertEquals( "tileToPixel() failed conversion", Map.tileToPixel( i ), i * Map.PIXELS_TO_TILES );
            assertEquals( "pixelToTile() failed conversion", Map.pixelToTile( i*Map.PIXELS_TO_TILES ), i );
        }
    }
    
    @Test
    public void testSpawn()
    {
        Map map = new Map( MAP_SIZE, MAP_SIZE, true );
        map.createRoom( x1, y1, w1, h1 );
        for ( int i = 0; i < 10; i++ )
        {
            map.setSpawn( -10, -10 );
            assertTrue( "Does not set Spawn on valid Tile", 
                map.getAreSpaces()[Map.pixelToTile( map.getSpawnX() )][Map.pixelToTile( map.getSpawnY() )] );
        }
    }

    
    // --------------------- main methods to run test------------------------ //
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUnitMapTest.class );
    }
    
    public static void main( String[] args )
    {
        org.junit.runner.JUnitCore.main( "JUnitMapTester" );
    }

}
