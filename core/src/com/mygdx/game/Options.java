package com.mygdx.game;

import java.util.EnumMap;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

/**
 *
 *  @author  Nathan Lui
 *  @version May 26, 2015
 *  @author  Period: 4
 *  @author  Assignment: Project Malice
 */
public class Options
{

    public enum Name {
        BlackMage( "Dark Wizard", "DarkFire" ), 
        Monk( "Brawler", "Boomerang" ), 
        RedMage( "Crimson Wizard", "Fireball" ), 
        Thief( "Bandit", "PoisonShot" ), 
        Warrior( "Warrior", "Sword1" ), 
        WhiteMage( "Mage of Justice", "HolyCross" );
        
        private String button, projectile;
        
        Name( String button, String projectile ) {
            this.button = button;
            this.projectile = projectile;
        }
        
        public String getButtonName()
        {
            return button;
        }
        
        public String getProjectileName()
        {
            return projectile;
        }
    }
    public static final Skin SKIN = new Skin( Gdx.files.internal( "ui/uiskin.json" ) );
    public static final BitmapFont FONT = new BitmapFont();
    public static final Name[] NAMES = Name.values();
    public static final EnumMap<Name, Animation[]> playerAtlas = new EnumMap<Name, Animation[]>(Name.class);
    public static final HashMap<String, Animation> atlas = new HashMap<String, Animation>();
    public static final int NUMENEMIES = 7;
    public static final float FRAME_DURATION = 0.2f;
    
    public static void initialize()
    {
        Audio.initializeAudio();
        loadAtlas();
        createSkin();
    }
    
    private static void loadAtlas()
    {
        String s;
        Array<AtlasRegion> a;
        for ( Name n : NAMES )
        {
            a = new TextureAtlas( "img/sprites/Players/" + n + "/" + n + ".atlas" ).getRegions();
            playerAtlas.put( n, new Animation[]{
                new Animation( FRAME_DURATION, a.get( 0 ), a.get( 1 ) ),
                new Animation( FRAME_DURATION, a.get( 2 ), a.get( 3 ) ),
                new Animation( FRAME_DURATION, a.get( 4 ), a.get( 5 ) ),
                new Animation( FRAME_DURATION, a.get( 6 ), a.get( 7 ) ) } );
            
            s = n.getProjectileName();
            a = new TextureAtlas( "img/sprites/Projectiles/" + s + "/" + s + ".atlas" ).getRegions();
            atlas.put( s, new Animation( FRAME_DURATION, a ) );
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
        SKIN.add( "touchBackground", new Texture(
                "ui/touchBackground.png" ) );
        // Set knob image
        SKIN.add( "touchKnob", new Texture( "ui/touchKnob.png" ) );
    }

    // -------------------------- Music and Audio --------------------- //
    /**
     * This class supports all the Audio in the game
     * 
     * note: This class below can be taken out of the Options class and into a 
     * separate class for organization
     *
     *  @author  Nathan Lui
     *  @version Jun 29, 2015
     *  @author  Period: 4
     *  @author  Assignment: Project Malice-core
     *
     *  @author  Sources:
     */
    public static class Audio {
        public static boolean SOUND_MUTED = false; // not implemented into the game, provides ability to mute once all audio is isolated
        public static boolean MUSIC_MUTED = false;

        public static Music mainTheme;
        public static HashMap<String,Sound> SOUNDS;

        /**
         * Initializes all the audio
         */
        private static void initializeAudio()
        {
            mainTheme = Gdx.audio.newMusic( Gdx.files.internal( "audio/music/revivedpower.mp3" ) );
            SOUNDS = new HashMap<String, Sound>();
            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
            for ( Name n : NAMES )
                SOUNDS.put( n.getProjectileName(), Gdx.audio.newSound( Gdx.files.internal( "audio/sound/" + n.getProjectileName().toLowerCase() + ".wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
//            SOUNDS.put( "levelup", Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) ) );
        }
        
        /**
         * Stops the theme music from playing
         * @param type method of stopping:
         *          0 - pauses playing
         *          1 - stops playing (returns theme to the beginning)
         */
        public static void stopTheme( int type )
        {
            if ( mainTheme.isPlaying() ) // may be able to remove
                switch ( type )
                {
                    case 0:
                        mainTheme.pause();
                        break;
                    case 1:
                        mainTheme.stop();
                        break;
                }
        }
        
        /**
         * Plays the theme music
         * @param volume volume to set the theme music to
         */
        public static void playTheme( float volume )
        {
            if ( mainTheme == null )
                initializeAudio();
            mainTheme.setLooping( true );
            mainTheme.setVolume( volume );
            if ( !MUSIC_MUTED )
                mainTheme.play();
            else
                stopTheme( 1 );
        }

        /**
         * Plays a sound based on a given string (relates to the file name)
         * @param s string related to the file name of the sound to play
         */
        public static void playAudio( String s )
        {
            if ( !SOUND_MUTED && SOUNDS.containsKey( s ) )
                SOUNDS.get( s ).play();
        }
    }
    
}
