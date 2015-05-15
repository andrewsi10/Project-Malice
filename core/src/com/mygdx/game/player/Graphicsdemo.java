package com.mygdx.game.player;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Graphicsdemo implements ApplicationListener {
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

	@Override
	public void create() {
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("img/sprites/unipotato.jpg"));
		sprite = new Sprite(texture);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}
	
	@Override 
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		sprite.draw(batch);
		batch.end();
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
