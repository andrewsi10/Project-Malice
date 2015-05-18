package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map
{
    public static final int ARENA = 0;
    public static final int DUNGEON = 1;
//    private Tile[][] tiles;
    
    private boolean[][] areSpaces;
    private Texture block;
    private Texture space;
    
//    public Map()
//    {
//        this( 25,25 );
//    }
    
    public Map( int rows, int cols)
    {
        block = new Texture( "map/DarkGreen.png" );
        space = new Texture( "map/background.png" );
        areSpaces = new boolean[25][25];
//      for ( int r = 0; r < areSpaces.length; r++ )
//      {
//          for ( int c = 0; c < areSpaces[r].length; c++ )
//          {
//              areSpaces[r][c] = ( r + c ) % 2 == 0 ? true : false;
//          }
//      }
    }
    
    public void generate( int type )
    {
//        switch ( type )
//        {
//            case ARENA:
//                createRoom( 0, 0, areSpaces.length, areSpaces[0].length );
//                break;
//            case DUNGEON:
//                break;
//        }
        createRoom( 0, 0, areSpaces.length, areSpaces[0].length );
        createRoom( 0, 0, 5, 5 );
        createRoom( 5, 4, 5, 5 );
        createRoom( 10, 0, 5, 5 );
        createRoom( 5, 4, 5, 5 );
    }
    
    public void createRoom( int x, int y, int w, int h )
    {
        this.createRoom( x, y, w, h, true );
    }
    
    private void createRoom( int x, int y, int w, int h, boolean fillRight )
    {
        if ( w <= 0 || x >= areSpaces.length - 1
          || h <= 0 || y >= areSpaces[0].length - 1 ) return;
        if ( x <= 0 ) { createRoom( 1, y, w + x - 1, h, true ); return; }
        if ( y <= 0 ) { createRoom( x, 1, w, h + y - 1, true ); return; }
        this.areSpaces[x][y] = true;
        if ( fillRight ) createRoom( x + 1, y, w - 1, h, true );
        createRoom( x, y + 1, w, h - 1, false );
    }
    
    private void randomGeneration()
    {
        int w = areSpaces.length;
        int h = areSpaces[0].length;
        
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
    }
    
    public void dispose()
    {
        this.block.dispose();
        this.space.dispose();
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
