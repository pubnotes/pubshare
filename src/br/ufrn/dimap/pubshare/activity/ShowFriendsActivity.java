package br.ufrn.dimap.pubshare.activity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufrn.dimap.pubshare.adapters.FriendsListAdapter;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import br.ufrn.dimap.pubshare.restclient.results.UserResult;
import br.ufrn.dimap.pubshare.util.Constants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class ShowFriendsActivity extends PubnotesActivity {

	private ListView usersListView;
	private FriendsListAdapter adapter;
	
	AsyncTask<User, Void, User[]> async; 
	AsyncTask<User, Void, UserResult> async2;
	ImageButton search;
	User userlogado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_friends);
		
		//Aqui deve pegar o usuario logado (username) e buscar no servidor
		//a lista de amigos (User) dele
		//e adicionar a listview

		final List<User> users1 = UserMockFactory.makeUserList();
		userlogado = ShowFriendsActivity.this.getCurrentUser();
		
		/** getting users from the server using the async task**/
		async = new AsyncTask<User, Void, User[]>(){
			
			
			protected void onPreExecute() {
				super.onPreExecute();
			}
			
			protected User[] doInBackground(User... users) {
					return getFriends(users[0]);
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(User[] result) {
				configureListView(Arrays.asList(result));
			}
		};
		
		async.execute(userlogado);
	}
	
	
	private void configureListView(List<User> users) {
		adapter = new FriendsListAdapter(this, R.layout.row_friends_list , users);
		
		usersListView = (ListView) findViewById(R.id.list_view_friends_detail);
		if ( usersListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.layout.row_listview_article_list");
		}
		usersListView.setAdapter( adapter );
		//Aqui possivelmente virah o codigo do click no botao de add+
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_friends, menu);
		return true;
	}
	
	private User[] getFriends(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/getFriends";
		
		ResponseEntity<User[]> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), User[].class);
		
		
		User[] result = response.getBody();
				
		return result;
	}
	

}
