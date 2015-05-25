package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Malice;
import com.mygdx.game.world.Map;
public class JUnitTester
{
    
    @Before
    public void setUp() {
    }
    
//    @Test
//    public void sample() {
//        fail();
//    }
//
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

    public static String filledMap;
    
    @Test
    public void testMap() {
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
        assertTrue( "Generation must have at least one room", check );
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUnitTester.class );
    }
    
    public static void main( String[] args )
    {
        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
    }

}
