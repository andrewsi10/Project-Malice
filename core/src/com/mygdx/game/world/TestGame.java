package com.mygdx.game.world;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestGame implements ApplicationListener {
   private Texture dropImage;
   private Texture spriteImage;
   // private Sound sound;
   // private Music music;
   private SpriteBatch batch;
   private OrthographicCamera camera;
   private Sprite sprite;
   private TextureRegion background;
   private Texture[][] tiles = new Texture[25][25];
//   private Array<Rectangle> raindrops;
//   private long lastDropTime;

   @Override
   public void create() {
      // load the images for the droplet and the bucket, 64x64 pixels each
       Texture t = new Texture(Gdx.files.internal("background.png"));
      dropImage = new Texture(Gdx.files.internal("droplet.png"));
      spriteImage = new Texture(Gdx.files.internal("Mine.png"));

      // load the drop sound effect and the rain background "music"
//      sound = Gdx.audio.newSound(Gdx.files.internal("*.wav"));
//      music = Gdx.audio.newMusic(Gdx.files.internal("*.mp3"));

      // start the playback of the background music immediately
//      music.setLooping(true);
//      music.play();
      background = new TextureRegion(t, 0, 0);
      background.setRegionHeight( 64 );
      background.setRegionWidth( 64 );

      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(true, 1000, 700);
      batch = new SpriteBatch();

      // Sprite
      sprite = new Sprite( spriteImage );
      sprite.setCenterX( 800 / 2 ); // center the sprite horizontally
      sprite.setCenterY( camera.position.y );
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
      camera.update();

      // tell the SpriteBatch to render in the
      // coordinate system specified by the camera.
      batch.setProjectionMatrix(camera.combined);

      // begin a new batch and draw the bucket and
      // all drops
      batch.begin();
//      batch.draw( background, 0, 0, 1000, 700);
      for ( int i = 0; i < tiles.length; i++ )
      {
          for ( int j = 0; j < tiles[i].length; j++ )
          {
              batch.draw( background, i*background.getRegionWidth(), j*background.getRegionHeight() );
          }
      }
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
      if(sprite.getX() > 800 - sprite.getWidth()) sprite.setX( 800 - sprite.getWidth() );
      if(sprite.getY() < 0) sprite.setY( 0 );
      if(sprite.getY() > 800 - sprite.getHeight()) sprite.setY( 800 - sprite.getHeight() );
   }

   @Override
   public void dispose() {
      // dispose of all the native resources
      dropImage.dispose();
      spriteImage.dispose();
//      sound.dispose();
//      music.dispose();
      batch.dispose();
   }

   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }
}
