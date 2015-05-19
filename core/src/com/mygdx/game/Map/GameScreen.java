package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Malice;

public class GameScreen extends ScreenAdapter
{
    Malice game;
    
    private Map map;
    private OrthographicCamera cam;
    private Sprite player;
    
//    private Body b;
    
    public GameScreen( Malice m )
    {
        this( m, Map.ARENA );
    }
    
    public GameScreen( Malice m, int generation )
    {
        game = m;
        
        cam = new OrthographicCamera();
        cam.setToOrtho( true, 960, 720 );
        
        map = new Map( 25, 25 );
        map.generate( generation );
        
        player = new Sprite( new Texture( "map/Mine.png" ) );
        player.setBounds( map.getSpawnX(), map.getSpawnY(), 50, 50 );
//        player.setX( map.getSpawnX() );
//        player.setY( map.getSpawnY() );
        

//        World world = new World(null, false);
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set((player.getX() + player.getWidth()/2) /
//                        Map.PIXELS_TO_METERS,
//                (player.getY() + player.getHeight()/2) / Map.PIXELS_TO_METERS);
//        b = world.createBody( bodyDef );
    }
    
    @Override
    public void render( float delta )
    {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
//      SpriteBatch batch = game.getBatch();
        SpriteBatch batch = new SpriteBatch();
        
        
        cam.position.x = player.getX();
        cam.position.y = player.getY();
        cam.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(cam.combined);
        
        batch.begin();
        map.draw( batch );
        player.draw( batch );
        batch.end();

        // process user input
        int speed = 400;
        
        float x = player.getX();
        if(Gdx.input.isKeyPressed(Keys.LEFT)) player.translateX( -speed * Gdx.graphics.getDeltaTime() );
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) player.translateX( speed * Gdx.graphics.getDeltaTime() );
        if ( map.isCollidingWithWall( player )) player.setX( x );
        
        float y = player.getY();
        if(Gdx.input.isKeyPressed(Keys.UP)) player.translateY( -speed * Gdx.graphics.getDeltaTime() );
        if(Gdx.input.isKeyPressed(Keys.DOWN)) player.translateY( speed * Gdx.graphics.getDeltaTime() );
        if ( map.isCollidingWithWall( player )) player.setX( y );

        // make sure the bucket stays within the screen bounds
//        if(player.getX() < map.getX( 1 )) player.setX( map.getX( 1 ) );
//        if(sprite.getX() > 800 - sprite.getWidth()) sprite.setX( 800 - sprite.getWidth() );
//        if(player.getY() < map.getY( 1 )) player.setY( map.getY( 1 ) );
//        if(sprite.getY() > 800 - sprite.getHeight()) sprite.setY( 800 - sprite.getHeight() );
    }
    
    @Override
    public void dispose()
    {
        player.getTexture().dispose();
        map.dispose();
    }
}
