package com.mygdx.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Projectile extends Sprite {

	final private int DIRECTION;
	final private int baseDmg; // base damage
	final private int randMod; // random damage modifier

	public Projectile(int direction, int baseDamage, int randomModifier) {
		this.set(new Sprite(new Texture(Gdx.files
				.internal("img/sprites/Fireball/0.png"))));
		DIRECTION = direction;
		baseDmg = baseDamage;
		randMod = randomModifier;
	}

	public void move() {
		// north
		if (DIRECTION == 0) {
			translateY(5f);
		}
		// northeast
		else if (DIRECTION == 1) {
			translateX((float) (5 / Math.sqrt(2)));
			translateY((float) (5 / Math.sqrt(2)));
		}
		// east
		else if (DIRECTION == 2) {
			translateX(5f);
		}
		// southeast
		else if (DIRECTION == 3) {
			translateX((float) (5 / Math.sqrt(2)));
			translateY((float) (-5 / Math.sqrt(2)));
		}
		// south
		else if (DIRECTION == 4) {
			translateY(-5f);
		}
		// southwest
		else if (DIRECTION == 5) {
			translateX((float) (-5 / Math.sqrt(2)));
			translateY((float) (-5 / Math.sqrt(2)));
		}
		// west
		else if (DIRECTION == 6) {
			translateX(-5f);
		}
		// northwest
		else if (DIRECTION == 7) {
			translateX((float) (-5 / Math.sqrt(2)));
			translateY((float) (5 / Math.sqrt(2)));
		}
	}
}
