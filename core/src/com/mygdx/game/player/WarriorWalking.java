package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WarriorWalking implements Screen {
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("img/sprites/WarriorWalking/WarriorWalking.png"));
		sprite = new Sprite(texture);
		sprite.setPosition(w/2 - sprite.getWidth()/2, h/2 - sprite.getHeight()/2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            sprite.translateX(-5f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            sprite.translateX(5f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
        	sprite.translateY(-5f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
        	sprite.translateY(5f);
        }
        batch.begin();
        sprite.draw(batch);
        batch.end();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
}
