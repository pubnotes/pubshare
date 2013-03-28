package br.ufrn.dimap.pubshare.activity;

import java.util.List;

import br.ufrn.dimap.pubshare.adapters.ArticleListAdapter;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.mocks.ArticleListMockFactory;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

/**
 * Responsible for managing the activity of displaying articles available.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
public class ArticleListActivity extends Activity {

	private ListView articlesListView;
	private ArticleListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				

		setContentView(R.layout.activity_article_list);
		setTitle(R.string.title_activity_article_list);
		
		List<Article> articles = ArticleListMockFactory.makeArticleList();		
		
		adapter = new ArticleListAdapter(this, R.layout.row_listview_article_list , articles);
		
		articlesListView = (ListView) findViewById(R.id.list_view_articles);
		if ( articlesListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.layout.row_listview_article_list");
		}
		articlesListView.setAdapter( adapter );		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.article_list, menu);
		return true;
	}

}
