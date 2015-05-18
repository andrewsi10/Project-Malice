package com.mygdx.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Projectile {
	private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	public Projectile() {
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(
				Gdx.files.internal("img/WarriorWalking.atlas"));
		animation = new Animation(1 / 5f, textureAtlas.getRegions());
	}
}
