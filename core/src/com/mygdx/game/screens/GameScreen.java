package com.mygdx.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Malice;
import com.mygdx.game.player.Character;
import com.mygdx.game.player.Enemy;
import com.mygdx.game.player.Player;
import com.mygdx.game.projectile.Projectile;
import com.mygdx.game.world.Map;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private SpriteBatch batchPause;

	private final Malice game;

	
	public final static int MAP_SIZE = 50;
	private Map map;
	private Texture pauseScreen;
	private Sprite pauseSprite;
	private OrthographicCamera cam;
    private ShapeRenderer renderer;
	private Player player;
    private ArrayList<Character> sprites;
	private ArrayList<Projectile> projectiles;
	private BitmapFont font;
	private String playerType;
	private String[] spriteNames = {"BlackMage", "Monk", "RedMage", "Thief", "Warrior", "WhiteMage"};
	private String[] projectileNames = {"DarkFire", "Boomerang", "Fireball", "PoisonShot", "Sword1", "HolyCross"};
	
	public enum State
	{
		PAUSE,
	    RUN,
	    RESUME,
	    STOPPED
	}
	
	private State state;
	private int enemyMaxCount;
	private int enemyMinCount;
	private int numEnemies;
	private long timeResumed;

	Music music;

	public GameScreen(Malice g, Music m, String playerType) {
		new Stage();
		game = g;
		music = m;
		music.setVolume( 0.4f );
		this.playerType = playerType;
	}

	@Override
	public void show() {
        projectiles = new ArrayList<Projectile>();
        renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		batchPause = new SpriteBatch();
		pauseScreen = new Texture( "img/pausescreen.png" );
		pauseSprite = new Sprite(pauseScreen);
		timeResumed = System.currentTimeMillis();
		state = State.RESUME;
		enemyMaxCount = -2;
		enemyMinCount = 10;
		numEnemies = 3;
		font = new BitmapFont();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 960, 720);
        
        map = new Map( MAP_SIZE, MAP_SIZE );
        map.generate(Map.RANDOM);
        
        //initializes enemies and puts in a random amount of enemies
        sprites = new ArrayList<Character>();
        CharacterSelect temp = new CharacterSelect(null, null);
        String[] charNames = temp.getNames();
        String spriteName = "BlackMage";
        String projectileName = "DarkFire";
        for (int i = 0; i < charNames.length; i++)
        {
        	if (charNames[i].equals( playerType ))
        	{
        		spriteName = spriteNames[i];
        		projectileName = projectileNames[i];
        	}
        }
        player = new Player("img/sprites/Players/" + spriteName + "/" + spriteName + ".atlas", projectileName);
        player.setPosition(map.getSpawnX(), map.getSpawnY());
        sprites.add( player );
        
        spawnEnemies();
	}

	
	
	@Override
	public void render(float delta) {
		switch (state)
		{
		case RUN:
			renderRun(delta);
			break;
		case PAUSE:
			renderPaused(delta);
			break;
		case RESUME:
			renderResume(delta);
			break;
		default:
			break;
		}
	}
	
	public void renderPaused(float delta)
	{
		music.pause();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batchPause.begin();
		pauseSprite.draw( batchPause );
		batchPause.end();
		if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ))
        {
        	state = State.RESUME;
        	timeResumed = System.currentTimeMillis();
        }
	}
	
	// identical to renderRun except nothing moves and thus the screen is static
	public void renderResume(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		cam.position.x = player.getX();
		cam.position.y = player.getY();
		cam.update();
		
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix( cam.combined );

        batch.begin();
        renderer.begin( ShapeType.Filled );
        map.draw(batch);
        
        if (sprites.size() < 3)
        {
        	spawnEnemies();
        }
        
        for (Character sprite : sprites) {
        	sprite.draw( batch );
            sprite.drawBars( batch, renderer );
        }
        for ( Projectile projectile : projectiles ) {
            projectile.draw(batch);
        }
        float fontX = cam.position.x - 100;
        float fontY = cam.position.y + cam.viewportHeight / 3;
        drawPoints();
        setFontColor( fontX, fontY );
        font.draw( batch, "Game resumes in " + (2000 - System.currentTimeMillis() + timeResumed) + " milliseconds", fontX, fontY );
        batch.end();
        renderer.end();
        
        if (System.currentTimeMillis() - timeResumed > 2000)
        {
        	state = State.RUN;
        }
	}

	public void renderRun(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		cam.position.x = player.getX();
		cam.position.y = player.getY();
		cam.update();
		
		if (!music.isPlaying())
		{
			music.play();
		}

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix( cam.combined );

        batch.begin();
        renderer.begin( ShapeType.Filled );
        map.draw(batch);
        
        if (sprites.size() < 3)
        {
        	spawnEnemies();
        }
        
        for (Character sprite : sprites) {
            moveSprite(sprite);
            sprite.drawBars( batch, renderer );
        }
        for ( int i = 0; i < projectiles.size(); i++ ) {
            Projectile projectile = projectiles.get( i );
            projectile.move();
            projectile.draw(batch);
            
            boolean hasHit = false;
            for (Character sprite : sprites) {
                if ( hasHit || projectile.hitCharacter( sprite ) )
                {
                    hasHit = true;
                    sprite.takeDamage( projectile.getDamage() );
                    if (sprite.isDead())
                    {
                    	sprites.remove( sprite );
                    	if (sprite instanceof Enemy)
                    	{
                    		player.increasePoints();
                    		player.increaseExp( sprite.getExperience() );
                    		for (int j = 1; j < player.getPoints(); j *= 60)
                    		{
                    			int index = 1 + (int) (Math.random() * numEnemies);
                                if (index == numEnemies + 1) index--;
                                Enemy e = new Enemy("img/sprites/Enemies/Enemy" + index
                                    + "/Enemy" + index + ".atlas");
                                e.increaseBdmg( -5 );
                                // set spawn for enemy
                                map.setSpawn(player.getX(), player.getY());
                                e.setPosition(map.getSpawnX(), map.getSpawnY());
                                sprites.add(e);
                    		}
                    	}
                    	else if (sprite instanceof Player )
                    	{
                    		music.stop();
                    		game.setScreen( new GameOver(game, music, player, playerType) );
                    	}
                    }
                    break;
                }
            }
            if ( hasHit || map.isCollidingWithWall( projectile ) ) {
                projectiles.remove( i );
                i--;
            }
        }
        drawPoints();
        batch.end();
        renderer.end();
        
        if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ))
        {
        	state = State.PAUSE;
        }
	}
	
	private void spawnEnemies()
	{
		int enemyCount = enemyMinCount + (int)(Math.random()*enemyMaxCount);
        for (int i = 0; i < enemyCount; i++){
            int index = 1 + (int) (Math.random() * numEnemies);
            if (index == numEnemies + 1) index--;
            Enemy e = new Enemy("img/sprites/Enemies/Enemy" + index
                + "/Enemy" + index + ".atlas");
            e.increaseBdmg( -5 );
            // set spawn for enemy
            map.setSpawn(player.getX(), player.getY());
            e.setPosition(map.getSpawnX(), map.getSpawnY());
            sprites.add(e);
        }
	}
    
    private void moveSprite( Character c )
    {
        float x = c.getX();
        float y = c.getY();
        c.move(player, projectiles, System.currentTimeMillis());
        if (map.isCollidingWithWall(c)) {
            c.setPosition(x, y);
        }
        c.draw(batch);
    }
    
    private void drawPoints()
    {
        float fontX = cam.position.x - cam.viewportWidth / 2;
        float fontY = cam.position.y + cam.viewportHeight / 2;
        setFontColor( fontX, fontY );
        font.draw( batch, "POINTS: " + player.getPoints(), fontX, fontY );
    }
    
    private void setFontColor( float fontX, float fontY )
    {
        if ( map.inPixelBounds( fontX, fontY ) )
        {
            font.setColor( Color.BLACK );
        }
        else
        {
            font.setColor( Color.WHITE );
        }
    }
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		batch.dispose();
		font.dispose();
	}
	
	public State getState()
	{
		return state;
	}

}
