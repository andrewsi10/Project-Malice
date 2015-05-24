package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class Player extends Character {

	public Player(String file) {
		super(new Array<AtlasRegion>(new AtlasRegion[] { // up animation new Array
				new TextureAtlas(Gdx.files.internal(file)).findRegion("0"),
				new TextureAtlas(Gdx.files.internal(file)).findRegion("1") }),
				new Array<AtlasRegion>( // right animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("2"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("3") }),
				new Array<AtlasRegion>( // down animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("4"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("5") }),
				new Array<AtlasRegion>( // left animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("6"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("7") }));
	}

	public void move() {
		// if (currentFrame < animationSpeed* 2 - 1) {
		// currentFrame++;
		// } else {
		// currentFrame = 0;
		// }
		int dir = getInputDirection();
		if (dir != -1) {
			move(dir);
		}
	}

	// public void strafe() {
	// if (currentFrame < 29) {
	// currentFrame++;
	// } else {
	// currentFrame = 0;
	// }
	//
	// int dir = getInputDirection();
	// if ( dir != -1 )
	// {
	// int direction = this.getDirection();
	// super.move( dir );
	// setDirection( direction );
	// }
	// if (getDirection() == 0) {
	// strafeSprite( 0 );
	// } else if (getDirection() == 1 || getDirection() == 2
	// || getDirection() == 3) {
	// strafeSprite( 2 );
	// } else if (getDirection() == 4) {
	// strafeSprite( 4 );
	// } else if (getDirection() == 5 || getDirection() == 6
	// || getDirection() == 7) {
	// strafeSprite( 6 );
	// }
	// }

//	private void strafeSprite(int atlas) {
//		setAtlas(atlas);
//		setRegion(textureAtlas.findRegion(currentAtlasKey));
//	}
//
//	private void setAtlas(int change) {
//		currentAtlasKey = String.format("%01d", currentFrame / animationSpeed
//				+ change);
//	}

	@Override
	protected void move(int dir) {
		super.move(dir);
//		strafeSprite(getAtlasNumber(dir));
	}

	private int getInputDirection() {
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W))
			return NORTHEAST;
		if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D))
			return SOUTHEAST;
		if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S))
			return SOUTHWEST;
		if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W))
			return NORTHWEST;
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			return NORTH;
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			return EAST;
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			return SOUTH;
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			return WEST;
		return -1;
	}

	/**
	 * Returns atlas number for animation based on direction
	 * 
	 * @param dir
	 *            Direction represented by an int
	 * @return atlas number
	 */
//	private int getAtlasNumber(int dir) {
//		switch (dir) {
//		case NORTH:
//			return 0;
//		case NORTHEAST:
//		case SOUTHEAST:
//		case EAST:
//			return 2;
//		case SOUTH:
//			return 4;
//		case NORTHWEST:
//		case SOUTHWEST:
//		case WEST:
//			return 6;
//		}
//		return -1;
//	}

}
