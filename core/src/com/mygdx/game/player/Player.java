package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Player extends Character {

	private String currentAtlasKey = new String("0");
	private TextureAtlas textureAtlas;
	private int currentFrame;
	private int animationSpeed = 15;
	private float moveSpeed = 5;

	public Player(String file, String startFrame) {
		super(file, startFrame);
		textureAtlas = getAtlas();
	}

	public void move() {
		if (currentFrame < animationSpeed* 2 - 1) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		// northeast
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			setDirection(NORTHEAST);
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southeast
		else if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D)) {
			setDirection(SOUTHEAST);
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S)) {
			setDirection(SOUTHWEST);
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// northwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			setDirection(NORTHWEST);
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// north
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			setDirection(NORTH);
			translateY(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// east
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			setDirection(EAST);
			translateX(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// south
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			setDirection(SOUTH);
			translateY(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// west
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			setDirection(WEST);
			translateX(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
	}

	public void strafe() {
		if (currentFrame < 29) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		// northeast
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
		}
		// southeast
		else if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D)) {
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
		}
		// southwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S)) {
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
		}
		// northwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
		}
		// north
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			translateY(moveSpeed);
		}
		// east
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			translateX(moveSpeed);
		}
		// south
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			translateY(-moveSpeed);
		}
		// west
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			translateX(-moveSpeed);
		}

		if (getDirection() == 0) {
			strafeUpSprite();
		} else if (getDirection() == 1 || getDirection() == 2
				|| getDirection() == 3) {
			strafeRightSprite();
		} else if (getDirection() == 4) {
			strafeDownSprite();
		} else if (getDirection() == moveSpeed || getDirection() == 6
				|| getDirection() == 7) {
			strafeLeftSprite();
		}
	}

	public void strafeLeftSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / animationSpeed + 6);
		setRegion(textureAtlas.findRegion(currentAtlasKey));
	}

	public void strafeRightSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / animationSpeed
				+ 2);
		setRegion(textureAtlas.findRegion(currentAtlasKey));

	}

	public void strafeUpSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / animationSpeed);
		setRegion(textureAtlas.findRegion(currentAtlasKey));

	}

	public void strafeDownSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / animationSpeed
				+ 4);
		setRegion(textureAtlas.findRegion(currentAtlasKey));
	}

	@Override
	void die() {

	}

}
