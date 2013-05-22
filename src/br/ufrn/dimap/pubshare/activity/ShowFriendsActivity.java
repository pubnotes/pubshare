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
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ShowFriendsActivity extends PubnotesActivity {

	
	User userlogado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_friends);
		
		//Aqui deve pegar o usuario logado (username) e buscar no servidor
		//a lista de amigos (User) dele
		//e adicionar a listview

		userlogado = ShowFriendsActivity.this.getCurrentUser();
		
		/** getting users from the server using the async task**/
		AsyncTask<User, Void, User[]> async = new AsyncTask<User, Void, User[]>(){
			
			
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
			
			final User userfriend = (User) adapter.getItemAtPosition(position);
			userlogado = ShowFriendsActivity.this.getCurrentUser();
			
			final List<Tag> tags = userlogado.getTags();
			
			final PopupMenu popupMenu = new PopupMenu(ShowFriendsActivity.this, view.findViewById(R.id.addtag));
			popupMenu.getMenu().add(Menu.NONE, tags.size()+ 1, Menu.NONE, "Create New Tag");
			
			for (int i = 0; i < tags.size(); i++) {
				popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, tags.get(i).getDescription());
			}
		
			popupMenu.show();
			
			
	       popupMenu.setOnMenuItemClickListener(
	               new PopupMenu.OnMenuItemClickListener() {
	           @Override
	       		public boolean onMenuItemClick(MenuItem item) {
	    	    	    LayoutInflater li = LayoutInflater.from(ShowFriendsActivity.this);
	   				    View promptsView = li.inflate(R.layout.tags, null);
	    	    	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	   					ShowFriendsActivity.this);
	    	    	    alertDialogBuilder.setMessage("Do you want to create a new tag?");
	    	    	    
		    	    	if(item.getItemId() ==  tags.size() + 1){    
			   				// set prompts.xml to alertdialog builder
			   				alertDialogBuilder.setView(promptsView);
			    
			   				final EditText userInput = (EditText) promptsView
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
					    	    	    
					    	    		AsyncTask<User, Void, UserResult> async2 = new AsyncTask<User, Void, UserResult>(){
											
											
											protected void onPreExecute() {
												super.onPreExecute();
											}
											
											protected UserResult doInBackground(User... user) {
												return addTag(user[0]);
												
											}
											/** now lets update the interface **/
											protected void onPostExecute(UserResult result) {
												Toast.makeText(ShowFriendsActivity.this,
														"Added tag!", Toast.LENGTH_LONG).show();
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
			    	    	    final Tag tagmarcada = new Tag();
			    	    	    tagmarcada.setDescription(item.toString());
			    	    	    boolean inside = containsUserTag(userfriend.getMarkedTags(), tagmarcada.getDescription());
			    	    	    //eu marquei você?
			    	    	    if(inside == false){
				    	    	    markedTagUser.setTag(tagmarcada);
				    	    	    markedTagUser.setUser(userlogado);
				    	    	    
				    	    	    userfriend.getMarkedTags().add(markedTagUser);
				    	    	    
				    	    	    AsyncTask<User, Void, UserResult> async3 = new AsyncTask<User, Void, UserResult>(){
										
										
										protected void onPreExecute() {
											super.onPreExecute();
										}
										
										protected UserResult doInBackground(User... user) {
											return makeTagUser(user[0]);
											
										}
										protected void onPostExecute(UserResult result) {
											Toast.makeText(ShowFriendsActivity.this,
													"The user " + userfriend.getUsername() + " was added to the group " + tagmarcada.getDescription(), Toast.LENGTH_LONG).show();
										}
									};		
									
									async3.execute(userfriend);
			    	    	    }else{
			    	    	    	Toast.makeText(ShowFriendsActivity.this,
											"You've added " + userfriend.getUsername() + " to the group " + tagmarcada.getDescription(), Toast.LENGTH_LONG).show();
			    	    	    	
			    	    	    	
			    	    	    }
			    	    	}
		    	    		   
	    	       return false;
	           }
	        	   
	       });
			
		}
	};
	
	public boolean containsUserTag(List<TagUser> t, String name){
		boolean isInside = false;
		for (int i = 0; i < t.size(); i++) {
			if(t.get(i).getTag().getDescription().equals(name)){
				isInside = true;
				break;
			}
		}
		return isInside;
	}
	private void configureListView(List<User> users) {
		final FriendsListAdapter adapter = new FriendsListAdapter(this, R.layout.row_friends_list , users);
		
		ListView usersListView = (ListView) findViewById(R.id.list_view_friends_detail);
		if ( usersListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.layout.row_listview_article_list");
		}
		usersListView.setAdapter( adapter );
		usersListView.setOnItemClickListener(onItemClickAddTag);
		
		usersListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {

            	 
            	 LayoutInflater li = LayoutInflater.from(ShowFriendsActivity.this);
				 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				 ShowFriendsActivity.this);
				 
				 final View view = arg1;
					if(view == null)
					{
						LayoutInflater inflater = getLayoutInflater();
						inflater.inflate(R.layout.row_friends_list, null);
					}
					
					final User userf = (User) adapter.getItem(pos);
					userlogado = ShowFriendsActivity.this.getCurrentUser();
					
				 
	   				// set dialog message
	   				alertDialogBuilder
	   					.setCancelable(false)
	   					.setPositiveButton("View Groups",
	   					  new DialogInterface.OnClickListener() {
	   					    public void onClick(DialogInterface dialog,int id) {
	   						
	   					    	
	   					    }
	   					  })
	   					.setNegativeButton("Delete Friend",
	   					  new DialogInterface.OnClickListener() {
	   					    public void onClick(DialogInterface dialog,int id) {
	   					    	userlogado.getFriends().remove(getIndice(userlogado.getFriends(), userf.getUsername()));
	   					    		Log.d("AMIGOS", "tamanho " + userlogado.getFriends().size());
	   					    	
	   					    	
	   					    	 AsyncTask<User, Void, UserResult>	async4 = new AsyncTask<User, Void, UserResult>(){
									
									
									protected void onPreExecute() {
										super.onPreExecute();
									}
									
									protected UserResult doInBackground(User... user) {
										return removeFriend(user[0]);
										
									}
									protected void onPostExecute(UserResult result) {
										Toast.makeText(ShowFriendsActivity.this,
												"The user " + userf.getUsername() + " has been removed from your friends list!", Toast.LENGTH_LONG).show();
										Intent i = new Intent(ShowFriendsActivity.this, ShowFriendsActivity.class);
										i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						                startActivity(i);
									}
								};		
								
								async4.execute(userlogado);
	   					    
	   					    }
	   					  });
	    
	   				// create alert dialog
	   				AlertDialog alertDialog = alertDialogBuilder.create();
	    
	   				// show it
	   				alertDialog.show();
				 

                return true;
            }
        }); 
	}
	
	public int getIndice(List<User> u, String name){
		int ind = 0;
		for (int i = 0; i < u.size(); i++) {
			if(name.equals(u.get(i).getUsername())){
				ind = i;
				break;
			}
		}
		return ind;
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
	
	private UserResult removeFriend(User user)
	{
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		 
		RestTemplate restTemplate = new RestTemplate();
	
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/user/removeFriend";
		
		ResponseEntity<UserResult> response = restTemplate.exchange(  
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(user, requestHeaders), UserResult.class);
		
		
		UserResult result = response.getBody();
				
		return result;
	}
	

}
