package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;

public class Player extends Character {

	public Player(String file) {
		super(new Array<AtlasRegion>(new AtlasRegion[] { // up animation new Array
				new TextureAtlas(Gdx.files.internal(file)).findRegion("0"),
				new TextureAtlas(Gdx.files.internal(file)).findRegion("1") }),
				new Array<AtlasRegion>( // right animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("2"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("3") }),
				new Array<AtlasRegion>( // down animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("4"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("5") }),
				new Array<AtlasRegion>( // left animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("6"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("7") }));
		setSpeed(5);
	}

    @Override
    public void move( Character character, 
                      ArrayList<Projectile> projectiles, 
                      long time )
    {
        int dir = getInputDirection();
        if (dir != -1) {
            move(dir);
        }
        
        if (Gdx.input.isButtonPressed( Input.Buttons.LEFT ))
        {
            this.shoot(projectiles, 
                Gdx.input.getX() - Gdx.graphics.getWidth() / 2, 
                Gdx.graphics.getHeight() / 2 - Gdx.input.getY(), 
                System.currentTimeMillis(), "fireball" );
        }
    }

	private int getInputDirection() {
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W))
			return NORTHEAST;
		if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D))
			return SOUTHEAST;
		if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S))
			return SOUTHWEST;
		if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W))
			return NORTHWEST;
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			return NORTH;
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			return EAST;
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			return SOUTH;
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			return WEST;
		return -1;
	}

	@Override
	public String getType()
	{
		return "player";
	}

}
