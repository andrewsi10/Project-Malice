package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;

public class Player extends Character {

	private String projectile;
	
	private int playerPoints = 0;
	
	public Player(String file, String proj) {
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
		setExpToLevel( 100 );
		setLevel( 1 );
		projectile = proj;
	}

    @Override
    public void move( Character character, 
                      ArrayList<Projectile> projectiles, 
                      long time )
    {
        int dir = getInputDirection();
        if (dir != -1) {
            setDirection( dir );
            super.move( character, projectiles, time );
        }
        
        if (Gdx.input.isButtonPressed( Input.Buttons.LEFT ))
        {
            shoot(projectiles, Gdx.input.getX() - Gdx.graphics.getWidth() / 2, 
                Gdx.graphics.getHeight() / 2 - Gdx.input.getY(), 
                System.currentTimeMillis(), projectile );
        }
    }
    
    public int getPoints()
    {
    	return playerPoints;
    }
    
    public void increasePoints()
    {
    	playerPoints += 10;
    }
    
    public void increaseExp( int exp )
    {
        this.setExperience( getExperience() + exp );
        if ( getExperience() >= getExpToLevel() )
        {
            setExperience( getExperience() - getExpToLevel() );
            increaseCurrentLevel();
            setExpToLevel(getExpToLevel() * getCurrentLevel() / 2);
        }
    }

	/**
	 * Returns direction to go based on key input
	 * @return direction or -1 if no direction
	 */
	private int getInputDirection() {
        int dirY = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            dirY = NORTH;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            dirY = ( dirY == NORTH ) ? -1 : SOUTH;
        
	    int dirX = -1;
	    if (Gdx.input.isKeyPressed(Input.Keys.D))
	        dirX = EAST;
	    if (Gdx.input.isKeyPressed(Input.Keys.A))
	        dirX = ( dirX == EAST ) ? -1 : WEST;
	    
	    if ( dirY == -1 )
	        return dirX;
	    if ( dirX == -1 )
	        return dirY;
	    if ( dirY == NORTH && dirX == WEST )
	        return NORTHWEST;
		return ( dirY + dirX ) / 2;
	}

}
