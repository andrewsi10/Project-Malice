package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.projectile.Projectile;

public class Player extends Character {

	private String currentAtlasKey = new String("0");
	private TextureAtlas textureAtlas;
	private int currentFrame;
	private int animationSpeed = 15;

	public Player(String file, String startFrame) {
		super(file, startFrame);
		textureAtlas = getAtlas();
		setSpeed( 5 );
	}

	public void move() {
		if (currentFrame < animationSpeed* 2 - 1) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		// northeast
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
            move( NORTHEAST, 2 );
		}
		// southeast
		else if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D)) {
            move( SOUTHEAST, 2 );
		}
		// southwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S)) {
            move( SOUTHWEST, 6 );
		}
		// northwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
            move( NORTHWEST, 6 );
		}
		// north
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            move( NORTH, 0 );
		}
		// east
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            move( EAST, 2 );
		}
		// south
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            move( SOUTH, 4 );
		}
		// west
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            move( WEST, 6 );
		}
	}

	public void strafe() {
		if (currentFrame < 29) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}

		// northeast
		if (Gdx.input.isKeyPressed(Input.Keys.D)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
            translate( 1, 1 );
		}
		// southeast
		else if (Gdx.input.isKeyPressed(Input.Keys.S)
				&& Gdx.input.isKeyPressed(Input.Keys.D)) {
            translate( 1, -1 );
		}
		// southwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.S)) {
            translate( -1, -1 );
		}
		// northwest
		else if (Gdx.input.isKeyPressed(Input.Keys.A)
				&& Gdx.input.isKeyPressed(Input.Keys.W)) {
            translate( -1, 1 );
		}
		// north
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			translate( 0, 1 );
		}
		// east
		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			translate( 1, 0 );
		}
		// south
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			translate( 0, -1 );
		}
		// west
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			translate( -1, 0 );
		}

		if (getDirection() == 0) {
            strafeSprite( 0 );
		} else if (getDirection() == 1 || getDirection() == 2
				|| getDirection() == 3) {
            strafeSprite( 2 );
		} else if (getDirection() == 4) {
            strafeSprite( 4 );
		} else if (getDirection() == 5 || getDirection() == 6
				|| getDirection() == 7) {
            strafeSprite( 6 );
		}
	}
    
    private void strafeSprite( int atlas )
    {
        setAtlas( atlas );
        setRegion(textureAtlas.findRegion(currentAtlasKey));
    }
    
    private void setAtlas( int change )
    {
        currentAtlasKey = String.format("%01d", currentFrame / animationSpeed
                + change);
    }
	

    private void move( int dir, int atlas )
    {
        move( dir );
        strafeSprite( atlas );
    }

}
