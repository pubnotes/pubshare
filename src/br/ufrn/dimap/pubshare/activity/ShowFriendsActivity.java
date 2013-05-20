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

import br.ufrn.dimap.pubshare.adapters.FriendsListAdapter;
import br.ufrn.dimap.pubshare.domain.Tag;
import br.ufrn.dimap.pubshare.domain.TagUser;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import br.ufrn.dimap.pubshare.restclient.results.UserResult;
import br.ufrn.dimap.pubshare.util.Constants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ShowFriendsActivity extends PubnotesActivity {

	private ListView usersListView;
	private FriendsListAdapter adapter;
	
	AsyncTask<User, Void, User[]> async; 
	AsyncTask<User, Void, UserResult> async2;
	AsyncTask<User, Void, UserResult> async3;
	ImageButton search;
	User userlogado;
	User userfriend;
	Tag tagmarcada;
	private List<Tag> tags;
	private PopupMenu popupMenu;
	
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
	
	private OnItemClickListener onItemClickAddTag = new OnItemClickListener()
	{
		public void onItemClick(AdapterView adapter, View v, int position, long id) 
		{
			final View view = v;
			if(view == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				inflater.inflate(R.layout.row_friends_list, null);
			}
			
			userfriend = (User) adapter.getItemAtPosition(position);
			userlogado = ShowFriendsActivity.this.getCurrentUser();
			
			tags = userlogado.getTags();
			
			//tags = new ArrayList<String>();
			//tags = UserMockFactory.usersTags;
			
			popupMenu = new PopupMenu(ShowFriendsActivity.this, view.findViewById(R.id.addtag));
			popupMenu.getMenu().add(Menu.NONE, tags.size()+ 1, Menu.NONE, "Create New Tag");
			
			for (int i = 0; i < tags.size(); i++) {
				popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, tags.get(i).getDescription());
			}
		
			popupMenu.show();
			
			
	       popupMenu.setOnMenuItemClickListener(
	               new PopupMenu.OnMenuItemClickListener() {
	           @Override
	       		public boolean onMenuItemClick(MenuItem item) {
	    	    	    //LayoutInflater li = LayoutInflater.from(ShowFriendsActivity.this);
	   				    //View promptsView = li.inflate(R.layout.tags, null);
	    	    	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	   					ShowFriendsActivity.this);
	    	    	    
		    	    	if(item.getItemId() ==  tags.size() + 1){    
			   				// set prompts.xml to alertdialog builder
			   				alertDialogBuilder.setView(view);
			    
			   				final EditText userInput = (EditText) view
			   						.findViewById(R.id.editTextDialogUserInput);
			    
			   				// set dialog message
			   				alertDialogBuilder
			   					.setCancelable(false)
			   					.setPositiveButton("OK",
			   					  new DialogInterface.OnClickListener() {
			   					    public void onClick(DialogInterface dialog,int id) {
			   						// get user input and set it to result
			   						// edit text
			   					    	//adicionando nova tag a lista de tags do user
			   					    	String newtag = userInput.getText().toString();
					    	    	    Tag tag = new Tag();
					    	    	    tag.setDescription(newtag);
			   					    	
			   					    	int newid = tags.size() + 1;
			   					    	popupMenu.getMenu().add(Menu.NONE, newid++, Menu.NONE, newtag);
			   					    	
					    	    	    userlogado.getTags().add(tag);
					    	    	    
					    	    	    async2 = new AsyncTask<User, Void, UserResult>(){
											
											
											protected void onPreExecute() {
												super.onPreExecute();
											}
											
											protected UserResult doInBackground(User... user) {
												return addTag(user[0]);
												
											}
											/** now lets update the interface **/
											protected void onPostExecute(UserResult result) {
												Toast.makeText(ShowFriendsActivity.this,
														"Tag adicionada!", Toast.LENGTH_SHORT).show();
											}
										};		
										
										async2.execute(userlogado);
			   					    	
			   					    }
			   					  })
			   					.setNegativeButton("Cancel",
			   					  new DialogInterface.OnClickListener() {
			   					    public void onClick(DialogInterface dialog,int id) {
			   						dialog.cancel();
			   					    }
			   					  });
			    
			   				// create alert dialog
			   				AlertDialog alertDialog = alertDialogBuilder.create();
			    
			   				// show it
			   				alertDialog.show();
			   				
		    	    	}else{
		    	    		  	TagUser markedTagUser = new TagUser();
			    	    	    tagmarcada = new Tag();
			    	    	    
			    	    	    tagmarcada.setDescription(item.toString());
			    	    	    markedTagUser.setTag(tagmarcada);
			    	    	    markedTagUser.setUser(userlogado);
			    	    	    
			    	    	    userfriend.getMarkedTags().add(markedTagUser);
			    	    	    Log.d("TAM", "tamanho " + userfriend.getMarkedTags().size());
			    	    	    
			    	    	    async3 = new AsyncTask<User, Void, UserResult>(){
									
									
									protected void onPreExecute() {
										super.onPreExecute();
									}
									
									protected UserResult doInBackground(User... user) {
										return makeTagUser(user[0]);
										
									}
									protected void onPostExecute(UserResult result) {
										Toast.makeText(ShowFriendsActivity.this,
												"O usuario " + userfriend.getUsername() + " foi adicionado ao grupo " + tagmarcada.getDescription(), Toast.LENGTH_SHORT).show();
									}
								};		
								
								async3.execute(userfriend);
		    	    	}
		    	    		   
	    	       return false;
	           }
	        	   
	       });
			
		}
	};
	
	
	private void configureListView(List<User> users) {
		adapter = new FriendsListAdapter(this, R.layout.row_friends_list , users);
		
		usersListView = (ListView) findViewById(R.id.list_view_friends_detail);
		if ( usersListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.layout.row_listview_article_list");
		}
		usersListView.setAdapter( adapter );
		usersListView.setOnItemClickListener(onItemClickAddTag);
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
	
	private UserResult addTag(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/addTag";
		
		ResponseEntity<UserResult> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), UserResult.class);
		
		
		UserResult result = response.getBody();
				
		return result;
	}
	
	private UserResult makeTagUser(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/addTagUser";
		
		ResponseEntity<UserResult> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), UserResult.class);
		
		
		UserResult result = response.getBody();
				
		return result;
	}
	

}
