package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Malice;
import com.mygdx.game.player.WarriorWalking;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Malice.TITLE + " v" + Malice.VERSION;
		config.vSyncEnabled = true;
		config.width = 1600;
		config.height = 900;
		new LwjglApplication( new WarriorWalking()/*Malice()*/, config );
	}
}
