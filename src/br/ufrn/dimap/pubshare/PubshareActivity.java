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
import android.content.Intent;
import android.os.Bundle;
import br.ufrn.dimap.pubshare.people.LoginActivity;

/**
 * Entry point of application. It can be used to perform some processing, 
 * redirecting to some activity by some logic.
 *
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
public class PubshareActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//When you develop, change this line to start your activity. (Dev Only)
		Intent i = new Intent(this, LoginActivity.class);        	        
				    
		startActivity(i);
		finish();
	}
}
