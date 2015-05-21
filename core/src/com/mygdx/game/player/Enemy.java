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
	private int travelTimeScalar = 100;

	public Enemy() {
		super("img/sprites/WarriorWalking/WarriorWalking.atlas", "4");
		textureAtlas = getAtlas();
		direction = (int) (Math.random() * 8);
		travelTime = (int) (4 + Math.random() * travelTimeScalar);
	}

	public void move(Player player) {
		if (!inRange(player)) {
			move();
		} 
		// moves towards the player
		else {		
			float deltaX = getX() - player.getX();
			float deltaY = getY() - player.getY();
			// lengthens the animation cycle
			if (currentFrame < animationSpeed * 2 - 1) {
				currentFrame++;
			} else {
				currentFrame = 0;
			}

			if (travelTime < 1) {
				direction = (int) (Math.random() * 8);
				travelTime = (int) (4 + Math.random() * travelTimeScalar);
			}
			travelTime--;

			// northeast
			if (deltaX < 0 && deltaY < 0) {
				setDirection(1);
				translateX((float) (moveSpeed / Math.sqrt(2)));
				translateY((float) (moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// southeast
			else if (deltaX < 0 && deltaY > 0) {
				setDirection(3);
				translateX((float) (moveSpeed / Math.sqrt(2)));
				translateY((float) (-moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// southwest
			else if (deltaX > 0 && deltaY > 0) {
				setDirection(5);
				translateX((float) (-moveSpeed / Math.sqrt(2)));
				translateY((float) (-moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// northwest
			else if (deltaX > 0 && deltaY < 0) {
				setDirection(7);
				translateX((float) (-moveSpeed / Math.sqrt(2)));
				translateY((float) (moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// north
			else if (deltaX == 0 && deltaY < 0) {
				setDirection(0);
				translateY(moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// east
			else if (deltaX < 0 && deltaY == 0) {
				setDirection(2);
				translateX(moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// south
			else if (deltaX == 0 && deltaY > 0) {
				setDirection(4);
				translateY(-moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 4);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// west
			else if (deltaX > 0 && deltaY == 0) {
				setDirection(6);
				translateX(-moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			else {
				move();
			}
		}
	}

	@Override
	public void move() {
		// lengthens the animation cycle
		if (currentFrame < animationSpeed * 2 - 1) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		if (travelTime < 1) {
			direction = (int) (Math.random() * 8);
			travelTime = (int) (4 + Math.random() * travelTimeScalar);
		}
		travelTime--;

		// northeast
		if (direction == 1) {
			setDirection(1);
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southeast
		else if (direction == 3) {
			setDirection(3);
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southwest
		else if (direction == 5) {
			setDirection(5);
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// northwest
		else if (direction == 7) {
			setDirection(7);
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// north
		else if (direction == 0 || direction == 8) {
			setDirection(0);
			translateY(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// east
		else if (direction == 2) {
			setDirection(2);
			translateX(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// south
		else if (direction == 4) {
			setDirection(4);
			translateY(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// west
		else if (direction == 6) {
			setDirection(6);
			translateX(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
	}

	@Override
	public void strafe() {
		// TODO Auto-generated method stub

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public Projectile shoot() {
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
