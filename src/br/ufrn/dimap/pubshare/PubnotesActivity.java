/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    PubShare is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PubShare.  If not, see <http://www.gnu.org/licenses/>.
 */

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
