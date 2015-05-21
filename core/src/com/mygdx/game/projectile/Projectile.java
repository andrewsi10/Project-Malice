package com.mygdx.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Projectile extends Sprite {

	final private int DIRECTION;
	final private int speed = 10;
	final private int damage;
	Sound sound = Gdx.audio.newSound(Gdx.files
			.internal("audio/sound/fireball.wav"));

	private static final int col = 4;
	private static final int row = 1;

 	Animation animation;
	Texture projectileTexture;
	TextureRegion currentFrame;
	TextureRegion[] frames;
	float stateTime;

	public Projectile(int direction, int damage) {
		sound.play();

		projectileTexture = new Texture(
				Gdx.files.internal("img/sprites/Fireball/fireball.png"));
		TextureRegion[][] temp = TextureRegion.split(projectileTexture,
				projectileTexture.getWidth() / col,
				projectileTexture.getHeight() / row);
		frames = new TextureRegion[col * row];
		
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = temp[i][j];
			}
		}
		
		this.set(new Sprite(frames[0]));
		
		animation = new Animation(.2f, frames);
		stateTime = 0f;
		currentFrame = animation.getKeyFrame(0);

		DIRECTION = direction;
		this.damage = damage;
	}

	public void move() {
		if (!animation.isAnimationFinished(stateTime)) {
			stateTime += Gdx.graphics.getDeltaTime();
		}
		else {
			stateTime = 0;
		}
		
		this.setRegion(animation.getKeyFrame(stateTime));

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

	public int getDamage() {
		return damage;
	}

}
