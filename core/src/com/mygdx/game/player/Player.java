package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Player extends Character {

	private String currentAtlasKey = new String("0");
	private TextureAtlas textureAtlas;
	private int currentFrame;

	public Player() {
		super();
		textureAtlas = getAtlas();
	}

	public void move() {
		if (currentFrame < 29) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		// northeast
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			setDirection(1);
			translateX((float) (5 / Math.sqrt(2)));
			translateY((float) (5 / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame / 15 + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southeast
		else if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D)) {
			setDirection(3);
			translateX((float) (5 / Math.sqrt(2)));
			translateY((float) (-5 / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame / 15 + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S)) {
			setDirection(5);
			translateX((float) (-5 / Math.sqrt(2)));
			translateY((float) (-5 / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame / 15);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// northwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			setDirection(7);
			translateX((float) (-5 / Math.sqrt(2)));
			translateY((float) (5 / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame / 15);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// north
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			setDirection(0);
			translateY(5f);
			currentAtlasKey = String.format("%01d", currentFrame / 15 + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// east
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			setDirection(2);
			translateX(5f);
			currentAtlasKey = String.format("%01d", currentFrame / 15 + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// south
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			setDirection(4);
			translateY(-5f);
			currentAtlasKey = String.format("%01d", currentFrame / 15 + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// west
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			setDirection(6);
			translateX(-5f);
			currentAtlasKey = String.format("%01d", currentFrame / 15);
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
			translateX((float) (5 / Math.sqrt(2)));
			translateY((float) (5 / Math.sqrt(2)));
		}
		// southeast
		else if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D)) {
			translateX((float) (5 / Math.sqrt(2)));
			translateY((float) (-5 / Math.sqrt(2)));
		}
		// southwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S)) {
			translateX((float) (-5 / Math.sqrt(2)));
			translateY((float) (-5 / Math.sqrt(2)));
		}
		// northwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
			translateX((float) (-5 / Math.sqrt(2)));
			translateY((float) (5 / Math.sqrt(2)));
		}
		// north
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			translateY(5f);
		}
		// east
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			translateX(5f);
		}
		// south
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			translateY(-5f);
		}
		// west
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			translateX(-5f);
		}

		if (getDirection() == 0) {
			strafeUpSprite();
		} else if (getDirection() == 1 || getDirection() == 2
				|| getDirection() == 3) {
			strafeRightSprite();
		} else if (getDirection() == 4) {
			strafeDownSprite();
		} else if (getDirection() == 5 || getDirection() == 6
				|| getDirection() == 7) {
			strafeLeftSprite();
		}
	}

	public void strafeLeftSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / 15);
		setRegion(textureAtlas.findRegion(currentAtlasKey));
	}

	public void strafeRightSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / 15 + 4);
		setRegion(textureAtlas.findRegion(currentAtlasKey));

	}

	public void strafeUpSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / 15 + 2);
		setRegion(textureAtlas.findRegion(currentAtlasKey));

	}

	public void strafeDownSprite() {
		currentAtlasKey = String.format("%01d", currentFrame / 15 + 6);
		setRegion(textureAtlas.findRegion(currentAtlasKey));
	}

	@Override
	void die() {

	}

	@Override
	Projectile shoot(int dir) {
		return null;
	}

}
