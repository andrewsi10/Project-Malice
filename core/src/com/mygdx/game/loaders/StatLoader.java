package com.mygdx.game.loaders;

import java.util.EnumMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.sprites.SpriteData;
import com.mygdx.game.sprites.SpriteData.Stats;

public class StatLoader
{
    public static final String PACKAGE = "values/";
    
    private FileHandle file;
    private SpriteData data = new SpriteData();
    
    public StatLoader( String filePath ) {
        file = Gdx.files.internal( PACKAGE + filePath );
        loadFile();
    }
    
    /**
     * Convenience method for loading the file
     */
    public void loadFile() {
        loadInput( file.readString() );
    }
    
    /**
     * Loads values from a string of input
     * @param input
     */
    public void loadInput( String input ) {
        String[] inputs = input.split( "\\s+" );
        Stats[] stats = Stats.values();
        int stat = 0;
        int value;
        boolean isIncrease = false;
        
        EnumMap<Stats, Integer> startStats = data.getStartStats();
        EnumMap<Stats, Integer> levelStats = data.getLevelStats();
        for ( String s : inputs ) {
            try {
                value = Integer.parseInt( s );
                if ( !isIncrease ) {
                    startStats.put( stats[stat], value );
                } 
                else {
                    levelStats.put( stats[stat], value );
                }
                isIncrease = !isIncrease;
            }
            catch ( NumberFormatException e ) {
                // NOTE: if there is an ArrayIndexOutOfBoundsException, then file is not formatted correctly
                while ( !stats[stat].toString().equals( s.toUpperCase() ) ) {
                    stat++;
                }
                isIncrease = false;
            }
        }
        // special convenience conditions
        levelStats.put( Stats.LEVEL, 1 ); // level up increases level by one
    }
    
    public SpriteData getData() {
        return data;
    }
    
//  public void setSprite( StatsSprite sprite ) {
////      System.out.println( "Setting Sprite" );
//      setSprite( sprite, 1 );
//  }
//  
//  public void setSprite( StatsSprite sprite, int newLevel ) {
//      for ( Stats s : Stats.values() ) {
//          Integer start = startStats.get( s );
//          Integer level = levelStats.get( s );
//          if ( start != null && level != null )
//          {
////              System.out.println( s + ": " + ( start + (newLevel-1)*level ) );
//              sprite.setStat( s, start + (newLevel-1)*level );
//          }
//      }
//  }
//  
//  public void levelUpSprite( StatsSprite sprite ) {
////      System.out.println( "Leveling Sprite" );
//      setSprite( sprite, sprite.getLevel() + 1 );
////      for ( Stats s : Stats.values() ) {
////          Integer i = levelUp.get( s );
////          if ( i != null )
////              sprite.increaseStat( s, i );
////      }
//  }
    
    // ---------------------------- Testing -------------------------- //
    
    /**
     * For Testing
     */
    public StatLoader() {}
    
    public String toString() {
        return file + "\n" + data;
    }
    
    /**
     * Tests this class's loadInput method
     * @param args
     */
    public static void main( String[] args ) {
        String file = "Level 1\n" 
                    + "MaxHp 50 10\n" 
                    + "Attack 20 2\n" 
                    + "Speed 5 0\n" 
                    + "ReloadSpeed 500 0\n" 
                    + "Luck 4 0";
        StatLoader loader = new StatLoader();
        System.out.println( loader );
        loader.loadInput( file );
        System.out.println( loader );
    }
}
