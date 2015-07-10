package com.mygdx.game.testers;
import static org.junit.Assert.*;
import org.junit.Test;

import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Character;
import com.mygdx.game.projectile.Projectile;

/**
 *  Tests the only testable method in Projectile
 *  
 *  Constructor and move() method in Projectile cannot be tested with JUnits 
 *  due to the animations in them
 *
 *  @author  Som Pathak
 *  @version Jun 1, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: Libgdx, JUnit4
 */
public class JUnitProjectileTest {
	
	/**
	 * Tests the hitcharacter( Character ) method
	 */
	@Test
	public void testHitCharacter()
	{
	    int x = 0;
	    int y = 0;
	    int w = 5;
	    int h = 5;
	    int d = 5;
        Projectile p = new Projectile( x, y, w, h, d );
	    Character c = new Enemy(); // new Character at (0,0)
	    c.setSize( w, h );
	    
	    int hp = c.getCurrentHp();
	    assertTrue( "Projectile did not collide with Character", p.hitCharacter( c ) ); // should collide
	    assertEquals( "Hp did not decrease after colliding", hp - d, c.getCurrentHp() );
	    
	    p.setPosition( 50, 50 ); // arbitrary location
        assertFalse( "Projectile should not collide with Character", p.hitCharacter( c ) ); // should not collide
        assertEquals( "Hp should not decrease when Projectile misses", 
            hp - d, c.getCurrentHp() ); // should not change hp
	}
}
