package com.mygdx.game.player;

import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Enemy extends Character{

	private String currentAtlasKey = new String("0");
	private TextureAtlas textureAtlas;
	private int currentFrame;
	private int travelTime;
	private int direction;
	
	public Enemy() {
		super("img/sprites/WarriorWalking/WarriorWalking.atlas", "1");
		textureAtlas = getAtlas();
		direction = (int)(Math.random() * 8);
	}
	
	@Override
	void move() {
		if (!inRange()) {
			
			//lengthens the animation cycle
			if (currentFrame < 44) {
				currentFrame++;
			} else {
				currentFrame = 0;
			}
			
			if (travelTime < 1) {
				direction = (int)(Math.random() * 8);
				travelTime = (int)(4 + Math.random() * 10);
			}			
			travelTime--;
			
			
			// northeast
			if (direction == 1) {
				setDirection(1);
				translateX((float) (5 / Math.sqrt(2)));
				translateY((float) (5 / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame / 15);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// southeast
			else if (direction == 3) {
				setDirection(3);
				translateX((float) (5 / Math.sqrt(2)));
				translateY((float) (-5 / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame / 15);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// southwest
			else if (direction == 5) {
				setDirection(5);
				translateX((float) (-5 / Math.sqrt(2)));
				translateY((float) (-5 / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame / 15);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// northwest
			else if (direction == 7) {
				setDirection(7);
				translateX((float) (-5 / Math.sqrt(2)));
				translateY((float) (5 / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame / 15);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// north
			else if (direction == 0 || direction == 8) {
				setDirection(0);
				translateY(5f);
				currentAtlasKey = String.format("%01d", currentFrame / 15 + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// east
			else if (direction == 2) {
				setDirection(2);
				translateX(5f);
				currentAtlasKey = String.format("%01d", currentFrame / 15 + 4);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// south
			else if (direction == 4) {
				setDirection(4);
				translateY(-5f);
				currentAtlasKey = String.format("%01d", currentFrame / 15 + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
			// west
			else if (direction == 6) {
				setDirection(6);
				translateX(-5f);
				currentAtlasKey = String.format("%01d", currentFrame / 15);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
			}
		}
		else {
			
		}
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
	Projectile shoot(int dir) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean inRange() {
		return false;
	}

}
