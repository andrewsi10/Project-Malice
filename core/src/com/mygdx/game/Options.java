package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.entities.ItemSprite;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Player;
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
    public static Skin SKIN;
    
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
        ItemSprite.loadAnimations();
        ItemSprite.loadMaps();
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
        float zoom = Malice.isAndroid ? 0.7f : 1.1f;
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
        SKIN = new Skin( Gdx.files.internal( "ui/uiskin1.json" ) );
        
        // everything is initialized in the file for the skin
        
        SKIN.add( "label", SKIN.get( LabelStyle.class ) );// line should be removed but kept for convenience
    }
    
}
