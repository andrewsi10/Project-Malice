package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Texture;

public class Tile extends Texture
{
    private boolean collidable;

    public Tile( String internalPath, boolean b )
    {
        super( internalPath );
        collidable = b;
    }
    
    public boolean isCollidable()
    {
        return collidable;
    }
    
}
