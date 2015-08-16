package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.loaders.StatLoader;
import com.mygdx.game.sprites.SpriteData.Stats;

public class StatsSprite extends AnimatedSprite
{
    private SpriteData data = new SpriteData();
    
    public StatsSprite( double dir, Animation a )
    {
        super( dir, a );
    }
    
    /**
     * For Testing
     */
    public StatsSprite() {}
    public StatsSprite( boolean defaultStats ) {}
    
    // ----------------------- Setters ------------------------- //

    public void setSpriteData( StatLoader loader ) {
        data.setSpriteValues( loader );
    }
    
    /**
     * setter for experience
     * 
     * @param experience
     *            new value for experience
     */
    public void setExperience( int experience ) {
        data.setExperience( experience );
    }
    
    /**
     * This method keeps the Speed in stats and the moveSpeed in AnimatedSprite
     * in sync, if these speeds are to be different, then removing this method
     * or modifying how they sync should be necessary
     * 
     * @see com.mygdx.game.sprites.AnimatedSprite#setMoveSpeed(int)
     */
    public void setSpeed( int speed ) {
        data.setStat( Stats.SPEED, speed );
        setMoveSpeed( speed );
    }
    
    public void syncSpeeds() {
        this.setMoveSpeed( this.getSpriteData().getSpeed() );
    }

    // ----------------------- Incrementers ------------------------- //
    
    public void levelUp( boolean maintainHpRatio ) {
        data.levelUpSprite( maintainHpRatio );
    }

    // ----------------------- Getters ------------------------- //
    
    public SpriteData getSpriteData() {
        return data;
    }
}
