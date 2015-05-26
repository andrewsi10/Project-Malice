package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Enemy extends Character {

	private int travelTime;
	private int aggroDistance = 400;
	private int travelTimeScalar = 100;
	private int marginOfDelta = 30;
	private int minTravelTime = 4;
	private String projectile;

	public Enemy(String file) {
		super(new TextureAtlas(Gdx.files.internal(file)).getRegions());

		setDirection((int) (Math.random() * 8));
		travelTime = (int) (minTravelTime + Math.random() * travelTimeScalar);
		setExperience( 20 ); // set amount of exp Player will receive
		setHpColor( Color.RED );
		setSpeed(3);  // set speed of Enemy
		setReloadSpeed(getReloadSpeed() * 2); // set reload speed
		projectile = "EnemyBullet";
	}

	@Override
	public void move( Character character, 
	                  ArrayList<Projectile> projectiles, 
	                  long time) 
	{
		if (!inRange(character)) {
            setRandomDirection();
	        super.move( character, projectiles, time ); // note % NUMDIRECTION if errors
		}
		// moves towards the player
		else {
		    setRandomDirection();

			float deltaX = character.getX() - getX();
			float deltaY = character.getY() - getY();
			int newDir = this.getDirection(-deltaX, -deltaY);
			if (newDir != -1) {
			    setDirection( newDir );
				shoot(projectiles, deltaX, deltaY, time, projectile);
			}
            super.move( character, projectiles, time );
		}
	}
	
	private void setRandomDirection()
	{
        if (travelTime < 1) {
            setDirection((int) (Math.random() * 8));
            travelTime = (int) (minTravelTime + Math.random()
                    * travelTimeScalar);
        }
        travelTime--;
	}

	private int getDirection(float deltaX, float deltaY) {
		if (deltaX < -marginOfDelta && deltaY < -marginOfDelta)
			return NORTHEAST;
		if (deltaX < -marginOfDelta && deltaY > marginOfDelta)
			return SOUTHEAST;
		if (deltaX > marginOfDelta && deltaY > marginOfDelta)
			return SOUTHWEST;
		if (deltaX > marginOfDelta && deltaY < -marginOfDelta)
			return NORTHWEST;
		if (Math.abs(deltaX) < marginOfDelta && deltaY < -marginOfDelta)
			return NORTH;
		if (deltaX < -marginOfDelta && Math.abs(deltaX) < marginOfDelta)
			return EAST;
		if (Math.abs(deltaX) < marginOfDelta && deltaY > marginOfDelta)
			return SOUTH;
		if (deltaX > marginOfDelta && Math.abs(deltaY) < marginOfDelta)
			return WEST;
		return -1;
	}

	public boolean inRange(Character character) {
	    float dx = character.getX() - this.getX();
	    float dy = character.getY() - this.getY();
		int distance = (int)Math.sqrt( dx * dx + dy * dy );
		return distance <= aggroDistance;
	}

}
