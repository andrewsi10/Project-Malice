package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.Malice;
import com.mygdx.game.services.DesktopGoogleServices;
import com.mygdx.game.services.IGoogleServices;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {
//    private GameHelper _gameHelper;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		// Create the GameHelper.
//		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
//		_gameHelper.enableDebugLog(false);
//
//		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
//		{
//		@Override
//		public void onSignInSucceeded()
//		{
//		}
//
//		@Override
//		public void onSignInFailed()
//		{
//		}
//		};
//
//		_gameHelper.setup(gameHelperListener);
//		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new Malice( this ), config);
        initialize(new Malice( new DesktopGoogleServices() ), config);
	}

    @Override
    public void signIn()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void signOut()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void rateGame()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void submitScore( long score )
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void showScores()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isSignedIn()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
//    @Override
//    protected void onStart()
//    {
//    super.onStart();
//    _gameHelper.onStart(this);
//    }
//
//    @Override
//    protected void onStop()
//    {
//    super.onStop();
//    _gameHelper.onStop();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//    super.onActivityResult(requestCode, resultCode, data);
//    _gameHelper.onActivityResult(requestCode, resultCode, data);
//    }
}
