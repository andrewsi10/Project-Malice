package com.mygdx.game.testers;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mygdx.game.MimicGdx;
import com.mygdx.game.sprites.Player;

/**
 *  JUnit tester for Player
 *
 *  @author  Som Pathak
 *  @author  Other contributors: Christopher Cheung
 *  @version Jun 1, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: Libgdx, JUnit4
 */
public class JUnitPlayerTest {

	/**
	 * Tests three initializations inside Player's constructor
	 */
	@Test
	public void testPlayer() {
		Player p = new Player();
		assertNotNull(p.getSpriteData().getSpeed());
		assertNotNull(p.getSpriteData().getExpToLevel());
		assertNotNull(p.getSpriteData().getLevel());
	}
	
	/**
	 * Tests Player's increasePoints() method for when increasePoints()
	 * increases playerPoints by 10
	 */
	@Test
	public void testIncreasePoints() {
		Player p = new Player();
		int points = p.getPoints();
		p.increasePoints();
		assertEquals(p.getPoints(), points + 10);
	}

	/**
	 * Tests Player's increaseExp() method for when it doesn't exceed expToLevel and
	 * when it does. expToLevel should equal 100 and player's level should equal 1.
	 */
	@Test
	public void testIncreaseExp() {
		MimicGdx.isTesting = true;
		Player p = new Player();
		// increaseExp() doesn't exceed expToLevel
		p.setExperience(0);
		p.increaseExp(10);
		assertEquals(p.getSpriteData().getExperience(), 10);
		// increaseExp() exceeds expToLevel
		p.setExperience(95);
		p.increaseExp(10);
		assertEquals(p.getSpriteData().getExperience(), 5);
		assertEquals(p.getSpriteData().getLevel(), 2);
		assertEquals(p.getSpriteData().getExpToLevel(), 100);
		MimicGdx.isTesting = false;
	}
}
