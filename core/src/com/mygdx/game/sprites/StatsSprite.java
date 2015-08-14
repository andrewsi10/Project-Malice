package com.mygdx.game.sprites;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;

public class StatsSprite extends AnimatedSprite
{
    public enum Stats {
        LEVEL, 
        EXPERIENCE, 
        EXPTOLEVEL, 
        HP, 
        MAXHP, 
        ATTACK, // base damage
        SPEED, 
        RELOADSPEED, 
        LUCK // random damage modifier
    }
    
    private EnumMap<Stats, Integer> stats = new EnumMap<Stats, Integer>( Stats.class );

    public StatsSprite( double dir, Animation a )
    {
        super( dir, a );
        defaultStats();
    }
    
    /**
     * For Testing
     */
    public StatsSprite() { 
        defaultStats();
    }
    
    /**
     * Sets default Stats: Attack - 20, Luck - 4, rest are 0
     */
    private void defaultStats() {
        fillStats();
        setStat( Stats.ATTACK, 20 );
        setStat( Stats.LUCK, 4 );
    }
    
    /**
     * Fills all the stats with 0
     */
    private void fillStats() {
        for ( Stats s : Stats.values() ) {
            stats.put( s, 0 );
        }
    }
    
    /**
     * Sets current Hp to maxHp
     */
    public void resetHp() {
        setStat( Stats.HP, getMaxHp() );
    }
    
    // ----------------------- Setters ------------------------- //

    /**
     * setter for experience
     * 
     * @param experience
     *            new value for experience
     */
    public void setExperience( int experience ) {
        setStat( Stats.EXPERIENCE, experience );
    }
    
    /**
     * This method keeps the Speed in stats and the moveSpeed in AnimatedSprite
     * in sync, if these speeds are to be different, then removing this method
     * or modifying how they sync should be necessary
     * 
     * @see com.mygdx.game.sprites.AnimatedSprite#setMoveSpeed(int)
     */
    public void setSpeed( int speed ) {
        setStat( Stats.SPEED, speed );
        setMoveSpeed( speed );
    }
    
    /**
     * Sets the Stat value of this Sprite
     * @param s Stats
     * @param value new value
     */
    public void setStat( Stats s, int value ) {
        stats.put( s, value );
    }

    // ----------------------- Incrementers ------------------------- //
    /**
     * Increases the Stat by the given value
     * @param s 
     * @param value
     */
    public void increaseStat( Stats s, int value ) {
        setStat( s, stats.get( s ) + value );
    }
    
    /**
     * Increments the given Stat by one
     * @param s
     */
    public void incrementStat( Stats s ) {
        increaseStat( s, 1 );
    }
    
    /**
     * Returns whether this sprite has full Hp
     * @return hp >= maxHp
     */
    public boolean atFullHp() {
        return getHp() >= getMaxHp();
    }

    // ----------------------- Getters ------------------------- //
    /**
     * Returns the requested stat's value
     * @param s
     * @return value of the Stat
     */
    public int getStat( Stats s ) {
        return stats.get( s );
    }
    // Personal getters for all the Stats
    /** Returns the level of this sprite */
    public int getLevel() {
        return stats.get( Stats.LEVEL );
    }

    /** Returns the experience of this sprite */
    public int getExperience() {
        return stats.get( Stats.EXPERIENCE );
    }

    /** Returns the exp needed for the next level of this sprite */
    public int getExpToLevel() {
        return stats.get( Stats.EXPTOLEVEL );
    }

    /** Returns the hp of this sprite */
    public int getHp() {
        return stats.get( Stats.HP );
    }

    /** Returns the maxHp of this sprite */
    public int getMaxHp() {
        return stats.get( Stats.MAXHP );
    }

    /** Returns the attack/strength of this sprite */
    public int getAttack() {
        return stats.get( Stats.ATTACK );
    }

    /** Returns the speed of this sprite */
    public int getSpeed() {
        return stats.get( Stats.SPEED );
    }

    /** Returns the time to reload for this sprite */
    public int getReloadSpeed() {
        return stats.get( Stats.RELOADSPEED );
    }

    /** Returns the luck of this sprite */
    public int getLuck() {
        return stats.get( Stats.LUCK );
    }
}
