package com.mygdx.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Controller;
import com.mygdx.game.Malice;
import com.mygdx.game.Options;
import com.mygdx.game.player.Character;
import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Player;
import com.mygdx.game.projectile.Projectile;
import com.mygdx.game.world.Map;

/**
 * GameScreen handles gameplay and manages the interaction between the map,
 * characters, and projectiles.
 * 
 * GameScreen does all collision detection between the map walls, characters,
 * and projectiles and determines when characters and projectiles move around
 * the screen. It uses ArrayLists to store all the characters and projectiles
 * currently in the game. GameScreen also calculates the delta x and delta y
 * values for projectile aiming. Furthermore, GameScreen utilizes an Enum to
 * allow for pausing and resuming the game.
 *
 * @author Andrew Si
 * @author Other contributors: Christopher Cheung, Nathan Lui
 * @version May 31, 2015
 * @author Period: 4
 * @author Assignment: my-gdx-game-core
 *
 * @author Sources: libgdx
 */
public class GameScreen implements Screen
{

	private SpriteBatch batch;
    private OrthographicCamera cam;
    private ShapeRenderer renderer;

	private final Malice game;
	
	private Controller controller = new Controller();
	
    /**
     * Volume of this screen
     */
    private static final float VOLUME = 0.4f;

	/**
	 * Size of the map
	 */
	public final static int MAP_SIZE = 50;
	private Map map;
    private Texture pauseTexture;
	private Player player;
	private ArrayList<Character> sprites;
	private ArrayList<Projectile> projectiles;

	/**
	 * Enum for handling the various states of the game screen.
	 *
	 * The game has multiple states, such as RUN (game is proceeding regularly),
	 * PAUSE (game displays pause menu), and RESUME (game freezes temporarily).
	 * This Enum State keeps track of the state of the game.
	 *
	 * @author Andrew Si
	 * @version May 31, 2015
	 * @author Period: 4
	 * @author Assignment: my-gdx-game-core
	 *
	 * @author Sources: libgdx
	 */
	public enum State
	{
		PAUSE, RUN, RESUME
	}

	private State state;
	private int enemyMaxCount;
	private int enemyMinCount;
	private long timeResumed;

	/**
	 * Creates a CharacterSelect screen and stores the Malice object that
	 * created this screen, and the class that the
	 * player chose.
	 * 
	 * @param g
	 *            the Malice object controlling the screens
	 *  playerType
	 *            the class that the player chose in the CharacterSelect screen
	 */
	public GameScreen(Malice g)
	{
		new Stage();
		game = g;
        projectiles = new ArrayList<Projectile>();
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        pauseTexture = new Texture( "img/pausescreen.png" );
        sprites = new ArrayList<Character>();

        enemyMaxCount = -2;
        enemyMinCount = 10;
        
        map = new Map( MAP_SIZE, MAP_SIZE );
        cam = new OrthographicCamera( 1280, 720 );
        // cam.setToOrtho(false, 1280, 720);
	}
    
    public Screen update( Options.Names type )
    {
        if ( player == null )
            player = new Player( type );
        else
            player.change( type );
        return this;
    }

	/**
	 * Shows gameplay with the player in the center of the screen inside a
	 * procedurally generated map.
	 * 
	 * The Game Screen starts out in the RESUME state. This method generates a
	 * map, then spawns all characters including the player and the enemies. All
	 * character spawn locations are completely randomized.
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
        Options.Audio.playTheme( VOLUME );
        Gdx.input.setInputProcessor( controller );
		timeResumed = System.currentTimeMillis();
		state = State.RESUME;

        sprites.clear();
		map.generate( Map.Generation.RANDOM, Map.Biome.RANDOM );
        player.setPosition( map.getSpawnX(), map.getSpawnY() );
        player.reload();
        sprites.add( player );

        spawnEnemies();
	}

	/**
	 * Render calls different helper methods depending on the current state of
	 * the game.
	 * 
	 * If the state is RUN, then renderRun is called. If the state is PAUSE,
	 * renderPaused is called. If the state is RESUME, then renderRESUME is
	 * called.
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 * @param delta
	 *            The time in seconds since the last render
	 */
	@Override
	public void render(float delta)
	{
		switch ( state )
		{
		case RUN :
			renderRun( delta );
			break;
		case PAUSE :
			renderPaused( delta );
			break;
		case RESUME :
			renderResume( delta );
			break;
		}
	}

