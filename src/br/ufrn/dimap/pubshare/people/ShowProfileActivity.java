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

package br.ufrn.dimap.pubshare.people;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.MenuActivity;
import br.ufrn.dimap.pubshare.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Profile;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.util.Constants;

public class ShowProfileActivity extends PubnotesActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_profile);
		
		final TextView institution = (TextView) findViewById(R.id.textinstitution);
		final TextView degree = (TextView) findViewById(R.id.textdegree);
		final TextView city = (TextView) findViewById(R.id.textcountry);
		final TextView gender = (TextView) findViewById(R.id.textgender);
		
		final TextView birthday = (TextView) findViewById(R.id.textbirthday);
		final TextView aboutme = (TextView) findViewById(R.id.textaboutme);
		
		final TextView facebook = (TextView) findViewById(R.id.textfacebook);
		final TextView email = (TextView) findViewById(R.id.textemail);
		final TextView phone = (TextView) findViewById(R.id.textphone);
		
		
		//Aqui eu devo pegar a ref do User, fazer uma busca no banco
		//pegar o Profile do user e setar os campos da activity
		User userlogado = ShowProfileActivity.this.getCurrentUser(); 
		TextView utext = (TextView) findViewById(R.id.usernametext);
		final TextView atext = (TextView) findViewById(R.id.aboutmeusertext);
		utext.setText(userlogado.getUsername());
		atext.setText(userlogado.getUserprofile().getAboutme());
		
		email.setText(userlogado.getUseremail());
		
		Button btnMenu = (Button) findViewById(R.id.btnMainMenu);
				btnMenu.setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent i = new Intent(ShowProfileActivity.this, MenuActivity.class);
								i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(i);
							}
						});
				
				/** creating the new asyncTask here **/
				AsyncTask<User, Void, Profile> async = new AsyncTask<User, Void, Profile>(){
					
					
					protected void onPreExecute() {
						//dialog = new ProgressDialog(ShowProfileActivity.this);
						super.onPreExecute();
						//dialog.setMessage("Loading profile...");
						//dialog.show();
					}
					
					protected Profile doInBackground(User... user) {
						return getProfile(user[0]);
						
					}
					
					/** now lets update the interface **/
					protected void onPostExecute(Profile profile) {
						/*if(dialog.isShowing())
						{
							dialog.dismiss();
						}*/
						
						if (profile.getId() != 0) {
							atext.setText(profile.getAboutme());
							
							institution.setText(profile.getInstitution());
							degree.setText(profile.getDegree());
							city.setText(profile.getLocation());
							gender.setText(profile.getGender());
							
							birthday.setText(profile.getBirthday());
							aboutme.setText(profile.getAboutme());
							
							facebook.setText(profile.getFacebook());
							//email.setText(profile.getEmail());
							phone.setText(profile.getPhone());
						}
					}
				};		
				
				async.execute(userlogado);
				
				
				//alterado aqui
				startService(new Intent(this, ServiceController.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_profile, menu);
		return true;
	}
	
	private Profile getProfile(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/getProfile";
		
		ResponseEntity<Profile> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), Profile.class);
		
		
		Profile profile = response.getBody();
				
		return profile;
	}

}
