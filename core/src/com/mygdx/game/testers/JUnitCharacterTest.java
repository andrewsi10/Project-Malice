package com.mygdx.game.testers;

import static com.mygdx.game.sprites.AnimatedSprite.Direction.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.mygdx.game.MimicGdx;
import com.mygdx.game.sprites.Character;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Player;

/**
 *  JUnit tester for the Character class
 *
 *  @author  Christopher Cheung
 *  @version Jun 1, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx, junit4
 */
public class JUnitCharacterTest {

	/**
	 * Tests the Character's move() method for all 8 directions
	 */
	@Test
	public void testMove() {
	    float x = 0;
	    float y = 0;
		Character c = new Enemy(); // create Character at (0,0)
		c.setDirection(NORTH.getDirection());
		c.translate();
		assertEquals("", x, c.getX(), .0001);
        assertTrue("", y < c.getY()); // check if moved up
        x = c.getX();
        y = c.getY();
        c.setDirection(NORTHEAST.getDirection());
        c.translate();
        assertTrue("", x < c.getX()); // check if moved right
        assertTrue("", y < c.getY()); // check if moved up
        x = c.getX();
        y = c.getY();
        c.setDirection( EAST.getDirection() );
        c.translate();
        assertTrue("", x < c.getX()); // check if moved right
        assertEquals("", y, c.getY(), .0001);
        x = c.getX();
        y = c.getY();
        c.setDirection( SOUTHEAST.getDirection() );
        c.translate();
        assertTrue("", x < c.getX()); // check if moved right
        assertTrue("", y > c.getY()); // check if moved down
        x = c.getX();
        y = c.getY();
        c.setDirection( SOUTH.getDirection() );
        c.translate();
        assertEquals("", x, c.getX(), .0001);
        assertTrue("", y > c.getY()); // check if moved down
        x = c.getX();
        y = c.getY();
        c.setDirection( SOUTHWEST.getDirection() );
        c.translate();
        assertTrue("", x > c.getX()); // check if moved left
        assertTrue("", y > c.getY()); // check if moved down
        x = c.getX();
        y = c.getY();
        c.setDirection( WEST.getDirection() );
        c.translate();
        assertTrue("", x > c.getX()); // check if moved left
        assertEquals("", y, c.getY(), .0001);
        x = c.getX();
        y = c.getY();
        c.setDirection(NORTHWEST.getDirection());
        c.translate();
        assertTrue("", x > c.getX()); // check if moved left
        assertTrue("", y < c.getY()); // check if moved up
	}

	/**
	 * initializes a character at the origin and translates it dx across the x
	 * axis and dy across the y axis. Margin of error is set at .0001
	 */
	@Test
	public void testTranslate() {
		// initializes new Character and moves it
		Character c = new Player();
		float moveSpeed = c.getSpriteData().getSpeed();
		int dx = 1;
		int dy = 1;
		c.translate(dx, dy);
		assertEquals("Character: translate(" + dy + ", " + dx
				+ ") should result in the x-coordinate of the Character at "
				+ (moveSpeed * dx / Math.sqrt(2)),
				moveSpeed * dx / Math.sqrt(2), c.getX(), .0001);
		assertEquals("Character: translate(" + dy + ", " + dx
				+ ") should result in the y-coordinate of the Character at "
				+ (moveSpeed * dy / Math.sqrt(2)),
				moveSpeed * dy / Math.sqrt(2), c.getY(), .0001);
	}

	/**
	 * tests Character's increaseCurrentLevel() method
	 */
	@Test
	public void testIncreaseCurrentLevel() {
		MimicGdx.isTesting = true;
		Character c = new Player();
		c.takeDamage(25);
		int level = c.getSpriteData().getLevel() + 1;
		int BDmg = c.getBDmg() + 2;
		int increasedHp = c.getCurrentHp()
				+ (int) (10 * (c.getSpriteData().getHpRatio() + 1));
		int maxHp = c.getSpriteData().getMaxHp() + 10;
		c.increaseCurrentLevel();
		assertEquals(c.getSpriteData().getLevel(), level);
		assertEquals(c.getBDmg(), BDmg);
		assertEquals(c.getSpriteData().getMaxHp(), maxHp);
		assertEquals(c.getCurrentHp(), increasedHp);
		MimicGdx.isTesting = false;
	}

}
