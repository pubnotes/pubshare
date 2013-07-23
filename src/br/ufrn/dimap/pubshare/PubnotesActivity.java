package br.ufrn.dimap.pubshare;

import android.app.Activity;
import br.ufrn.dimap.pubshare.domain.User;

public class PubnotesActivity extends Activity{
	
	public User getCurrentUser()
	{
		PubnotesApplication app = (PubnotesApplication) getApplicationContext();
		return app.getCurrentUser();
		
		//return UserMockFactory.makeSingleUser();
	}
	
	public void setCurrentUser(User user)
	{
		PubnotesApplication app = (PubnotesApplication) getApplicationContext();
		app.setCurrentUser(user);
	}

}
