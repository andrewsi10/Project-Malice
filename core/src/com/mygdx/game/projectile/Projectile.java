package com.mygdx.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Projectile extends Sprite {

	final private int DIRECTION;
	final private int speed = 10;
	final private int baseDmg; // base damage
	final private int randMod; // random damage modifier
	Sound sound = Gdx.audio.newSound(Gdx.files.internal("audio/sound/fireball.wav"));

	public Projectile(int direction, int baseDamage, int randomModifier) {
		sound.play();
		this.set(new Sprite(new Texture(Gdx.files
				.internal("img/sprites/Fireball/0.png"))));
		DIRECTION = direction;
		baseDmg = baseDamage;
		randMod = randomModifier;
	}

	public void move() {
		// north
		if (DIRECTION == 0) {
			translateY(speed);
		}
		// northeast
		else if (DIRECTION == 1) {
			translateX((float) (speed / Math.sqrt(2)));
			translateY((float) (speed / Math.sqrt(2)));
		}
		// east
		else if (DIRECTION == 2) {
			translateX(speed);
		}
		// southeast
		else if (DIRECTION == 3) {
			translateX((float) (speed / Math.sqrt(2)));
			translateY((float) (-1 * speed / Math.sqrt(2)));
		}
		// south
		else if (DIRECTION == 4) {
			translateY(-1 * speed);
		}
		// southwest
		else if (DIRECTION == 5) {
			translateX((float) (-1 * speed / Math.sqrt(2)));
			translateY((float) (-1 * speed / Math.sqrt(2)));
		}
		// west
		else if (DIRECTION == 6) {
			translateX(-1 * speed);
		}
		// northwest
		else if (DIRECTION == 7) {
			translateX((float) (-1 * speed / Math.sqrt(2)));
			translateY((float) (speed / Math.sqrt(2)));
		}
	}
	
	public int getBdmg()
	{
		return baseDmg;
	}
	
	public int getRandDmg()
	{
		return randMod;
	}
	
}
