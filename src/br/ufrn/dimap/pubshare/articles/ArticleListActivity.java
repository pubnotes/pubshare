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

package br.ufrn.dimap.pubshare.articles;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.download.ArticlesDownloadedActivity;
import br.ufrn.dimap.pubshare.evaluation.ArticleDetailActivity;
import br.ufrn.dimap.pubshare.parsers.ACMParser;
import br.ufrn.dimap.pubshare.parsers.IEEExplorerParser;
import br.ufrn.dimap.pubshare.parsers.Parser;
import br.ufrn.dimap.pubshare.parsers.SpringerParser;
import br.ufrn.dimap.pubshare.recomendation.ActivityRecommendation;

/**
 * Responsible for managing the activity of displaying articles available.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 * @author Itamir Filho <i>itamir.filho@gmail.com</i>
 * @author Daniel Costa <i>daniel.calencar@gmail.com</i>
 */
public class ArticleListActivity extends Activity
{

	private static final String TAG = ArticleListActivity.class.getSimpleName();

	private ListView articlesListView;
	private ArticleListAdapter adapter;
	private View searchingView;

	/**
	 * Daniel Costa.
	 * 
	 * This method is responsible for creating the dialog which actions are:
	 * View article details Download article Recommend article
	 * 
	 * @return Dialog
	 */
	private DialogFragment createActionsDialog(final Article selectedArticle)
	{
		final DialogFragment articleAtctions = new DialogFragment()
		{
			final CharSequence[] actions = { "View details", "Download", "Recommend it" };

			public Dialog onCreateDialog(Bundle savedInstanceState)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(ArticleListActivity.this);
				builder.setTitle(R.string.actions_dialog).setItems(actions,
						createActionsDialogOnClick(selectedArticle));
				return builder.create();
			};
		};
		return articleAtctions;
	}

	/**
	 * Daniel Costa
	 * This method is responsible for creating the onclicklistener to be
	 * executed when an option is clicked in the Dialog fragment
	 * 
	 * @param selectedArticle
	 * @return
	 */
	private DialogInterface.OnClickListener createActionsDialogOnClick(final Article selectedArticle)
	{
		DialogInterface.OnClickListener onclickListener = new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				handleActionsNavigation(selectedArticle, which);
			}
		};
		return onclickListener;
	}

	/**
	 * Daniel Costa
	 * this method handles the navigation depending on the choice made in the
	 * actions dialog
	 * 
	 * @param selectedArticle
	 * @param selectedAction
	 */
	private void handleActionsNavigation(final Article selectedArticle, int selectedAction)
	{
		Intent intent = null;
		switch (selectedAction)
		{
		case 0:
			intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
			intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
			startActivity(intent);
			break;
		case 1:

			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(selectedArticle.getDownloadLink()));
			startActivity(i);
			break;
		case 2:
			intent = new Intent(ArticleListActivity.this, ActivityRecommendation.class);
			intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
			startActivity(intent);
			break;
		}
	}

	/**
	 * Daniel Costa 
	 * Listener to get the selected article
	 * @return  onItemClickListener
	 */
	private OnItemClickListener onArticleClick = new OnItemClickListener()
	{
		public void onItemClick(AdapterView adapter, View v, int position, long id)
		{
			View view = v;
			if (view == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				inflater.inflate(R.layout.activity_article_list, null);
			}
			/** get the selected article and show the actions dialog **/
			final Article selectedArticle = (Article) adapter.getItemAtPosition(position);
			DialogFragment dialog = createActionsDialog(selectedArticle);
			dialog.show(getFragmentManager(), TAG);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);

		articlesListView = (ListView) findViewById(R.id.list_view_articles);
		if (articlesListView == null)
		{
			Log.d(this.getClass().getSimpleName(),
					"Não foi possível encontrar R.layout.row_listview_article_list");
		}

		searchingView = findViewById(R.id.search_status);

		String texto = getIntent().getStringExtra("textoConsulta");
		String tipoBusca = getIntent().getStringExtra("searchType");
		String fonte = getIntent().getStringExtra("library");

		new ListagemTask().execute(new String[] { texto, tipoBusca, fonte });

	}

	private class ListagemTask extends AsyncTask<String, Integer, List<Article>>
	{

		@Override
		protected List<Article> doInBackground(String... urls)
		{
			Parser parser;
			List<Article> articles = new ArrayList<Article>();

			if (urls[2].equals("Springer"))
				parser = new SpringerParser();
			else if (urls[2].equals("ACM"))
				parser = new ACMParser();
			else
				parser = new IEEExplorerParser();

			if (urls[1].equals("Title"))
				articles = parser.findArticlesByTitle(urls[0]);
			else
				articles = parser.findArticleByAuthor(urls[0]);

			publishProgress(10);

			return articles;
		}

		@Override
		protected void onPostExecute(List<Article> result)
		{
			super.onPostExecute(result);
			publishProgress(70);
			configureListView(result);
			searchingView.setVisibility(View.GONE);
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
			// progressBar.setProgress(values[0]);
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			publishProgress(10);
			searchingView.setVisibility(View.VISIBLE);
		}

	}

	private void configureListView(List<Article> articles)
	{
		adapter = new ArticleListAdapter(this, R.layout.row_listview_article_list, articles);
		articlesListView.setAdapter(adapter);
		articlesListView.setOnItemClickListener(onArticleClick);
	}

	public void showMyDownloadActivity()
	{
		Log.d(TAG, "Display My Downloads Activity");

		Intent intent = new Intent(this, ArticlesDownloadedActivity.class);
		startActivity(intent);
	}

}
