package com.mygdx.game.player;

import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Enemy extends Character {

	private String currentAtlasKey = new String("0");
	private TextureAtlas textureAtlas;
	private int currentFrame;
	private int travelTime;
	private int direction;
	private int animationSpeed = 15;
	private float moveSpeed = 3;
	private int aggroDistance = 50;

	public Enemy() {
		super("img/sprites/WarriorWalking/WarriorWalking.atlas", "1");
		textureAtlas = getAtlas();
		direction = (int) (Math.random() * 8);
	}

	void move(Player player) {
		if (!inRange(player)) {
			move();
		} else {
			move(); //temporary
		}
	}

	@Override
	void move() {
		// if (!inRange(player)) {

		// lengthens the animation cycle
		if (currentFrame < animationSpeed * 3 - 1) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		if (travelTime < 1) {
			direction = (int) (Math.random() * 8);
			travelTime = (int) (4 + Math.random() * 10);
		}
		travelTime--;

		// northeast
		if (direction == 1) {
			setDirection(1);
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southeast
		else if (direction == 3) {
			setDirection(3);
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southwest
		else if (direction == moveSpeed) {
			setDirection(5);
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// northwest
		else if (direction == 7) {
			setDirection(7);
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// north
		else if (direction == 0 || direction == 8) {
			setDirection(0);
			translateY(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// east
		else if (direction == 2) {
			setDirection(2);
			translateX(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// south
		else if (direction == 4) {
			setDirection(4);
			translateY(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// west
		else if (direction == 6) {
			setDirection(6);
			translateX(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// }
		// else {
		//
		// }
	}

	@Override
	void strafe() {
		// TODO Auto-generated method stub

	}

	@Override
	void die() {
		// TODO Auto-generated method stub

	}

	@Override
	Projectile shoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean inRange(Player player) {
		int distance = (int)Math.sqrt((player.getX() - this.getX())
				* (player.getX() - this.getX()) + (player.getY() - this.getY())
				* (player.getY() - this.getY()));
		return distance <= aggroDistance;
	}

}
