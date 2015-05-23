package com.mygdx.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.player.Character;

public class Projectile extends Sprite
{
    private Character myCharacter;

	final private float xDistance;
	final private float yDistance;
	final private double angle;
	final private int speed = 10;
	final private int damage;
	private String projectileType;
	private Sound sound;

	private static final int col = 4;
	private static final int row = 1;

	Animation animation;
	Texture projectileTexture;
	TextureRegion currentFrame;
	TextureRegion[] frames;
	float stateTime;

	public Projectile(Character c, int direction, int damage, String type, 
	    float distanceX, float distanceY)
	{
        this.myCharacter = c;

		xDistance = distanceX;
		yDistance = distanceY;
		angle = Math.atan2( yDistance, xDistance );

		projectileType = type;
		sound = Gdx.audio.newSound( Gdx.files.internal( "audio/sound/"
				+ projectileType + ".wav" ) );
		sound.play();

		projectileTexture = new Texture(
				Gdx.files.internal( "img/sprites/Projectiles/"
						+ projectileType.charAt( 0 )
						+ projectileType.substring( 1 ) + "/" + projectileType
						+ ".png" ) );
		TextureRegion[][] temp = TextureRegion.split( projectileTexture,
				projectileTexture.getWidth() / col,
				projectileTexture.getHeight() / row );
		frames = new TextureRegion[col * row];

		int index = 0;
		for ( int i = 0; i < row; i++ )
		{
			for ( int j = 0; j < col; j++ )
			{
				frames[index++] = temp[i][j];
			}
		}

		this.set( new Sprite( frames[0] ) );

		animation = new Animation( .2f, frames );
		stateTime = 0f;
		currentFrame = animation.getKeyFrame( 0 );

		this.damage = damage;
	}

	public void move()
	{
		if ( !animation.isAnimationFinished( stateTime ) )
		{
			stateTime += Gdx.graphics.getDeltaTime();
		} else
		{
			stateTime = 0;
		}

		this.setRegion( animation.getKeyFrame( stateTime ) );

		translateY( (float) ( speed * Math.sin( angle ) ) );
		translateX( (float) ( speed * Math.cos( angle ) ) );

	}
    
    public boolean hitCharacter( Character c )
    {
        if ( c == this.myCharacter ) return false;
        boolean overlaps = c.getBoundingRectangle().overlaps( 
                            this.getBoundingRectangle() );
        if ( overlaps )
            c.takeDamage( this.damage );
        return overlaps;
    }

	public int getDamage()
	{
		return damage;
	}

}