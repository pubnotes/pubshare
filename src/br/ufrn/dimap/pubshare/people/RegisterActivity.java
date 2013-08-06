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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.restclient.UserResult;
import br.ufrn.dimap.pubshare.util.Constants;

public class RegisterActivity extends PubnotesActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		findViewById(R.id.btnRegister).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						
						//Aqui devo pegar as informacoes, criar um User 
						
						EditText nameText = (EditText) findViewById(R.id.reg_fullname);
						EditText mailText = (EditText) findViewById(R.id.reg_email);
						EditText passwordText = (EditText) findViewById(R.id.reg_password);
						
						//User estah se registrando, ainda com profile vazio, tags e amigos vazios
						
						String name = nameText.getText().toString();
						User user = new User();
						user.setUsername(name);
						user.setUseremail(mailText.getText().toString());
						user.setPassword(passwordText.getText().toString());
						

						/** creating the new asyncTask here **/
						AsyncTask<User, Void, UserResult> async = new AsyncTask<User, Void, UserResult>(){
							ProgressDialog dialog;
							
							protected void onPreExecute() {
								dialog = new ProgressDialog(RegisterActivity.this);
								super.onPreExecute();
								dialog.setMessage("Registering account...");
								dialog.show();
							}
							
							protected UserResult doInBackground(User... user) {
								return registerUser(user[0]);
								
							}
							
							/** now lets update the interface **/
							protected void onPostExecute(UserResult result) {
								if(dialog.isShowing())
								{
									dialog.dismiss();
								}
								Toast.makeText(RegisterActivity	.this,
									"Account registered successfully!", Toast.LENGTH_LONG).show();
								Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
								i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				                startActivity(i);
							}
						};
						
						//e mandar pra o servidor
						async.execute(user);
					}
				}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	/**
	 * The logic of registering an user is here
	 * @param user
	 * @return
	 */
	private UserResult registerUser(User user)
	{
		try {
			HttpHeaders requestHeaders = new HttpHeaders(); 
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			 
			RestTemplate restTemplate = new RestTemplate();
 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			
			String url = "/user/register";
			
			ResponseEntity<UserResult> response = restTemplate.exchange(  
					Constants.URL_SERVER + url, 
					HttpMethod.POST, 
					new HttpEntity<Object>(user, requestHeaders), UserResult.class);
			
			Log.d("SaveEvaluationRestClient", "ResponseEntity " + response.getBody() );
			return response.getBody();
			
 		} catch (HttpClientErrorException e) {		
 			Log.d("SaveEvaluationRestClient", "Erro ao tentar editar avaliação: " + e.getMessage() );
 			e.printStackTrace();
			return new UserResult();
		}
	}

}
