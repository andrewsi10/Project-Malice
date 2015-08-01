package com.mygdx.game.testers;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mygdx.game.sprites.Enemy;

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
		assertTrue(e.getTravelTime() >= Enemy.minTravelTime
				&& e.getTravelTime() < Enemy.minTravelTime
						+ Enemy.travelTimeScalar);
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
		assertTrue(e.getTravelTime() >= Enemy.minTravelTime
				&& e.getTravelTime() < Enemy.minTravelTime
						+ Enemy.travelTimeScalar);
	}

//	/**
//	 * tests inRange(Character) method for Enemy
//	 */
//	@Test
//	public void testInRange() {
//		Character c = new Enemy();
//		Enemy e = new Enemy();
//		// tests for character in the positive x-direction
//		c.setPosition(Enemy.aggroDistance + 1, 0);
//		assertFalse(e.inRange(c));
//		// tests for character in the negative x-direction
//		c.setPosition(-Enemy.aggroDistance + 1, 0);
//		assertTrue(e.inRange(c));
//		// tests for character in the positive y-direction
//		c.setPosition(0, Enemy.aggroDistance + 1);
//		assertFalse(e.inRange(c));
//		// tests for character in the negative y-direction
//		c.setPosition(0, -Enemy.aggroDistance + 1);
//		assertTrue(e.inRange(c));
//		// tests for inRange(Character)	using the Distance Formula
//		c.setPosition((float) (Enemy.aggroDistance / Math.sqrt(2) - 1),
//				(float) (Enemy.aggroDistance / Math.sqrt(2) - 1));
//		assertTrue(e.inRange(c));
//	}

}
