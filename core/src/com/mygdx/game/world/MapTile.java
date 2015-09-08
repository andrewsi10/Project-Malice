package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapTile
{
    private TextureRegion floor;
    private TextureRegion wall;
    
    public MapTile() {}
    public MapTile( TextureRegion floor, TextureRegion wall )
    {
        this.setTile( floor, wall );
    }
    public MapTile( MapTile m )
    {
        this( m.floor, m.wall );
    }
    
    public boolean isSpace() 
    {
        return wall == null;
    }
    
    public void draw( Batch batch, int x, int y )
    {
        batch.draw( floor, x, y );
        if ( !isSpace() )
            batch.draw( wall, x, y );
    }
    
    public void setFloor( TextureRegion floor )
    {
        this.floor = floor;
    }
    
    public void setWall( TextureRegion wall )
    {
        this.wall = wall;
    }
    
    public void setTile( TextureRegion floor, TextureRegion wall )
    {
        this.setFloor( floor );
        this.setWall( wall );
    }
}
