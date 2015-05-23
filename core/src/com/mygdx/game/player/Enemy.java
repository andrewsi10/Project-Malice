package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;

public class Enemy extends Character {

	private int travelTime;
	private int aggroDistance = 200;
	private int travelTimeScalar = 100;
	private int marginOfDelta = 30;
	private int minTravelTime = 4;
	Animation animation;
	Array<TextureAtlas.AtlasRegion> frames;
	float stateTime;

	public Enemy(String file, String startFrame) {
		super(file, startFrame);

		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);

		setReloadSpeed(getReloadSpeed() * 2);

		frames = getAtlas().getRegions();
		animation = new Animation(.2f, frames);
		stateTime = 0f;
		setSpeed( 3 );
	}

	public void move(Player player, ArrayList<Projectile> projectiles, long time) {
		if (!inRange(player)) {
			move();
		}
		// moves towards the player
		else {
            float deltaX = player.getX() - getX();
            float deltaY = player.getY() - getY();

			if (!animation.isAnimationFinished(stateTime)) {
				stateTime += Gdx.graphics.getDeltaTime();
			} else {
				stateTime = 0;
			}

			this.setRegion(animation.getKeyFrame(stateTime));

			if (travelTime < 1) {
				setDirection((int) (Math.random() * 8));
				travelTime = (int) (minTravelTime + Math.random()
						* travelTimeScalar);
			}
			travelTime--;

			// northeast
			if (deltaX < -marginOfDelta && deltaY < -marginOfDelta) {
                move( NORTHEAST ); // strafe east
                shoot( projectiles, deltaX, deltaY, time);
			}
			// southeast
			else if (deltaX < -marginOfDelta && deltaY > marginOfDelta) {
                move( SOUTHEAST ); // strafe east
                shoot( projectiles, deltaX, deltaY, time);
			}
			// southwest
			else if (deltaX > marginOfDelta && deltaY > marginOfDelta) {
                move( SOUTHWEST ); // strafe west
                shoot( projectiles, deltaX, deltaY, time);
			}
			// northwest
			else if (deltaX > marginOfDelta && deltaY < -marginOfDelta) {
                move( NORTHWEST ); // strafe west
                shoot( projectiles, deltaX, deltaY, time);
			}
			// north
			else if (Math.abs(deltaX) < marginOfDelta
					&& deltaY < -marginOfDelta) {
                move( NORTH );
                shoot( projectiles, deltaX, deltaY, time);
			}
			// east
			else if (deltaX < -marginOfDelta
					&& Math.abs(deltaX) < marginOfDelta) {
                move( EAST );
                shoot( projectiles, deltaX, deltaY, time);
			}
			// south
			else if (Math.abs(deltaX) < marginOfDelta && deltaY > marginOfDelta) {
                move( SOUTH );
                shoot( projectiles, deltaX, deltaY, time);
			}
			// west
			else if (deltaX > marginOfDelta && Math.abs(deltaY) < marginOfDelta) {
                move( WEST );
                shoot( projectiles, deltaX, deltaY, time);
			} else {
				move();
			}
		}
	}

	@Override
	public void move() {

		if (!animation.isAnimationFinished(stateTime)) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0;
		}

		this.setRegion(animation.getKeyFrame(stateTime));

		if (travelTime < 1) {
			setDirection((int) (Math.random() * 8));
			travelTime = (int) (4 + Math.random() * travelTimeScalar);
		}
		travelTime--;

		move( getDirection() ); // note % NUMDIRECTION if errors
	}
	

	@Override
	public void strafe() {
		// TODO Auto-generated method stub

	}

	public boolean inRange(Player player) {
		int distance = (int) Math.sqrt((player.getX() - this.getX())
				* (player.getX() - this.getX()) + (player.getY() - this.getY())
				* (player.getY() - this.getY()));
		return distance <= aggroDistance;
	}

}
