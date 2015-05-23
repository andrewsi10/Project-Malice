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

		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);

		setReloadSpeed(getReloadSpeed() * 2);

		frames = getAtlas().getRegions();
		animation = new Animation(.2f, frames);
		stateTime = 0f;
        moveSpeed = 3;
	}

	public void move(Player player, ArrayList<Projectile> projectiles, long time) {
		if (!inRange(player)) {
			move();
		}
		// moves towards the player
		else {
			float deltaX = getX() - player.getX();
			float deltaY = getY() - player.getY();

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
                shoot(deltaX, deltaY, time, projectiles);
			}
			// southeast
			else if (deltaX < -marginOfDelta && deltaY > marginOfDelta) {
                move( SOUTHEAST ); // strafe east
                shoot(deltaX, deltaY, time, projectiles);
			}
			// southwest
			else if (deltaX > marginOfDelta && deltaY > marginOfDelta) {
                move( SOUTHWEST ); // strafe west
                shoot(deltaX, deltaY, time, projectiles);
			}
			// northwest
			else if (deltaX > marginOfDelta && deltaY < -marginOfDelta) {
                move( NORTHWEST ); // strafe west
                shoot(deltaX, deltaY, time, projectiles);
			}
			// north
			else if (Math.abs(deltaX) < marginOfDelta
					&& deltaY < -marginOfDelta) {
                move( NORTH );
                shoot(deltaX, deltaY, time, projectiles);
			}
			// east
			else if (deltaX < -marginOfDelta
					&& Math.abs(deltaX) < marginOfDelta) {
                move( EAST );
                shoot(deltaX, deltaY, time, projectiles);
			}
			// south
			else if (Math.abs(deltaX) < marginOfDelta && deltaY > marginOfDelta) {
                move( SOUTH );
                shoot(deltaX, deltaY, time, projectiles);
			}
			// west
			else if (deltaX > marginOfDelta && Math.abs(deltaY) < marginOfDelta) {
                move( WEST );
                shoot(deltaX, deltaY, time, projectiles);
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

		// northeast
		if (getDirection() == NORTHEAST) {
            move( NORTHEAST ); // strafe east
		}
		// southeast
		else if (getDirection() == SOUTHEAST) {
            move( SOUTHEAST );
		}
		// southwest
		else if (getDirection() == SOUTHWEST) {
            move( SOUTHWEST );
		}
		// northwest
		else if (getDirection() == NORTHWEST) {
            move( NORTHWEST );
		}
		// north
		else if (getDirection() % 8 == NORTH) {
            move( NORTH );
		}
		// east
		else if (getDirection() == EAST) {
            move( EAST );
		}
		// south
		else if (getDirection() == SOUTH) {
            move( SOUTH );
		}
		// west
		else if (getDirection() == WEST) {
            move( WEST );
		}
	}

	public void setProjectile(Projectile p, ArrayList<Projectile> projectiles) {
		p.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 3);
		p.setSize(p.getWidth() / 3, p.getHeight() / 3);

		projectiles.add(p);
	}
	

    public void shoot(float xDistance, float yDistance, long time, ArrayList<Projectile> projectiles )
    {
        Projectile p = shoot( xDistance, yDistance, time );

        if (p != null)
        {
            p.setPosition(getX() + getWidth() / 2, getY()
                    + getHeight() / 3);
            p.setSize(p.getWidth() / 3, p.getHeight() / 3);
            
            projectiles.add( p );
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
		int distance = (int) Math.sqrt((player.getX() - this.getX())
				* (player.getX() - this.getX()) + (player.getY() - this.getY())
				* (player.getY() - this.getY()));
		return distance <= aggroDistance;
	}

}
