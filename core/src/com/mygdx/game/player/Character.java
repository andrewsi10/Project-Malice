package com.mygdx.game.player;

public abstract class Character
{
	private int maxHp; // max health
	private int currentHp; // current health
	private int baseDmg; // base damage
	private int randMod; // random damage modifier
	private char direction;

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

	public void changeDirection(char dir)
	{
		if ( dir == 'w' )
		{
			direction = 'w';
		}
		if ( dir == 'n' )
		{
			direction = 'n';
		}
		if ( dir == 'e' )
		{
			direction = 'e';
		}
		if ( dir == 's' )
		{
			direction = 's';
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

	public char direction()
	{
		return direction;
	}
}
