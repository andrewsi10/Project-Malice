package com.mygdx.game.player;

import java.util.ArrayList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;

public class Enemy extends Character {

	private TextureAtlas textureAtlas;
	private int travelTime;
	private float moveSpeed = 3;
	private int aggroDistance = 200;
	private int travelTimeScalar = 100;
	private int marginOfDelta = 30;
	private int minTravelTime = 4;
	Animation animation;
	Array<TextureAtlas.AtlasRegion> frames;
	float stateTime;

	public Enemy(String file, String startFrame) {
		super(file, startFrame);
		textureAtlas = getAtlas();
		
		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);
		
		setReloadSpeed(getReloadSpeed() * 2);
		
		frames = textureAtlas.getRegions();
		animation = new Animation(.2f, frames);
		stateTime = 0f;
	}

	public void move(Player player, ArrayList<Projectile> projectiles) {
		if (!inRange(player)) {
			move();
		} 
		// moves towards the player
		else {		
			float deltaX = getX() - player.getX();
			float deltaY = getY() - player.getY();
			
			if (!animation.isAnimationFinished(stateTime)) {
				stateTime += Gdx.graphics.getDeltaTime();
			}
			else {
				stateTime = 0;
			}
			
			this.setRegion(animation.getKeyFrame(stateTime));

			if (travelTime < 1) {
				setDirection((int) (Math.random() * 8));
				travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);
			}
			travelTime--;

			// northeast
			if (deltaX < -marginOfDelta && deltaY < -marginOfDelta) {
				strafeEast();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// southeast
			else if (deltaX < -marginOfDelta && deltaY > marginOfDelta) {
				strafeEast();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// southwest
			else if (deltaX > marginOfDelta && deltaY > marginOfDelta) {
				strafeWest();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// northwest
			else if (deltaX > marginOfDelta && deltaY < -marginOfDelta) {
				strafeWest();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// north
			else if (Math.abs( deltaX ) < marginOfDelta && deltaY < -marginOfDelta) {
				moveNorth();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// east
			else if (deltaX < -marginOfDelta && Math.abs( deltaX ) < marginOfDelta) {
				moveEast();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// south
			else if (Math.abs( deltaX ) < marginOfDelta && deltaY > marginOfDelta) {
				moveSouth();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			// west
			else if (deltaX > marginOfDelta && Math.abs( deltaY ) < marginOfDelta) {
				moveWest();
				Projectile p = shoot();
				if (p != null)
				{
					setProjectile(p, projectiles);
				}
			}
			else {
				move();
			}
		}
	}

	@Override
	public void move() {
		
		if (!animation.isAnimationFinished(stateTime)) {
			stateTime += Gdx.graphics.getDeltaTime();
		}
		else {
			stateTime = 0;
		}
		
		this.setRegion(animation.getKeyFrame(stateTime));

		if (travelTime < 1) {
			setDirection((int) (Math.random() * 8));
			travelTime = (int) (4 + Math.random() * travelTimeScalar);
		}
		travelTime--;

		// northeast
		if (getDirection() == NORTHEAST) {
			strafeEast();
		}
		// southeast
		else if (getDirection() == SOUTHEAST) {
			strafeEast();
		}
		// southwest
		else if (getDirection() == SOUTHWEST) {
			strafeWest();
		}
		// northwest
		else if (getDirection() == NORTHWEST) {
			strafeWest();
		}
		// north
		else if (getDirection() % 8 == NORTH) {
			moveNorth();
		}
		// east
		else if (getDirection() == EAST) {
			moveEast();
		}
		// south
		else if (getDirection() == SOUTH) {
			moveSouth();
		}
		// west
		else if (getDirection() == WEST) {
			moveWest();
		}
	}

	public void moveNorth() {
		setDirection(NORTH);
		translateY(moveSpeed);
	}
	
	public void moveEast() {
		translateX(moveSpeed);
	}
	
	public void moveSouth() {
		translateY(-moveSpeed);
	}
	
	public void moveWest() {
		translateX(-moveSpeed);;
	}
	
	public void strafeEast() {
		translateX((float) (moveSpeed / Math.sqrt(2)));
		translateY((float) (-moveSpeed / Math.sqrt(2)));
	}
	
	public void strafeWest() {
		translateX((float) (-moveSpeed / Math.sqrt(2)));
		translateY((float) (moveSpeed / Math.sqrt(2)));
	}
	
	public void setProjectile(Projectile p, ArrayList<Projectile> projectiles) {
		p.setPosition(getX() + getWidth() / 2, getY()
				+ getHeight() / 3);
		p.setSize(p.getWidth() / 3, p.getHeight() / 3);
		
		projectiles.add( p );
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
