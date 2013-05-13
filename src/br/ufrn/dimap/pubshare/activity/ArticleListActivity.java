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

package br.ufrn.dimap.pubshare.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.ufrn.dimap.pubshare.adapters.ArticleListAdapter;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.download.activity.ArticlesDownloadedActivity;
import br.ufrn.dimap.pubshare.download.service.DownloaderService;
import br.ufrn.dimap.pubshare.evaluation.ArticleDetailActivity;
import br.ufrn.dimap.pubshare.mocks.ArticleMockFactory;
import br.ufrn.dimap.pubshare.parsers.ACMParser;
import br.ufrn.dimap.pubshare.parsers.IEEExplorerParser;
import br.ufrn.dimap.pubshare.parsers.Parser;
import br.ufrn.dimap.pubshare.parsers.SpringerParser;

/**
 * Responsible for managing the activity of displaying articles available.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 * @author Itamir Filho <i>itamir.filho@gmail.com</i>
 */
public class ArticleListActivity extends Activity {

	private static final String TAG = ArticleListActivity.class.getSimpleName();

	private Article selectedArticle;
	private ListView articlesListView;
	private ArticleListAdapter adapter;
//	private ProgressBar progressBar;

	
	/**
	 * @author daniel.costa
	 * Listener to get the selected article
	 */
	private OnItemLongClickListener onArticleClick = new OnItemLongClickListener()
	{
		public boolean onItemLongClick(AdapterView adapter, View v, int position, long id) 
		{
			View view = v;
			if(view == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				inflater.inflate(R.layout.activity_article_list, null);
			}
			selectedArticle = (Article) adapter.getItemAtPosition(position); 
			return false;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
//		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		articlesListView = (ListView) findViewById(R.id.list_view_articles);
		if (articlesListView == null) {
			Log.d(this.getClass().getSimpleName(),
					"Não foi possível encontrar R.layout.row_listview_article_list");
		}
		
		String texto = getIntent().getStringExtra("textoConsulta");
		String tipoBusca = getIntent().getStringExtra("searchType");
		String fonte = getIntent().getStringExtra("library");

		// List<Article> articles = ArticleMockFactory.makeArticleList();
		new ListagemTask().execute(new String[] {texto, tipoBusca, fonte});

	}

	private class ListagemTask extends AsyncTask<String, Integer, List<Article>> {

		@Override
		protected List<Article> doInBackground(String... urls) {
			Parser parser;
			List<Article> articles = new ArrayList<Article>();
			
			if(urls[2].equals("Springer"))
				parser = new SpringerParser();
			else if(urls[2].equals("ACM"))
				parser = new ACMParser();
			else
				parser = new IEEExplorerParser();
			
			if(urls[1].equals("Title")) 
				articles = parser.findArticlesByTitle(urls[0]);
			else 
				articles = parser.findArticlesByTitle(urls[0]);
			
			
			publishProgress(10);
			
			return articles;
		}

		@Override
		protected void onPostExecute(List<Article> result) {
			super.onPostExecute(result);
			publishProgress(70);
			configureListView(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
//			progressBar.setProgress(values[0]);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			publishProgress(10);
		}

	}

	private void configureListView(List<Article> articles) {
		adapter = new ArticleListAdapter(this,
				R.layout.row_listview_article_list, articles);

		articlesListView.setAdapter(adapter);
		registerForContextMenu(articlesListView);
		articlesListView.setOnItemLongClickListener(onArticleClick);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_long_press_article_list, menu);

		MenuItem menuItem = (MenuItem) menu
				.findItem(R.id.contextual_menu_delete);
		menuItem.setVisible(false);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.contextual_menu_view:
			intent = new Intent(this, ArticleDetailActivity.class);
			intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
			startActivity(intent);
			return true;
		case R.id.contextual_menu_download:
			intent = new Intent(this, DownloaderService.class);
			selectedArticle = ArticleMockFactory.singleArticle();
			intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
			startService(intent);
			return true;
		case R.id.contextual_menu_share:
			// share
			return true;

		default:
			return super.onContextItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_my_downloads:
			showMyDownloadActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showMyDownloadActivity() {
		Log.d(TAG, "Display My Downloads Activity");

		Intent intent = new Intent(this, ArticlesDownloadedActivity.class);
		startActivity(intent);
	}

}
