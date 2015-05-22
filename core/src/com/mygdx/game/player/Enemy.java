package com.mygdx.game.player;

import java.util.ArrayList;
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
	private int animationSpeed = 15;
	private float moveSpeed = 3;
	private int aggroDistance = 200;
	private int travelTimeScalar = 100;
	private int marginOfDelta = 30;

	public Enemy() {
		super("img/sprites/WarriorWalking/WarriorWalking.atlas", "4");
		textureAtlas = getAtlas();
		setDirection((int) (Math.random() * 8));
		travelTime = (int) (4 + Math.random() * travelTimeScalar);
		setReloadSpeed(getReloadSpeed() * 2);
	}

	public void move(Player player, ArrayList<Projectile> projectiles) {
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
				setDirection((int) (Math.random() * 8));
				travelTime = (int) (4 + Math.random() * travelTimeScalar);
			}
			travelTime--;

			// northeast
			if (deltaX < -marginOfDelta && deltaY < -marginOfDelta) {
				setDirection(NORTHEAST);
				translateX((float) (moveSpeed / Math.sqrt(2)));
				translateY((float) (moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// southeast
			else if (deltaX < -marginOfDelta && deltaY > marginOfDelta) {
				setDirection(SOUTHEAST);
				translateX((float) (moveSpeed / Math.sqrt(2)));
				translateY((float) (-moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// southwest
			else if (deltaX > marginOfDelta && deltaY > marginOfDelta) {
				setDirection(SOUTHWEST);
				translateX((float) (-moveSpeed / Math.sqrt(2)));
				translateY((float) (-moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// northwest
			else if (deltaX > marginOfDelta && deltaY < -marginOfDelta) {
				setDirection(NORTHWEST);
				translateX((float) (-moveSpeed / Math.sqrt(2)));
				translateY((float) (moveSpeed / Math.sqrt(2)));
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// north
			else if (Math.abs( deltaX ) < marginOfDelta && deltaY < -marginOfDelta) {
				setDirection(NORTH);
				translateY(moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// east
			else if (deltaX < -marginOfDelta && Math.abs( deltaX ) < marginOfDelta) {
				setDirection(EAST);
				translateX(moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 6);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// south
			else if (Math.abs( deltaX ) < marginOfDelta && deltaY > marginOfDelta) {
				setDirection(SOUTH);
				translateY(-moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 4);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
			}
			// west
			else if (deltaX > marginOfDelta && Math.abs( deltaY ) < marginOfDelta) {
				setDirection(WEST);
				translateX(-moveSpeed);
				currentAtlasKey = String.format("%01d", currentFrame
						/ animationSpeed + 2);
				setRegion(textureAtlas.findRegion(currentAtlasKey));
				Projectile p = shoot();
				if (p != null)
				{
					p.setPosition(getX() + getWidth() / 2, getY()
							+ getHeight() / 3);
					p.setSize(p.getWidth() / 3, p.getHeight() / 3);
					
					projectiles.add( p );
				}
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
			setDirection((int) (Math.random() * 8));
			travelTime = (int) (4 + Math.random() * travelTimeScalar);
		}
		travelTime--;

		// northeast
		if (getDirection() == NORTHEAST) {
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southeast
		else if (getDirection() == SOUTHEAST) {
			translateX((float) (moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// southwest
		else if (getDirection() == SOUTHWEST) {
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (-moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// northwest
		else if (getDirection() == NORTHWEST) {
			translateX((float) (-moveSpeed / Math.sqrt(2)));
			translateY((float) (moveSpeed / Math.sqrt(2)));
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 2);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// north
		else if (getDirection() % 8 == NORTH) {
			setDirection(NORTH);
			translateY(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// east
		else if (getDirection() == EAST) {
			translateX(moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 6);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// south
		else if (getDirection() == SOUTH) {
			translateY(-moveSpeed);
			currentAtlasKey = String.format("%01d", currentFrame
					/ animationSpeed + 4);
			setRegion(textureAtlas.findRegion(currentAtlasKey));
		}
		// west
		else if (getDirection() == WEST) {
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

	public boolean inRange(Player player) {
		int distance = (int)Math.sqrt((player.getX() - this.getX())
				* (player.getX() - this.getX()) + (player.getY() - this.getY())
				* (player.getY() - this.getY()));
		return distance <= aggroDistance;
	}

}
