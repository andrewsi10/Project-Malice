package com.mygdx.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Audio;
import com.mygdx.game.Controller;
import com.mygdx.game.Malice;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.sprites.Character;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Player;
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
public class GameScreen extends StagedScreen
{
    /**
     * Size of the map
     */
    public final static int MAP_SIZE = 50;
    
    /**
     * The Zoom of the Camera, set in Options/OptionsScreen classes
     */
    public static float ZOOM;

    private Batch batch;
    private OrthographicCamera cam;
    private ShapeRenderer renderer; // hp can use progressBars
    private Controller controller;
    
	private Map map;
	private Player player;
	private ArrayList<Character> sprites;
	private ArrayList<Entity> entities;
	
	private TextButton settingsButton, backButton;
	private Label resumeLabel, pointsLabel;

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
    private int enemyMinCount, enemyMaxCount;
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
	public GameScreen( Malice g, Skin s, Controller c )
	{
	    super( g, s, c, "img/pausescreen.png", 40 );
        enemyMinCount = 10; enemyMaxCount = -2;
        
        controller = c;
        entities = new ArrayList<Entity>();
        renderer = new ShapeRenderer();
        batch = stage.getBatch();
        sprites = new ArrayList<Character>();
        
        map = new Map( MAP_SIZE, MAP_SIZE );
        cam = new OrthographicCamera();
        player = new Player( skin, c );
        
	    settingsButton = new TextButton( "Settings", skin );
        backButton = new TextButton( "Back to Main Menu", skin );
        resumeLabel = new Label( "", skin, "label" );
        pointsLabel = player.getPointsLabel(); // this screen will manage the color, positioning, and drawing of this label
        
        // Scaling
        scaleLabels( settingsButton.getLabel(), backButton.getLabel(), resumeLabel, pointsLabel );

        // xy -coordinates
        float centerX = stage.getWidth() / 2;
        float centerY = stage.getHeight() / 2;
        
        // Positioning
        settingsButton.setPosition( centerX - settingsButton.getWidth() / 2, centerY );
        backButton.setPosition( centerX - backButton.getWidth() / 2, centerY / 2 );
        resumeLabel.setPosition( centerX - 100, stage.getHeight() * 2 / 3 );
        pointsLabel.setPosition( 5, stage.getHeight() - pointsLabel.getPrefHeight() / 2 );
        
        // Listeners
	    settingsButton.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y ) {
                game.setScreen( game.optionsScreen.update( game.gameScreen ) );
                settingsButton.toggle();
            }
	    } );
	    backButton.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y ) {
                game.setScreen( game.mainMenu );
                backButton.toggle();
            }
        } );
	    
	    // add Actors
	    stage.addActor( resumeLabel );
	    stage.addActor( pointsLabel );
	    stage.addActor( settingsButton );
        stage.addActor( backButton );
	}
    
    /**
     * Updates this Screen according to the parameters where the parameters will 
     * adjust the player Sprite
     * 
     * @param proj String representing the player's projectile type
     * @param a Animation for the player
     * @return this Screen for the game to be set to
     */
    public Screen update( Player.Name n )
    {
        player.change( n );
        return update();
    }
    
    public Screen update() {
        sprites.clear();
        map.generate( Map.Generation.RANDOM, Map.Biome.RANDOM );
        player.setPosition( map.getSpawnX(), map.getSpawnY() );
        player.reload();
        sprites.add( player );

        spawnEnemies();
        
        resume();
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
	    super.show();
        cam.zoom = ZOOM;
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
	    stage.draw();
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

		spawnEnemies();
        for ( Entity entity : entities )
        {
            drawSprite( entity );
        }
		drawCharacters( false );
        batch.end();
        renderer.end();
        // set labels
		float fontX = cam.position.x - 100;
		float fontY = cam.position.y + cam.viewportHeight * ZOOM / 3;
		setPointsLabelColor();
		setFontColor( resumeLabel, fontX, fontY );
		long time = 1 + ( 2000 - System.currentTimeMillis() + timeResumed ) / 1000;
		resumeLabel.setText( "Game resumes in " + time + " seconds" );
        stage.draw();

		if ( System.currentTimeMillis() - timeResumed > 2000 ) {
		    toggleGameState();
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
	public void renderRun( float delta )
	{
	    setMatrixAndCam();

		batch.begin();
		renderer.begin( ShapeType.Filled );
		map.draw( batch );
		
		spawnEnemies();
		for ( int i = 0; i < entities.size(); i++ )
		{
			Entity entity = entities.get( i );
			entity.move();
			drawSprite( entity );

			boolean hasHit = false;
			for ( Character sprite : sprites )
			{
				if ( hasHit || entity.hitCharacter( sprite ) )
				{
					hasHit = true;
					if ( sprite.isDead() )
					{
						sprites.remove( sprite );
						if ( sprite != player )
						{
							player.increasePoints();
							player.increaseExp( sprite.getExperience() );
							spawnEnemies( (int) ( Math.random() * ( player.getPoints() / 100 ) ) + 1 );
						} else // if ( sprite instanceof Player )
						{
						    Audio.stopTheme(); // stops the theme music
							game.setScreen( game.gameOver.update( 
							                       player.getPoints(), 
							                       player.getLevel() ) );
						}
						sprite.die( entities );
					}
					break;
				}
			}
			if ( hasHit || map.isCollidingWithWall( entity ) )
			{
				entities.remove( i );
				i--;
			}
		}
        drawCharacters( true );
        batch.end();
        renderer.end();
		setPointsLabelColor();
        stage.act();
        stage.draw();
	}
	
	private void setMatrixAndCam()
	{
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );
        cam.position.x = player.getCenterX();
        cam.position.y = player.getCenterY();
        cam.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix( cam.combined );
        renderer.setProjectionMatrix( cam.combined );
	}
	
	private void spawnEnemies()
	{
        if ( sprites.size() < 3 )
            spawnEnemies( enemyMinCount + (int) ( Math.random() * enemyMaxCount ) );
	}

	/**
	 * Helper method that spawns enemies in random locations, where the number
	 * of enemies spawned is based on the number of points the player has.
	 * 
	 * This method is called in the beginning of gameplay and whenever an enemy dies.
	 * 
	 */
	private void spawnEnemies( int limit )
	{
		for ( int i = 0; i < limit; i++ )
		{
			int index = (int) ( Math.random() * Enemy.NUMENEMIES );
			Enemy e = new Enemy( skin, index );
			e.increaseBdmg( -5 + player.getPoints() / 50 );
			e.increaseMaxHp( player.getPoints() / 20 );
			e.increaseCurrentHp( player.getPoints() / 20 );
			// set spawn for enemy
			map.setSpawn( player.getX(), player.getY() );
			e.setPosition( map.getSpawnX(), map.getSpawnY() );
			sprites.add( e );
		}
	}
	

    private Rectangle checkRectangle = new Rectangle(); // TODO see if this works to lessen Sprite rendering
	/**
	 * Draws all the Characters and moves them if requested
	 * @param move whether to move the Characters
	 */
	private void drawCharacters( boolean move ) {
        for ( Character sprite : sprites )
        {
            if ( move )
                moveSprite( sprite );
            if ( drawSprite( sprite ) ) {
                sprite.drawBars( batch, renderer );
            }
        }
	}
	
	private boolean drawSprite( Sprite sprite ) {
        checkRectangle.setSize( cam.viewportWidth * ZOOM, cam.viewportHeight * ZOOM );
        checkRectangle.setPosition( cam.position.x - cam.viewportWidth * ZOOM / 2, 
                                    cam.position.y - cam.viewportHeight * ZOOM / 2);
        if ( sprite.getBoundingRectangle().overlaps( checkRectangle ) ) {
            sprite.draw( batch );
            return true;
        }
        return false;
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
		c.move( player, entities, System.currentTimeMillis() );
		if ( map.isCollidingWithWall( c ) )
		{
		    int dir = c.getRoundedDirection(); // TODO fix glitchy collision
			c.setPosition( x, y );
			if ( c.getDirection() % 90 != 0 )
			{
			    c.setDirection( dir - 45 );
			    c.translate();
			    if ( map.isCollidingWithWall( c ) )
			    {
			        c.setPosition( x, y );
			        c.setDirection( dir + 45 );
			        c.translate();
			        if ( map.isCollidingWithWall( c ) )
			            c.setPosition( x, y );
			    }
			}
		}
		c.setAnimations(); // be aware of the animation bugs produced by this line
	}

	/**
	 * Draws the number of points the player has at the top left of the game
	 * screen.
	 */
	private void setPointsLabelColor()
	{
		float fontX = cam.position.x - cam.viewportWidth * ZOOM / 2;
		float fontY = cam.position.y + cam.viewportHeight * ZOOM / 2;
		setFontColor( pointsLabel, fontX, fontY );
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
	private void setFontColor( Label label, float fontX, float fontY)
	{
	    label.setColor( map.inPixelBounds( fontX, fontY ) ? Color.BLACK : Color.WHITE );
	}
	
	/**
	 * @see com.badlogic.gdx.ScreenAdapter#pause()
	 */
	@Override
	public void pause() { // TODO note: these methods will affect game when going in and out of focus
        Audio.pauseTheme(); // pause the theme music
        state = State.PAUSE;
        background.setVisible( true );
        settingsButton.setDisabled( false );
        settingsButton.setVisible( true );
        backButton.setDisabled( false );
        backButton.setVisible( true );
        
        pointsLabel.setVisible( false );
        controller.pause();
	}
	
	/**
	 * @see com.badlogic.gdx.ScreenAdapter#resume()
	 */
	@Override
	public void resume() {
        state = State.RESUME;
        timeResumed = System.currentTimeMillis();
        background.setVisible( false );
        settingsButton.setDisabled( true );
        settingsButton.setVisible( false );
        backButton.setDisabled( true );
        backButton.setVisible( false );
        
        pointsLabel.setVisible( true );
        resumeLabel.setVisible( true );
        controller.resume();
	}

    private void run()
    {
        state = State.RUN;
        Audio.playTheme();
        resumeLabel.setVisible( false );
    }
	
	/**
	 * Toggles the game state based on what it is
	 */
	public void toggleGameState() {
	    switch ( state )
	    {
	        case RUN:
	            pause();
	            break;
	        case PAUSE:
	            resume();
	            break;
	        case RESUME:
	            run();
	            break;
	    }
	}

    /**
     * Resizes the screen according to both the resolution and any resize 
     * changes
     * 
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize( int width, int height ) {
        cam.setToOrtho( false, Malice.GAME_WIDTH, Malice.GAME_HEIGHT );
        super.resize( width, height );
    }

	/**
	 * Removes the Map, SpriteBatch, and Font to prevent memory leakage.
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
	    super.dispose();
		map.dispose();
		renderer.dispose();
		if ( player.getTexture() != null )
		    player.getTexture().dispose();
        for ( Entity e : entities )
            e.getTexture().dispose();
        for ( Character e : sprites )
            e.getTexture().dispose();
	}

}
