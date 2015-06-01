package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.Malice;


/**
 *  Class runs the game on a browser
 *
 *  @author  badlogic (Libgdx)
 *  @version Jun 1, 2015
 *  @author  Assignment: my-gdx-game-html
 *
 *  @author  Sources: Libgdx
 */
public class HtmlLauncher extends GwtApplication
{

    @Override
    public GwtApplicationConfiguration getConfig()
    {
        return new GwtApplicationConfiguration( 480, 320 );
    }


    @Override
    public ApplicationListener getApplicationListener()
    {
        return new Malice();
    }
}