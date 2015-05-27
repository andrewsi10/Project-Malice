package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.mygdx.game.MimicGdx;
import com.mygdx.game.player.Character;
import com.mygdx.game.MimicGdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class JUnitCharacterTest {

	private int moveSpeed = 10;
	private int dy = 1;
	private int dx = 1;
	private Array<AtlasRegion> frames = new TextureAtlas(
			Gdx.files.internal("img/sprites/Enemies/Enemy1/Enemy1.atlas"))
			.getRegions();

	/**
	 * finds the distance between the coordinate (x, y) and (0, 0)
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return the distance from the origin to (x, y)
	 */
	public double distanceFormula(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	@Test
	public void testMove() {

	}

	/**
	 * initializes a character at the origin and translates it dx across the x
	 * axis and dy across the y axis. Margin of error is set at .0001
	 */
	@Test
	public void testTranslate() {
		Character c = new Character(frames);
		c.setPosition(0f, 0f);
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
}
