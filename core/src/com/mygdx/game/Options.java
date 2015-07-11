package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Player;
import com.mygdx.game.world.Map;

/**
 *
 *  @author  Nathan Lui
 *  @version May 26, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice
 */
public class Options
{
    public static final Skin SKIN = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
    public static final BitmapFont FONT = new BitmapFont();
    
    public static void initialize()
    {
        createSkin();
        Player.loadMaps();
        Enemy.loadAnimations();
        Map.loadBiomes();
    }
    
    public static void dispose() {
        SKIN.dispose();
        FONT.dispose();
        Map.disposeBiomes();
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
