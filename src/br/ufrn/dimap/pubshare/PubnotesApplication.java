package br.ufrn.dimap.pubshare;

import android.app.Application;
import br.ufrn.dimap.pubshare.domain.User;

public class PubnotesApplication extends Application{
	
	private User currentUser;

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
