package br.ufrn.dimap.pubshare.activity;

import br.ufrn.dimap.pubshare.PubnotesApplication;
import br.ufrn.dimap.pubshare.domain.User;
import android.app.Activity;

public class PubnotesActivity extends Activity{
	
	public User getCurrentUser()
	{
		PubnotesApplication app = (PubnotesApplication) getApplicationContext();
		return app.getCurrentUser();
	}
	
	public void setCurrentUser(User user)
	{
		PubnotesApplication app = (PubnotesApplication) getApplicationContext();
		app.setCurrentUser(user);
	}

}
