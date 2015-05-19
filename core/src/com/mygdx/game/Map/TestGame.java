package com.mygdx.game.map;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *  Current Testing Class for functions: change desktopLauncher to create 
 *  new TestGame() in order to see map results
 *
 *  @author  Nathan Lui
 *  @version May 17, 2015
 *  @author  Period: 4
 *  @author  Assignment: TestGame-core
 *
 *  @author  Sources: 
 */
public class TestGame extends ApplicationAdapter {
   private SpriteBatch batch;
   private OrthographicCamera camera;
   private Sprite sprite;
   private Map map;

   @Override
   public void create() {
      map = new Map( 25, 25 );
      map.generate( Map.ARENA );
      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(true, 960, 720);
      batch = new SpriteBatch();

      // Sprite
      sprite = new Sprite( new Texture("map/Mine.png") );
      sprite.setCenterX( map.getX( 1 ) );
      sprite.setCenterY( map.getY( 1 ) );
   }

   @Override
   public void render() {
      // clear the screen with a dark blue color. The
      // arguments to glClearColor are the red, green
      // blue and alpha component in the range [0,1]
      // of the color to be used to clear the screen.
      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      // tell the camera to update its matrices.
      camera.position.x = sprite.getX();
      camera.position.y = sprite.getY();
      camera.update();

      // tell the SpriteBatch to render in the
      // coordinate system specified by the camera.
      batch.setProjectionMatrix(camera.combined);

      // begin a new batch and draw
      batch.begin();
      map.draw( batch );
      sprite.draw( batch );
      batch.end();

      // process user input
      if(Gdx.input.isKeyPressed(Keys.LEFT)) sprite.translateX( -200 * Gdx.graphics.getDeltaTime() );
      if(Gdx.input.isKeyPressed(Keys.RIGHT)) sprite.translateX( 200 * Gdx.graphics.getDeltaTime() );
      if(Gdx.input.isKeyPressed(Keys.UP)) sprite.translateY( -200 * Gdx.graphics.getDeltaTime() );
      if(Gdx.input.isKeyPressed(Keys.DOWN)) sprite.translateY( 200 * Gdx.graphics.getDeltaTime() );

      // make sure the bucket stays within the screen bounds
      if(sprite.getX() < 0) sprite.setX( 0 );
//      if(sprite.getX() > 800 - sprite.getWidth()) sprite.setX( 800 - sprite.getWidth() );
      if(sprite.getY() < 0) sprite.setY( 0 );
//      if(sprite.getY() > 800 - sprite.getHeight()) sprite.setY( 800 - sprite.getHeight() );
//      if(map.isCollidingWithWall( sprite ))
   }

   @Override
   public void dispose() {
      // dispose of all the native resources
      batch.dispose();
      map.dispose();
   }
}
