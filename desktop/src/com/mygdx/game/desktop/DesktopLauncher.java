package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Malice;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Malice.TITLE + " v" + Malice.VERSION;
		config.vSyncEnabled = true;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication( new Malice(), config );
	}
}
