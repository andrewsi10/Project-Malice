package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Character extends Sprite {
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
	private int srcX;
	private int srcY;

	private static final int col = 4;
	private static final int row = 2;
	Animation animation;
	Texture playerTexture;
	TextureRegion[] frames;
	TextureRegion startFrame;
	float stateTime;

	/**
	 * TODO: animations, sprites, coordinates
	 * */
	public Character() {
		playerTexture = new Texture(
				Gdx.files.internal("img/sprites/WhiteMonk/WhiteMonk.png"));
		TextureRegion[][] tmp = TextureRegion
				.split(playerTexture, playerTexture.getWidth() / col,
						playerTexture.getHeight() / row);
		frames = new TextureRegion[col * row];

		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		
		animation = new Animation(1f, frames);
		stateTime = 0f;
	}
	
	public void update() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
        	
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
        	
        }
	}

	public void increaseMaxHp(int i) {
		maxHp += i;
	}

	public void increaseCurrentHp(int i) {
		if (currentHp + i > maxHp) {
			currentHp = maxHp;
		} else {
			currentHp += i;
		}
	}

	public void increaseBdmg(int i) {
		baseDmg += i;
	}

	public void changeDirection(int dir) {
		if (dir >= 0 || dir < NUMDIRECTIONS) {
			direction = dir;
		}
	}

	public void takeDamage(int bdmg, int rdm) {
		currentHp -= bdmg;
		currentHp -= (int) (Math.random() * rdm);
		if (currentHp <= 0) {
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

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getBdmg() {
		return baseDmg;
	}

	public int getRandDmg() {
		return randMod;
	}

	public int direction() {
		return direction;
	}
}