	/**
	 * Displays a static pause menu image until the player presses escape.
	 * 
	 * Pauses the music and does not update the game. This creates flicker if a
	 * character is moving, so a pause menu image is drawn to mask the
	 * flickering. When the escape key is pressed, the game state is changed to
	 * resume.
	 * 
	 * @param delta
	 *            The time in seconds since the last render
	 */
	public void renderPaused(float delta)
	{
	    Options.Audio.stopTheme( 0 ); // pause the theme music
		batch.begin();
		batch.draw( pauseTexture, 
		    cam.position.x - pauseTexture.getWidth() / 2, 
		    cam.position.y - pauseTexture.getHeight() / 2 );
		batch.end();
		if ( Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ) )
		{
			state = State.RESUME;
			timeResumed = System.currentTimeMillis();
		}
	}

	/**
	 * Displays all characters and projectiles on the map but does not move any
	 * of them until two seconds pass.
	 * 
	 * Draws a map, then all the characters from the projectile ArrayList and
	 * their HP bars, then all the projectiles from the projectile ArrayList.
	 * Displays a timer telling the user how long until the game unfreezes.
	 * 
	 * @param delta
	 *            The time in seconds since the last render
	 */
	public void renderResume(float delta)
	{
        setMatrixAndCam();

		batch.begin();
		renderer.begin( ShapeType.Filled );
		map.draw( batch );

		if ( sprites.size() < 3 )
		{
			spawnEnemies();
		}

		for ( Character sprite : sprites )
		{
			sprite.draw( batch );
			sprite.drawBars( batch, renderer );
		}
		for ( Projectile projectile : projectiles )
		{
			projectile.draw( batch );
		}
		float fontX = cam.position.x - 100;
		float fontY = cam.position.y + cam.viewportHeight / 3;
		drawPoints();
		setFontColor( fontX, fontY );
		Options.FONT.draw( batch,
				"Game resumes in "
						+ ( 1 + ( 2000 - System.currentTimeMillis() + timeResumed ) / 1000 )
						+ " seconds", fontX, fontY );
		batch.end();
		renderer.end();

		if ( System.currentTimeMillis() - timeResumed > 2000 )
		{
			state = State.RUN;
		}
	}

	/**
	 * Displays all characters and projectiles on the map and moves each of
	 * them.
	 * 
	 * If the music is paused, it is unpaused. Draws a map, then spawns enemies
	 * if less than three characters remain, then all the characters from the
	 * projectile ArrayList and their HP bars, then all the projectiles from the
	 * projectile ArrayList. Then the method moves the characters in the
	 * character ArrayList and the projectiles in the projectile ArrayList with
	 * the moveSprite helper method. Finally, draws the number of points that
	 * the player has and pauses the game if the escape button is pressed.
	 * 
	 * If there are any collisions between a projectile and the wall, the
	 * projectile is removed. If there are any collisions between a projectile
	 * and a character, the projectile is deleted and the character health is
	 * decreased. Collisions between characters and walls are handled by the
	 * moveSprite helper method.
	 * 
	 * If a character dies and is an instance of the enemy class, the number of
	 * points that the player has is increased and more enemies spawn. If the
	 * character that died was an instance of player, then the game ends, the
	 * music stops, and the GameOver screen is displayed.
	 * 
	 * @param delta
	 *            The time in seconds since the last render
	 */
	public void renderRun(float delta)
	{
	    setMatrixAndCam();

		Options.Audio.playTheme( VOLUME ); // note: removed check, may cause lag without check for isPlaying()

		batch.begin();
		renderer.begin( ShapeType.Filled );
		map.draw( batch );

		if ( sprites.size() < 3 )
		{
			spawnEnemies();
		}

		for ( Character sprite : sprites )
		{
			moveSprite( sprite );
			sprite.drawBars( batch, renderer );
		}
		for ( int i = 0; i < projectiles.size(); i++ )
		{
			Projectile projectile = projectiles.get( i );
			projectile.move();
			projectile.draw( batch );

			boolean hasHit = false;
			for ( Character sprite : sprites )
			{
				if ( hasHit || projectile.hitCharacter( sprite ) )
				{
					hasHit = true;
					sprite.takeDamage( projectile.getDamage() );
					if ( sprite.isDead() )
					{
						sprites.remove( sprite );
						if ( sprite instanceof Enemy )
						{
							player.increasePoints();
							player.increaseExp( sprite.getExperience() );
							spawnEnemies( player.getPoints() / 60 );
						} else if ( sprite instanceof Player )
						{
						    Options.Audio.stopTheme( 1 ); // stops the theme music
							game.setScreen( game.gameOver.update( 
							                       player.getPoints(), 
							                       player.getCurrentLevel() ) );
						}
					}
					break;
				}
			}
			if ( hasHit || map.isCollidingWithWall( projectile ) )
			{
				projectiles.remove( i );
				i--;
			}
		}
		drawPoints();
		batch.end();
		renderer.end();

		if ( Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ) )
		{
			state = State.PAUSE;
		}
	}
	
	private void setMatrixAndCam()
	{
        cam.position.x = player.getX();
        cam.position.y = player.getY();
        cam.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix( cam.combined );
        renderer.setProjectionMatrix( cam.combined );
	}
	
	private void spawnEnemies()
	{
	    spawnEnemies( enemyMinCount + (int) ( Math.random() * enemyMaxCount ) );
	}

	/**
	 * Helper method that spawns enemies in random locations, where the number
	 * of enemies spawned is based on the number of points the player has.
	 * 
	 * This method is called in the beginning of gameplay and if less than three
	 * characters remain.
	 * 
	 */
	private void spawnEnemies( int limit )
	{
		for ( int i = 0; i < limit; i++ )
		{
			int index = 1 + (int) ( Math.random() * Options.NUMENEMIES );
			Enemy e = new Enemy( Options.atlas.get( "Enemy" + index ) );
			e.increaseBdmg( -5 );
			// set spawn for enemy
			map.setSpawn( player.getX(), player.getY() );
			e.setPosition( map.getSpawnX(), map.getSpawnY() );
			sprites.add( e );
		}
	}

	/**
	 * Helper method that calls a character's move method, then checks for
	 * collisions with a wall.
	 * 
	 * If there is a collision with a wall, the character is placed back in its
	 * old position.
	 * 
	 * @param c
	 *            the Character that is being moved
	 */
	private void moveSprite(Character c)
	{
		float x = c.getX();
		float y = c.getY();
		c.move( player, projectiles, System.currentTimeMillis() );
		if ( map.isCollidingWithWall( c ) )
		{
		    int dir = c.getRoundedDirection();
			c.setPosition( x, y );
			c.setDirection( dir - 45 );
			c.move();
			if ( map.isCollidingWithWall( c ) )
			{
				c.setPosition( x, y );
				c.setDirection( dir + 45 );
				c.move();
				if ( map.isCollidingWithWall( c ) )
					c.setPosition( x, y );
			}
		}
		c.setAnimations(); // TODO be aware of the animation bugs produced by this line
		c.draw( batch );
	}

	/**
	 * Draws the number of points the player has at the top left of the game
	 * screen.
	 */
	private void drawPoints()
	{
		float fontX = cam.position.x - cam.viewportWidth / 2;
		float fontY = cam.position.y + cam.viewportHeight / 2;
		setFontColor( fontX, fontY );
		Options.FONT.draw( batch, "POINTS: " + player.getPoints(), fontX, fontY );
	}

	/**
	 * Changes the color of the font based on whether or not its location is
	 * within map boundaries.
	 * 
	 * If the font location is within the boundaries, then the font color is
	 * changed to black. Otherwise, the color is changed to white.
	 * 
	 * @param fontX
	 *            x coordinate of the font location
	 * @param fontY
	 *            y coordinate of the font location
	 */
	private void setFontColor(float fontX, float fontY)
	{
	    Options.FONT.setColor( map.inPixelBounds( fontX, fontY ) ? Color.BLACK : Color.WHITE );
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {}

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {}

	/**
	 * Removes the Map, SpriteBatch, and Font to prevent memory leakage.
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		map.dispose();
		batch.dispose();
		renderer.dispose();
		pauseTexture.dispose();
        player.getTexture().dispose();
        for ( Projectile p : projectiles )
            p.getTexture().dispose();
        for ( Character e : sprites )
            e.getTexture().dispose();
	}

}
