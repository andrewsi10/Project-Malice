package com.mygdx.game.testers;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.mygdx.game.world.Map;

public class JUnitProjectileTest {
	
	
	
	
	

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUnitMapTest.class );
    }
    
    public static void main( String[] args )
    {
        org.junit.runner.JUnitCore.main( "JUnitMapTester" );
    }


}
