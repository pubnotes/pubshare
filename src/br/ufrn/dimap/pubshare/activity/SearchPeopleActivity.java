package br.ufrn.dimap.pubshare.activity;

import java.util.ArrayList;
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

import br.ufrn.dimap.pubshare.PubnotesApplication;
import br.ufrn.dimap.pubshare.adapters.ArticleListAdapter;
import br.ufrn.dimap.pubshare.adapters.FriendsListAdapter;
import br.ufrn.dimap.pubshare.adapters.UserListAdapter;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.Tag;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.evaluation.ArticleDetailActivity;
import br.ufrn.dimap.pubshare.evaluation.ArticleEvaluationDetailActivity;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import br.ufrn.dimap.pubshare.restclient.results.UserResult;
import br.ufrn.dimap.pubshare.util.Constants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchPeopleActivity extends PubnotesActivity {

	private ListView usersListView;
	private UserListAdapter adapter;
	AsyncTask<Void, Void, User[]> async; 
	AsyncTask<User, Void, UserResult> async2;
	EditText searchpeople;
	ImageButton search;
	List<User> users;
	User userlogado;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_people);
		
		searchpeople = (EditText) findViewById(R.id.searchpeopletext);
		search = (ImageButton) findViewById(R.id.imageButtonsearchpeople);
		//A partir da entrada fornecida no campo de busca
		//quando clicar no botao de busca, fazer busca no servidor
		//deve pegar os usuarios (username, aboutme) do servidor numa lista
		//e adicionar a listview
		users = new ArrayList<User>();
		userlogado = SearchPeopleActivity.this.getCurrentUser();
		
		
		/** done **/
		
		search.setOnClickListener(
				new View.OnClickListener() 
				{
					public void onClick(View view) {
						/** getting users from the server using the async task**/
						async = new AsyncTask<Void, Void, User[]>(){
							
							
							protected void onPreExecute() {
								super.onPreExecute();
							}
							
							protected User[] doInBackground(Void... params) {
									return getPeople(searchpeople.getText().toString());
							}
							
							/** now lets update the interface **/
							protected void onPostExecute(User[] result) {
								if(result.length != 0){
										//for (int i = 0; i < result.length; i++) {
										if(contains(users, result[0].getUsername()) == false)	
											users.add(result[0]);
										else{
												Toast.makeText(SearchPeopleActivity.this,
														"The user has been fetched!", Toast.LENGTH_LONG).show();
										}
										configureListView(Arrays.asList(result));
									//}
								}else{
									Toast.makeText(SearchPeopleActivity.this,
											"There is no user with that username!", Toast.LENGTH_LONG).show();	
								}
							}
						};
						
						async.execute((Void) null);
					}
				});
		
	}
	
	private static boolean contains(List<User> users, String name){
		boolean contain = false;
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(name))
				contain = true;
		}
		return contain;
	}
	
	private OnItemClickListener onItemClickAddFriend = new OnItemClickListener()
	{
		public void onItemClick(AdapterView adapter, View v, int position, long id) 
		{
			View view = v;
			if(view == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				inflater.inflate(R.layout.row_listview_people_list, null);
			}
			
			User userfriend = (User) adapter.getItemAtPosition(position);
			userlogado = SearchPeopleActivity.this.getCurrentUser();
			if(userfriend.getUsername().equals(userlogado.getUsername())){
				Toast.makeText(SearchPeopleActivity.this,
						"User logged on this device!", Toast.LENGTH_LONG).show();
		
			}else{
				
				if(contains(userlogado.getFriends(), userfriend.getUsername()) == false)	{
					userlogado.getFriends().add(userfriend);
					SearchPeopleActivity.this.setCurrentUser(userlogado);
					Log.d("AMIGOS SEARCCH", "tamanho " + userlogado.getFriends().size());
					
					async2 = new AsyncTask<User, Void, UserResult>(){
						
						
						protected void onPreExecute() {
							super.onPreExecute();
						}
						
						protected UserResult doInBackground(User... user) {
							return addFriends(user[0]);
							
						}
						/** now lets update the interface **/
						protected void onPostExecute(UserResult result) {
							Toast.makeText(SearchPeopleActivity.this,
									"Friend added!", Toast.LENGTH_LONG).show();
						}
					};		
					
					async2.execute(userlogado);
				}else{
					Toast.makeText(SearchPeopleActivity.this,
							userfriend.getUsername() + " is already your friend!", Toast.LENGTH_LONG).show();
				}
			}
					
		}
	};
	
	private void configureListView(List<User> users) {
		adapter = new UserListAdapter(this, R.layout.row_listview_people_list , users);
		
		usersListView = (ListView) findViewById(R.id.list_view_people_detail);
		if ( usersListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.layout.row_listview_article_list");
		}
		usersListView.setAdapter( adapter );
		usersListView.setOnItemClickListener(onItemClickAddFriend);
		//Aqui possivelmente virah o codigo do click no botao de +
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_people, menu);
		return true;
	}

	private User[] getPeople(String text)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		Map<String, String> body = new HashMap<String, String>();     			
		body.put("username", text);
		
		String url = "/user/getPeople?username=" + text.trim();
		
		User[] entity = restTemplate
				.getForObject(Constants.URL_SERVER + url, User[].class);
				
		return entity;
	}
	
	private UserResult addFriends(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/addFriends";
		
		ResponseEntity<UserResult> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), UserResult.class);
		
		
		UserResult result = response.getBody();
				
		return result;
	}
}
