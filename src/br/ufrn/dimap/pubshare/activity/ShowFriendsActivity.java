package br.ufrn.dimap.pubshare.activity;

import java.util.List;

import br.ufrn.dimap.pubshare.adapters.FriendsListAdapter;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class ShowFriendsActivity extends Activity {

	private ListView usersListView;
	private FriendsListAdapter adapter;
	//private PopupMenu popupMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_friends);
		//popupMenu = new PopupMenu(this, findViewById(R.id.addtag));
		
		//Aqui deve pegar o usuario logado (username) e buscar no servidor
		//a lista de amigos (User) dele
		//e adicionar a listview
		
		User user = UserMockFactory.makeSingleUser();
		List<User> users = UserMockFactory.getFriends(user);
		/*String[] tags = user.getTags();
		for (int i = 0; i < tags.length; i++) {
			 popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, tags[i]);
		}
		
        popupMenu.setOnMenuItemClickListener((OnMenuItemClickListener) this);

        findViewById(R.id.addtag).setOnClickListener((OnClickListener) this);
        */
		configureListView(users);
		
	}
	
	/*public boolean onMenuItemClick(MenuItem item) {
	       switch (item.getItemId()) {
	       case 0:
	    	   Toast.makeText(ShowFriendsActivity.this,
						"Click!", Toast.LENGTH_SHORT).show();
	    	   	  break;
	       case 1:
	    	   Toast.makeText(ShowFriendsActivity.this,
						"Click!", Toast.LENGTH_SHORT).show();
	    	   break;
	       case 2:
	    	   Toast.makeText(ShowFriendsActivity.this,
						"Click!", Toast.LENGTH_SHORT).show();
	           break;
	       }
	       return false;
	}
	
	public void onClick(View v) {
	       popupMenu.show();
	}*/
	
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

}
