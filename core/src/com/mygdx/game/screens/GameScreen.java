package com.mygdx.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

	private Map map;
	private Texture pauseScreen;
	private Sprite pauseSprite;
	private OrthographicCamera cam;
    private ShapeRenderer renderer;
	private Player player;
    private ArrayList<Character> sprites;
	private ArrayList<Projectile> projectiles;
	
	public enum State
	{
		PAUSE,
	    RUN,
	    RESUME,
	    STOPPED
	}
	
	private State state = State.RUN;
	private int enemyMaxCount = -2;
	private int enemyMinCount = 10;
	private int numEnemies = 3;

	Music music;

	public GameScreen(Malice g, Music m) {
		new Stage();
		game = g;
		music = m;
		music.setVolume( 0.4f );
	}

	@Override
	public void show() {
        projectiles = new ArrayList<Projectile>();
        renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		batchPause = new SpriteBatch();
		pauseScreen = new Texture( "img/pausescreen.png" );
		pauseSprite = new Sprite(pauseScreen);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 960, 720);
        
        map = new Map(50, 50);
        map.generate(Map.DUNGEON);
        
        //initializes enemies and puts in a random amount of enemies
        sprites = new ArrayList<Character>();
        player = new Player("img/sprites/RedMage/RedMage.atlas");
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
			break;
		default:
			break;
		}
	}
	
	public void renderPaused(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batchPause.begin();
		pauseSprite.draw( batchPause );
		batchPause.end();
		if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ))
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
            sprite.drawHp( renderer );
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
                    }
                    break;
                }
            }
            if ( hasHit || map.isCollidingWithWall( projectile ) ) {
                projectiles.remove( i );
                i--;
            }
        }
        
        batch.end();
        renderer.end();
        
        if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ))
        {
        	state = State.PAUSE;
        	// game.setScreen( new PauseMenu(game, music, this) );
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
            e.increaseBdmg( -1 );
            // set spawn for enemy
            map.setSpawn();
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
	}
	
	public State getState()
	{
		return state;
	}

}
