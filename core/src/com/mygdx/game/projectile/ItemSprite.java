package com.mygdx.game.projectile;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.player.Character;

public class ItemSprite extends Entity
{
    public enum Item {
        HealthKit
    }
    
    public static final EnumMap<Item, Animation> itemAnimations = new EnumMap<Item, Animation>( Item.class );
    
    public static void loadAnimations() {
        TextureRegion t;
        for ( Item i : Item.values() ) {
            t = new TextureRegion( new Texture( "img/items/" + i + ".png" ) );
            itemAnimations.put( i, new Animation( FRAME_DURATION, t ) );
        }
    }
    
    public ItemSprite( Character c, Item i ) {
        super( -1, itemAnimations.get( i ) );
        
        setSize( 64, 64 );
        setCenterPosition( c.getCenterX(), c.getCenterY() );
    }

    @Override
    public void move() {}

    /**
     * Does stuff according to Item type to Character colliding with
     * 
     * @see com.mygdx.game.projectile.Entity#hitCharacter(com.mygdx.game.player.Character)
     */
    @Override
    public boolean hitCharacter( Character c )
    {
        return false;
    }
}
