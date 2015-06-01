package com.mygdx.game.testers;

import static org.junit.Assert.*;
import org.junit.Test;
import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Character;

/**
 *  JUnit tester for the Enemy class
 *
 *  @author  Christopher Cheung
 *  @version Jun 1, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: Libgdx, JUnit4
 */
public class JUnitEnemyTest {

	/**
	 * tests for the constructor: setting random direction, setting random
	 * travel time, setting experience, setting enemy speed
	 */
	@Test
	public void testEnemy() {
		Enemy e = new Enemy();
		assertEquals(e.getExperience(), 20);
		assertEquals(e.getSpeed(), 3, .001);
		assertTrue(e.getDirection() >= 0 && e.getDirection() < 8);
		assertTrue(e.getTravelTime() >= e.getMinTravelTime()
				&& e.getTravelTime() < e.getMinTravelTime()
						+ e.getTravelTimeScalar());
	}

	/**
	 * tests setRandomDirection() for when travelTime is less than 1 and when
	 * travelTime is great than 1
	 */
	@Test
	public void testSetRandomDirection() {
		Enemy e = new Enemy();
		// travelTime >= 1
		e.setTravelTime(3);
		e.setRandomDirection();
		assertEquals(e.getTravelTime(), 2);

		// travelTime < 1
		e.setTravelTime(0);
		e.setRandomDirection();
		assertTrue(e.getDirection() >= 0 && e.getDirection() < 8);
		assertTrue(e.getTravelTime() >= e.getMinTravelTime()
				&& e.getTravelTime() < e.getMinTravelTime()
						+ e.getTravelTimeScalar());
	}

	/**
	 * tests every possible case for Enemy's getDirection(float, float) method
	 */
	@Test
	public void testGetDirection() {
	    int d = Enemy.marginOfDelta;
	    Enemy e = new Enemy();
        float dx = 0;
        float dy = 0;
        assertEquals(-1, e.getDirection(dx,dy), .0001); // check for not moving
        dy = d + 1;
        assertEquals(Character.NORTH, e.getDirection(dx,dy), .0001);
        dx = d + 1;
        assertEquals(Character.NORTHEAST, e.getDirection(dx,dy), .0001);
        dy = 0;
        assertEquals(Character.EAST, e.getDirection(dx,dy), .0001);
        dy = -d - 1;
        assertEquals(Character.SOUTHEAST, e.getDirection(dx,dy), .0001);
        dx = 0;
        assertEquals(Character.SOUTH, e.getDirection(dx,dy), .0001);
        dx = -d - 1;
        assertEquals(Character.SOUTHWEST, e.getDirection(dx,dy), .0001);
        dy = 0;
        assertEquals(Character.WEST, e.getDirection(dx,dy), .0001);
        dy = d + 1;
        assertEquals(Character.NORTHWEST, e.getDirection(dx,dy), .0001);
	}

	/**
	 * tests inRange(Character) method for Enemy
	 */
	@Test
	public void testInRange() {
		Character c = new Character();
		Enemy e = new Enemy();
		// tests for character in the positive x-direction
		c.setPosition(e.getAggroDistance() + 1, 0);
		assertFalse(e.inRange(c));
		// tests for character in the negative x-direction
		c.setPosition(-e.getAggroDistance() + 1, 0);
		assertTrue(e.inRange(c));
		// tests for character in the positive y-direction
		c.setPosition(0, e.getAggroDistance() + 1);
		assertFalse(e.inRange(c));
		// tests for character in the negative y-direction
		c.setPosition(0, -e.getAggroDistance() + 1);
		assertTrue(e.inRange(c));
		// tests for inRange(Character)	using the Distance Formula
		c.setPosition((float) (e.getAggroDistance() / Math.sqrt(2) - 1),
				(float) (e.getAggroDistance() / Math.sqrt(2) - 1));
		assertTrue(e.inRange(c));
	}

}
