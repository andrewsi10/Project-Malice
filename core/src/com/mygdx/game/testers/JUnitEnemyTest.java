package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Character;

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
