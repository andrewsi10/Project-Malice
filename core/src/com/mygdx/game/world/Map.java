package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map
{
    private Tile[][] tiles = new Tile[25][25];
    
    public void load()
    {
        for ( int r = 0; r < tiles.length; r++ )
        {
            for ( int c = 0; c < tiles[r].length; c++ )
            {
                tiles[r][c] = new Tile("DarkGreen.png",false);
            }
        }
    }
    
    public void draw( SpriteBatch batch )
    {
        
    }
    
    public void dispose()
    {
        for ( Tile[] t : tiles )
            for ( Tile tile : t )
                tile.dispose();
    }
}
