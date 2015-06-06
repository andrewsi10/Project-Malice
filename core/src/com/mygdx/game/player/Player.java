package com.mygdx.game.player;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;
import com.mygdx.game.Options;

/**
 *  This class represents a Player in the game. Takes in Input from the keyboard
 *  in order to move around and shoot.
 *
 *  @author  Christopher Cheung
 *  @author  Other contributors: Andrew Si, Nathan Lui
 *  @version May 31, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources: libgdx
 */
public class Player extends Character {

	private String projectile;

	private int playerPoints = 0;

	/**
	 * The constructor for player. The first parameter is used to reference the
	 * file names of the images to create the Arrays for the super constructor.
	 * The second parameter is used to reference the file name for the
	 * projectile. Initialize variable speed to 5, expToLevel to 100, and level
	 * to 1.
	 * 
	 * @param file
	 *            reference for the player sprite atlas file
	 * @param proj
	 *            reference for the projectile atlas file
	 */
	public Player(String file, String proj) {
		super(new Array<AtlasRegion>(
				new AtlasRegion[] { // up animation new Array
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("0"),
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("1") }), new Array<AtlasRegion>(
				// right animation new Array
				new AtlasRegion[] {
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("2"),
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("3") }), new Array<AtlasRegion>(
				// down animation new Array
				new AtlasRegion[] {
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("4"),
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("5") }), new Array<AtlasRegion>(
				// left animation new Array
				new AtlasRegion[] {
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("6"),
						new TextureAtlas(Gdx.files.internal(file))
								.findRegion("7") }));
		setSpeed(5);
		setExpToLevel(100);
		setLevel(1);
		projectile = proj;
	}

	/**
	 * Constructor used for testing only. It does not use the super constructor
	 * or initialize projectile. Initialize speed, expToLevel, and level as
	 * accordingly with the regular constructor.
	 */
	public Player() {
		setSpeed(5);
		setExpToLevel(100);
		setLevel(1);
	}

	/**
	 * @see com.mygdx.game.player.Character#move(com.mygdx.game.player.Character, java.util.ArrayList, long)
	 * 
	 * moves the Character according to the input of keyboard gotten from 
	 * MimicGdx class
	 * 
	 * @param character main Character to interact with -- not used in Player
	 * @param projectiles ArrayList of Projectiles to add this Player's 
	 *                     projectile into the environment when shooting
	 * @param time Time in game (used in order to determine delays in moving or
     *            shooting)
	 */
	@Override
	public void move(Character character, ArrayList<Projectile> projectiles,
			long time) {
		int dir = Options.getInputDirection();
		if (dir != -1) {
			setDirection(dir);
			super.move(character, projectiles, time);
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			shoot(projectiles, Gdx.input.getX() - Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - Gdx.input.getY(),
					System.currentTimeMillis(), projectile);
		}
	}

	/**
	 * increases displayed player points by a unit of 10
	 */
	public void increasePoints() {
		playerPoints += 10;
	}

	/**
	 * Increases the experience of Player (variable inherited by Character). If
	 * experience exceeds expToLevel, increment level by one and set experience
	 * to the remaining experience after reaching expToLevel.
	 * 
	 * @param exp
	 *            current value of experience
	 */
	public void increaseExp(int exp) {
		this.setExperience(getExperience() + exp);
		if (getExperience() >= getExpToLevel()) {
			setExperience(getExperience() - getExpToLevel());
			increaseCurrentLevel();
			setExpToLevel(getExpToLevel() * getCurrentLevel() / 2);
		}
	}

	// --------------------Getters & Setters------------------ //

	/**
	 * returns the amount of points earned by the Player
	 * 
	 * @return points earned by the player
	 */
	public int getPoints() {
		return playerPoints;
	}

}
