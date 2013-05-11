package br.ufrn.dimap.pubshare.activity;

import java.util.List;

import br.ufrn.dimap.pubshare.adapters.ArticleListAdapter;
import br.ufrn.dimap.pubshare.adapters.UserListAdapter;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class SearchPeopleActivity extends Activity {

	private ListView usersListView;
	private UserListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_people);
		
		//A partir da entrada fornecida no campo de busca
		//quando clicar no botao de busca, fazer busca no servidor
		//deve pegar os usuarios (username, aboutme) do servidor numa lista
		//e adicionar a listview
		List<User> users = UserMockFactory.makeUserList();
		
		configureListView(users);
		
	}
	
	private void configureListView(List<User> users) {
		adapter = new UserListAdapter(this, R.layout.row_listview_people_list , users);
		
		usersListView = (ListView) findViewById(R.id.list_view_people_detail);
		if ( usersListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.layout.row_listview_article_list");
		}
		usersListView.setAdapter( adapter );
		//Aqui possivelmente virah o codigo do click no botao de +
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_people, menu);
		return true;
	}

}
