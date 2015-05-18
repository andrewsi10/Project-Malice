package com.mygdx.game.player;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Character extends Sprite
{
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int NUMDIRECTIONS = 4;
    
	private int maxHp; // max health
	private int currentHp; // current health
	private int baseDmg; // base damage
	private int randMod; // random damage modifier
	private int direction;

	/**
	 * TODO: animations, sprites, coordinates
	 * */

	public void increaseMaxHp(int i)
	{
		maxHp += i;
	}

	public void increaseCurrentHp(int i)
	{
		if ( currentHp + i > maxHp )
		{
			currentHp = maxHp;
		} else
		{
			currentHp += i;
		}
	}

	public void increaseBdmg(int i)
	{
		baseDmg += i;
	}

	public void changeDirection(int dir)
	{
	    if ( dir >= 0 || dir < NUMDIRECTIONS )
	    {
	        direction = dir;
	    }
	}

	public void takeDamage(int bdmg, int rdm)
	{
		currentHp -= bdmg;
		currentHp -= (int) ( Math.random() * rdm );
		if ( currentHp <= 0 )
		{
			die();
		}
	}

	abstract void die();

	abstract void move();

	abstract void shoot(); // this is supposed to return a Projectile object but
							// that class doesn't exist yet

	/**
	 * TODO: methods for determining and changing coordinates, sprites, and so
	 * on
	 * **/

	public int getMaxHp()
	{
		return maxHp;
	}

	public int getCurrentHp()
	{
		return currentHp;
	}

	public int getBdmg()
	{
		return baseDmg;
	}

	public int getRandDmg()
	{
		return randMod;
	}

	public int direction()
	{
		return direction;
	}
}
