package com.mygdx.game.Map;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
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
   private Texture dropImage;
   private Texture spriteImage;
   // private Sound sound;
   // private Music music;
   private SpriteBatch batch;
   private OrthographicCamera camera;
   private Sprite sprite;
   private Map map;
//   private long lastDropTime;

   @Override
   public void create() {
      // load the images for the droplet and the bucket, 64x64 pixels each
      dropImage = new Texture("droplet.png");
      spriteImage = new Texture("Mine.png");

      // load the drop sound effect and the rain background "music"
//      sound = Gdx.audio.newSound(Gdx.files.internal("*.wav"));
//      music = Gdx.audio.newMusic(Gdx.files.internal("*.mp3"));

      // start the playback of the background music immediately
//      music.setLooping(true);
//      music.play();
      map = new Map();
      map.generate();
      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(true, 1000, 700);
      batch = new SpriteBatch();

      // Sprite
      sprite = new Sprite( spriteImage );
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

      // begin a new batch and draw the bucket and
      // all drops
      batch.begin();
      map.draw( batch );
      sprite.draw( batch );
//      batch.draw(spriteImage, sprite.getX(), sprite.getY() );
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
      dropImage.dispose();
      spriteImage.dispose();
//      sound.dispose();
//      music.dispose();
      batch.dispose();
      map.dispose();
   }

//   @Override
//   public void resize(int width, int height) {
//   }
//
//   @Override
//   public void pause() {
//   }
//
//   @Override
//   public void resume() {
//   }
}
