package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tile extends Texture
{
    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;
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
