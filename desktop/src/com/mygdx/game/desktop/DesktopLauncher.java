package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Malice;

/**
 *  Class runs the Game on a desktop computer
 *
 *  @author  Andrew Si
 *  @version Jun 1, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-desktop
 *
 *  @author  Sources: Libgdx
 */
public class DesktopLauncher
{
	/**
	 * Runs the game on a desktop computer
	 * @param arg command line args -- not used
	 */
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
