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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Malice;
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
	private Player player;
	private ArrayList<Enemy> enemies;
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
		player = new Player("img/sprites/RedMage/RedMage.atlas");
		batch = new SpriteBatch();
		batchPause = new SpriteBatch();
		pauseScreen = new Texture( "img/pausescreen.png" );
		pauseSprite = new Sprite(pauseScreen);
		
		//initializes enemies and puts in a random amount of enemies
		enemies = new ArrayList<Enemy>();
		int enemyCount = enemyMinCount + (int)(Math.random()*enemyMaxCount);
		for (int i = 0; i < enemyCount; i++){
			int index = 1 + (int) (Math.random() * numEnemies);
			if (index == numEnemies + 1) index--;
			enemies.add(new Enemy("img/sprites/Enemies/Enemy" + index
					+ "/Enemy" + index + ".atlas"));
		}

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 960, 720);

		map = new Map(50, 50);
		map.generate(Map.DUNGEON);

		// player.setBounds( map.getSpawnX(), map.getSpawnY(), 60, 60 );
		player.setPosition(map.getSpawnX(), map.getSpawnY());
		
		for (Enemy e : enemies) {
			map.setSpawn();
			e.setPosition(map.getSpawnX(), map.getSpawnY());
		}
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

		batch.begin();
        map.draw(batch);
        
        float x = player.getX();
        float y = player.getY();
//        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
//            player.strafe();
//        } else {
        player.move();
//        }
        if (map.isCollidingWithWall(player))
            player.setPosition(x, y);
        player.draw(batch);
        
        if (Gdx.input.isButtonPressed( Input.Buttons.LEFT ))
        {
            player.shoot(projectiles, 
                Gdx.input.getX() - Gdx.graphics.getWidth() / 2, 
                Gdx.graphics.getHeight() / 2 - Gdx.input.getY(), 
                System.currentTimeMillis());
        }
        
        for (Enemy enemy : enemies) {
            
            x = enemy.getX();
            y = enemy.getY();
            enemy.move(player, projectiles, System.currentTimeMillis());
            if (map.isCollidingWithWall(enemy)) {
                enemy.setPosition(x, y);
            }
            enemy.draw(batch);
        }
        for ( int i = 0; i < projectiles.size(); i++ ) {
            Projectile projectile = projectiles.get( i );
            projectile.move();
            projectile.draw(batch);
            x = projectile.getX();
            y = projectile.getY();

            boolean hasHit = false;
            if ( projectile.hitCharacter( player ) )
                hasHit = true;
            else
            for (Enemy enemy : enemies) {
                if ( hasHit || projectile.hitCharacter( enemy ) )
                     hasHit = true;
            }
            if ( hasHit || map.isCollidingWithWall( projectile ) ) {
                projectiles.remove( i );
                i--;
            }
        }
        
        batch.end();
        
        if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE ))
        {
        	state = State.PAUSE;
        	// game.setScreen( new PauseMenu(game, music, this) );
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
	}
	
	public State getState()
	{
		return state;
	}

}
