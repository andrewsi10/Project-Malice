package com.mygdx.game.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.sprites.StatsSprite;
import com.mygdx.game.sprites.StatsSprite.Stats;

public class StatLoader
{
    private FileHandle file;
    
    public StatLoader( String filePath ) {
        file = Gdx.files.internal( filePath );
    }
    
    public void loadSpriteStats( StatsSprite sprite ) {
        for ( Stats stat : Stats.values() ) {
            
        }
    }
}
