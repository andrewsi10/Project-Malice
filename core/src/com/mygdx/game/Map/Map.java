package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map
{
//    private Tile[][] tiles;
    
    private boolean[][] areSpaces;
    private Texture block;
    private Texture space;
    
    public Map()
    {
        this( 25,25 );
    }
    
    public Map( int rows, int cols)
    {
        block = new Texture( "DarkGreen.png" );
        space = new Texture( "background.png" );
        areSpaces = new boolean[25][25];
//      for ( int r = 0; r < areSpaces.length; r++ )
//      {
//          for ( int c = 0; c < areSpaces[r].length; c++ )
//          {
//              areSpaces[r][c] = ( r + c ) % 2 == 0 ? true : false;
//          }
//      }
//        tiles = new Tile[rows][cols];
//        for ( int r = 0; r < tiles.length; r++ )
//        {
//            for ( int c = 0; c < tiles[r].length; c++ )
//            {
//                String s =  ( r + c ) % 2 == 0 ? "DarkGreen.png" : "background.png";
//                tiles[r][c] = new Tile(s, false );
//            }
//        }
    }
    
    public void generate()
    {
        createRoom( 0, 0, areSpaces.length, areSpaces[0].length );
//        createRoom( 1, 1, 15, 15 );
//        createRoom( 0, 0, 10, 10 );
//        createRoom( 0, 0, 11, 11 );
//        createRoom( 5, 5, 5, 5 );
//        createRoom( 10, 10, 5, 5 );
//        createRoom( 15, 15, 5, 5 );
//        createRoom( 20, 20, 5, 5 );
    }
    
    public void createRoom( int x, int y, int w, int h )
    {
        this.createRoom( x, y, w, h, true );
    }
    
    private void createRoom( int x, int y, int w, int h, boolean fillRight )
    {
        System.out.println( x + ", "  + y + ", " + w + ", " + h );
        if ( w <= 0 || x >= areSpaces.length - 1
          || h <= 0 || y >= areSpaces[0].length - 1 ) return;
        if ( x <= 0 ) { createRoom( 1, y, w + x - 1, h, true ); return; }
        if ( y <= 0 ) { createRoom( x, 1, w, h + y - 1, true ); return; }
        this.areSpaces[x][y] = true;
//        System.out.println( "Right" );
        if ( fillRight )
        createRoom( x + 1, y, w - 1, h, true );
//        System.out.println( "Down" );
        createRoom( x, y + 1, w, h - 1, false );
    }
    
    public boolean isCollidingWithWall( Sprite s )
    {
        s.getBoundingRectangle();
        return false;
    }
    
    public void draw( SpriteBatch batch )
    {
        for ( int i = 0; i < areSpaces.length; i++ )
        {
            for ( int j = 0; j < areSpaces[i].length; j++ )
            {
                batch.draw( areSpaces[i][j]?space:block, i*block.getWidth(),j*block.getHeight());
            }
        }
//        for ( int r = 0; r < tiles.length; r++ )
//        {
//            for ( int c = 0; c < tiles[r].length; c++ )
//            {
//                batch.draw( tiles[r][c], r*Tile.TILE_WIDTH, c*Tile.TILE_HEIGHT );
//            }
//        }
    }
    
    public void dispose()
    {
        this.block.dispose();
        this.space.dispose();
        
//        for ( Tile[] t : tiles )
//            for ( Tile tile : t )
//                tile.dispose();
    }
    
    public int getX( int x )
    {
        return x * block.getWidth() + block.getWidth() / 2;
    }
    
    public int getY( int y )
    {
        return y * block.getHeight() + block.getHeight() / 2;
    }
}
