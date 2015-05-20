package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.Splash;

public class Malice extends Game
{
	public static final String TITLE = "Project Malice", VERSION = "0.0.0.9";

	@Override
	public void create()
	{
		setScreen( new Splash( this ) );
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize( width, height );
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}
}