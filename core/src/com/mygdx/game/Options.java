package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Player;
import com.mygdx.game.screens.GameScreen;
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
    /**
     * Skin containing the resources for many of this game's assets
     */
    public static final Skin SKIN = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
    private static final BitmapFont FONT = new BitmapFont();
    
    /**
     * The FileHandle for the settings file
     */
    public static final FileHandle SETTINGS = Gdx.files.local( "settings.txt" );
    
    /**
     * Initializes Audio, SKIN, player EnumMaps, Enemy Animations, map biomes,
     * and settings values from file
     */
    public static void initialize()
    {
        Audio.initializeAudio();
        createSkin();
        Player.loadMaps();
        Enemy.loadAnimations();
        Map.loadBiomes();
        if ( SETTINGS.exists() )
            loadSettings();
        else
            defaultSettings();
    }
    
    /**
     * Disposes of Resources
     */
    public static void dispose() {
        SKIN.dispose();
        FONT.dispose();
        Map.disposeBiomes();
    }
    
    /**
     * Loads the settings
     */
    public static void loadSettings() {
        String[] strings = SETTINGS.readString().split( "\\s+" );
        int music = Integer.parseInt( strings[1] );
        int sound = Integer.parseInt( strings[3] );
        float zoom = Float.parseFloat( strings[5] );
        setSettings( music, sound, zoom );
    }
    
    /**
     * Loads default settings
     */
    public static void defaultSettings() {
        float zoom = Gdx.app.getType().equals( ApplicationType.Android ) ? 0.7f : 1.1f;
        setSettings( 100, 100, zoom );
    }
    
    /**
     * Settings settings based on parameters
     * @param music music volume
     * @param sound sound volume
     * @param zoom zoom amount
     */
    public static void setSettings( int music, int sound, float zoom ) {
        Audio.MUSIC_VOLUME = music;
        Audio.SOUND_VOLUME = sound;
        GameScreen.ZOOM = zoom;
    }
    
    /**
     * Saves the settings
     */
    public static void saveSettings() {
        SETTINGS.writeString( "Music: " + Audio.MUSIC_VOLUME + "\n", false );
        SETTINGS.writeString( "Sound: " + Audio.SOUND_VOLUME + "\n", true );
        SETTINGS.writeString( "Zoom: " + GameScreen.ZOOM + "\n", true );
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
        
        // Create TouchPad Style
        // Set background image
        SKIN.add( "touchBackground", new Texture( "ui/touchBackground.png" ) );
        // Set knob image
        SKIN.add( "touchKnob", new Texture( "ui/touchKnob.png" ) );
        TouchpadStyle touchpadStyle = new TouchpadStyle();
        // Create Drawable's from TouchPad skin
        Drawable touchBackground = SKIN.getDrawable( "touchBackground" );
        Drawable touchKnob = SKIN.getDrawable( "touchKnob" );
        // Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        
        SKIN.add( "touchPad", touchpadStyle );
        
        LabelStyle labelStyle = new LabelStyle( FONT, Color.WHITE );
        SKIN.add( "label", labelStyle );
    }
    
}
