package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.CharacterSelect;

/**
 *
 *  @author  Nathan Lui
 *  @version May 26, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice
 */
public class Options
{
    public enum Enemy {
        
    }
    
    public static final Skin SKIN = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
    public static final BitmapFont FONT = new BitmapFont();
    public static final HashMap<String, Animation> atlas = new HashMap<String, Animation>();
    public static final int NUMENEMIES = 7;
    public static final float FRAME_DURATION = 0.2f;
    
    public static void initialize()
    {
        createSkin();
        CharacterSelect.loadMap();
        loadAtlas();
    }
    
    private static void loadAtlas()
    {
        String s;
        Array<AtlasRegion> a;
        for ( CharacterSelect.Name n : CharacterSelect.NAMES )
        {
            atlas.put( n.getProjectileName(), CharacterSelect.PROJECTILE_ANIMATIONS.get( n ) );
        }
        for ( int i = 1; i <= NUMENEMIES; i++ )
        {
            s = "Enemy" + i;
            a = new TextureAtlas( "img/sprites/Enemies/" + s + "/" + s + ".atlas" ).getRegions();
            atlas.put( s, new Animation( FRAME_DURATION, a ) );
        }
        a = new TextureAtlas( "img/sprites/Projectiles/EnemyBullet/EnemyBullet.atlas" ).getRegions();
        atlas.put( "EnemyBullet", new Animation( FRAME_DURATION, a ) );
    }

    /**
     * Creates a skin and the text button style that will be displayed in the
     * main menu.
     * 
     * The skin should be the default LibGDX skin and the text button style
     * should also be the default style.
     */
    private static void createSkin()
    {
        SKIN.add( "default", FONT );

        // Create a texture
        Pixmap pixmap = new Pixmap( (int) Gdx.graphics.getWidth() / 4,
                (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888 );
        pixmap.setColor( Color.WHITE );
        pixmap.fill();
        SKIN.add( "background", new Texture( pixmap ) );
        pixmap.dispose();

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = SKIN.newDrawable( "background", Color.GRAY );
        textButtonStyle.down = SKIN.newDrawable( "background", Color.DARK_GRAY );
        textButtonStyle.checked = SKIN.newDrawable( "background", Color.DARK_GRAY );
        textButtonStyle.over = SKIN.newDrawable( "background", Color.LIGHT_GRAY );
        textButtonStyle.font = SKIN.getFont( "default" );
        SKIN.add( "default", textButtonStyle );
        // Set background image
        SKIN.add( "touchBackground", new Texture( "ui/touchBackground.png" ) );
        // Set knob image
        SKIN.add( "touchKnob", new Texture( "ui/touchKnob.png" ) );
    }
    
}
