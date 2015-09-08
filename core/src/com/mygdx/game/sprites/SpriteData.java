package com.mygdx.game.sprites;

import java.util.EnumMap;

import com.mygdx.game.loaders.StatLoader;

public class SpriteData
{
    public enum Stats {
        LEVEL(true), 
        EXPERIENCE(true), 
        EXPTOLEVEL(true), 
        HP(true), 
        MAXHP(false), 
        ATTACK(false), // base damage
        SPEED(false), 
        RELOADSPEED(false), 
        LUCK(false); // random damage modifier
        public static final Stats[] VALUES = Stats.values();
        public final boolean isInGame;
        Stats( boolean inGame ) {
            this.isInGame = inGame;
        }
        
    }
    
    private String name;
    private EnumMap<Stats, Integer> myStats = new EnumMap<Stats, Integer>( Stats.class );
    private EnumMap<Stats, Integer> startingStats = new EnumMap<Stats, Integer>(Stats.class);
    private EnumMap<Stats, Integer> levelingStats = new EnumMap<Stats, Integer>(Stats.class);
    
    public SpriteData() { fillStats(); }
    public SpriteData( SpriteData data ) {
        copy( data );
    }
    
    /**
     * Fills all the stats with 0
     */
    private void fillStats() {
        name = "";
        for ( Stats s : Stats.values() ) {
            myStats.put( s, 0 );
            startingStats.put( s, 0 );
            levelingStats.put( s, 0 );
        }
    }
    
    public void copy( SpriteData data ) {
        name = data.name;
        for ( Stats s : Stats.values() ) {
            myStats.put( s, data.myStats.get( s ) );
            startingStats.put( s, data.startingStats.get( s ) );
            levelingStats.put( s, data.levelingStats.get( s ) );
        }
    }
    // ----------------------- Checkers ------------------------- //
    
    public boolean canLevel() {
        return getExperience() >= getExpToLevel();
    }
    
    public boolean hpIsInRange() {
        return getHp() > 0 && getHp() <= getMaxHp();
    }
    
    /**
     * Returns whether this sprite has full Hp
     * @return hp >= maxHp
     */
    public boolean atFullHp() {
        return getHp() >= getMaxHp();
    }
    
    public boolean expIsInRange() {
        return getExperience() > 0 && !canLevel();
    }
    
    // ----------------------- Setters ------------------------- //
    
    public void setName( String newName ) {
        name = newName;
    }
    
    public void setSpriteValues( StatLoader loader ) {
        copy( loader.getData() );
    }
    
    public void resetSprite() {
        setSprite( 1 );
        resetHp();
    }
    
    public void setSprite( int newLevel ) {
        for ( Stats s : Stats.values() ) {
            Integer start = startingStats.get( s );
            Integer level = levelingStats.get( s );
            setStat( s, start + (newLevel-1)*level );
        }
    }
    
    public void levelUpSprite( boolean maintainHpRatio) {
        float ratio = getHpRatio();
        int exp = getExperience() - getExpToLevel();
        setSprite( getLevel() + 1 ); // note: sets exp and hp to 0 as well
        if ( maintainHpRatio )
        {
            increaseHp( (int)( getMaxHp() * ratio - getHp() ) ); // TODO test
        }
        else
        {
            resetHp();
        }
        setExperience( exp );
    }
    
    /**
     * Sets current Hp to maxHp
     */
    public void resetHp() {
        setStat( Stats.HP, getMaxHp() );
    }

    
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
     * Sets the Stat value of this Sprite
     * @param s Stats
     * @param value new value
     */
    public void setStat( Stats s, int value ) {
        myStats.put( s, value );
    }

    // ----------------------- Incrementers ------------------------- //
    /**
     * Increases the Stat by the given value
     * @param s 
     * @param value
     */
    public void increaseStat( Stats s, int value ) {
        setStat( s, myStats.get( s ) + value );
    }
    
    /**
     * Increments the given Stat by one
     * @param s
     */
    public void incrementStat( Stats s ) {
        increaseStat( s, 1 );
    }
    
    public void increaseHp( int i ) {
        increaseStat( Stats.HP, i );
        if ( getHpRatio() > 1 )
            resetHp();
    }
    
    public void increaseExp( int i ) {
        increaseStat( Stats.EXPERIENCE, i );
    }

    // ----------------------- Getters ------------------------- //

    /**
     * damage dealt by Character
     * 
     * @return baseDmg with an added random value scaled by randMod
     */
    public int getDamageDealt() {
        return getAttack() + (int)( getLuck() * Math.random() );
    }
    
    /**
     * Returns Hp Ratio
     * @return hp / maxHp
     */
    public float getHpRatio() {
        return (float)getHp() / getMaxHp();
    }
    
    /**
     * Returns a ratio between exp and expToLevel
     * @return exp / expToLevel
     */
    public float getExpRatio() {
        return (float)getExperience() / getExpToLevel();
    }
    
    public EnumMap<Stats, Integer> getStartStats() {
        return startingStats;
    }
    
    public EnumMap<Stats, Integer> getLevelStats() {
        return levelingStats;
    }
    
    /**
     * Returns the requested stat's value
     * @param s
     * @return value of the Stat
     */
    public int getStat( Stats s ) {
        return myStats.get( s );
    }
    // --------------- Personal getters for all the Stats ------------------- //
    
    public String getName() {
        return name;
    }
    
    /** Returns the level of this sprite */
    public int getLevel() {
        return myStats.get( Stats.LEVEL );
    }

    /** Returns the experience of this sprite */
    public int getExperience() {
        return myStats.get( Stats.EXPERIENCE );
    }

    /** Returns the exp needed for the next level of this sprite */
    public int getExpToLevel() {
        return myStats.get( Stats.EXPTOLEVEL );
    }

    /** Returns the hp of this sprite */
    public int getHp() {
        return myStats.get( Stats.HP );
    }

    /** Returns the maxHp of this sprite */
    public int getMaxHp() {
        return myStats.get( Stats.MAXHP );
    }

    /** Returns the attack/strength of this sprite */
    public int getAttack() {
        return myStats.get( Stats.ATTACK );
    }

    /** Returns the speed of this sprite */
    public int getSpeed() {
        return myStats.get( Stats.SPEED );
    }

    /** Returns the time to reload for this sprite */
    public int getReloadSpeed() {
        return myStats.get( Stats.RELOADSPEED );
    }

    /** Returns the luck of this sprite */
    public int getLuck() {
        return myStats.get( Stats.LUCK );
    }
    
    // ---------------------------- Testing -------------------------- //
    
    public String toString() {
        return "Data:\n   " + myStats + "\n   " + startingStats + "\n   " + levelingStats;
    }
    
}
