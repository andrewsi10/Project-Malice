package com.mygdx.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Projectile extends Sprite
{

	final int DIRECTION;

	public Projectile(int dir)
	{
		this.set( new Sprite( new Texture( Gdx.files
				.internal( "img/sprites/Fireball/0.png" ) ) ) );
		DIRECTION = dir;
	}

	// TODO later

}
