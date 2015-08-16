package com.mygdx.game.entities;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.sprites.Character;
import com.mygdx.game.sprites.SpriteData.Stats;

public class ItemSprite extends Entity
{
    public enum Item {
        HealthKit;
        
        /**
         * Returns whether conditions are right for this Item to collide with 
         * Character
         * @param c Character to collide with
         * @return whether conditions are right for this Item to collide
         */
        public boolean specificallyCollidesWith( Character c ) {
            boolean b = false;
            switch ( this ) {
                case HealthKit:
                    b = !c.getSpriteData().atFullHp();
            }
            return b;
        }
    }
    
    public static final EnumMap<Item, Animation> itemAnimations = new EnumMap<Item, Animation>( Item.class );
    
    public static void loadAnimations() {
        TextureRegion t;
        for ( Item i : Item.values() ) {
            t = new TextureRegion( new Texture( "img/items/" + i + ".png" ) );
            itemAnimations.put( i, new Animation( FRAME_DURATION, t ) );
        }
    }
    
    // TODO should create an Effect class to store Stats and the effect for more flexibility 
    // i.e. timers for status effects
    private static final EnumMap<Item, Stats> stats = new EnumMap<Item, Stats>( Item.class );
    private static final EnumMap<Item, Integer> effects = new EnumMap<Item, Integer>( Item.class );
    
    public static void loadMaps() { // TODO note: should use a file to load properties
        stats.put( Item.HealthKit, Stats.HP );
        effects.put( Item.HealthKit, +10 ); // HealthKits add 10 hp when picked up
    }
    
    private Item item;
    
    /**
     * Constructs an ItemSprite
     * @param c Character that "dropped" this ItemSprite
     * @param i Item that this ItemSprite is to represent
     */
    public ItemSprite( Character c, Item i ) {
        super( c, -1, itemAnimations.get( i ) );
        item = i;
        
        setWallCollision( false );
        setSize( 64, 64 );
        setCenterPosition( c.getCenterX(), c.getCenterY() );
    }

    /**
     * ItemSprites currently do not move (may change depending on items added)
     * 
     * @see com.mygdx.game.entities.Entity#move()
     */
    @Override
    public void move() {}

    /**
     * Does stuff to Character colliding with according to Item type
     * 
     * @see com.mygdx.game.entities.Entity#hitCharacter(com.mygdx.game.sprites.Character)
     */
    @Override
    public boolean hitCharacter( Character c )
    {
        if ( !sameTeamWith( c ) && collidesWith( c ) 
                        && item.specificallyCollidesWith( c ) ) {
            c.getSpriteData().increaseStat( stats.get( item ), effects.get( item ) );
            c.updateLabels();
            return true;
        }
        return false;
    }
}
