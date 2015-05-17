package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map
{
    private Tile[][] tiles;
    
    public Map()
    {
        this( 25,25 );
    }
    
    public Map( int rows, int cols)
    {
        tiles = new Tile[rows][cols];
        for ( int r = 0; r < tiles.length; r++ )
        {
            for ( int c = 0; c < tiles[r].length; c++ )
            {
                String s =  ( r + c ) % 2 == 0 ? "DarkGreen.png" : "background.png";
                tiles[r][c] = new Tile(s, false );
            }
        }
    }
    
    public void generate()
    {
        
    }
    
    public boolean checkWallCollisions( Sprite s )
    {
        s.getBoundingRectangle();
        return false;
    }
    
    public void draw( SpriteBatch batch )
    {
        for ( int r = 0; r < tiles.length; r++ )
        {
            for ( int c = 0; c < tiles[r].length; c++ )
            {
                batch.draw( tiles[r][c], r*Tile.TILE_WIDTH, c*Tile.TILE_HEIGHT );
            }
        }
    }
    
    public void dispose()
    {
        for ( Tile[] t : tiles )
            for ( Tile tile : t )
                tile.dispose();
    }
}
