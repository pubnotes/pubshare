package br.ufrn.dimap.pubshare.evaluation;

import java.util.Arrays;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.util.Constants;

/**
 * Class for detailing a specific Article
 * @author Daniel
 *
 */
public class ArticleDetailActivity extends PubnotesActivity
{
	private final static int REQUEST_CODE = 14566;
	private ProgressDialog dialog; 
	AsyncTask<Article, Void, Evaluation[]> async;
	Article selectedArticle;
	private ListView evaluationListView;
	
	/**
	 * 1. prepare the content view
	 * 2. prepare the title
	 * 3. get the selected article from  the previous activity  
	 * 4. use async-task to get the evaluations of this article from the server
	 * 5. register the button 'evaluate' to an listener
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detail);
		setTitle(R.string.title_activity_article_detail);
		selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		configureMainView(selectedArticle);
		getEvaluationsAsyncTask();
		async.execute(selectedArticle);
		/** done **/
		
		/** giving life to the evaluation button **/
		findViewById(R.id.button_evaluate).setOnClickListener(
				new View.OnClickListener() 
				{
					@Override
					public void onClick(View view) {
						
						
						EvaluationListAdapter adapter = (EvaluationListAdapter) evaluationListView.getAdapter();
						User user = getCurrentUser();
						Evaluation evalFromUser =  null;
						
						for(Evaluation element : adapter.getEvaluations())
						{
							if(element.getUser().getId() == user.getId())
							{
								evalFromUser = element;
							}
						}
						
						Intent intent = null;
						if(evalFromUser != null)
						{
							intent = new Intent(ArticleDetailActivity.this, ArticleEvaluationDetailActivity.class);
							intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
							intent.putExtra(Evaluation.KEY_INSTANCE, evalFromUser);
							startActivity(intent);
						}
						else
						{
							intent = new Intent(ArticleDetailActivity.this, ArticleEvaluationActivity.class);
							intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
							intent.putExtra(Evaluation.KEY_INSTANCE, evalFromUser);
							startActivityForResult(intent, REQUEST_CODE);
						}
					}
				});
	}	
	
	public void getEvaluationsAsyncTask()
	{
		/** getting evaluations from the server using the async task**/
		async = new AsyncTask<Article, Void, Evaluation[]>(){
			
			
			protected void onPreExecute() {
				dialog = new ProgressDialog(ArticleDetailActivity.this);
				super.onPreExecute();
				dialog.setMessage("Retrieving the evaluations...");
				dialog.show();
			}
			
			protected Evaluation[] doInBackground(Article... article) {
				return retrieveEvaluations(article[0]);
				
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(Evaluation[] result) {
				if(dialog.isShowing())
				{
					dialog.dismiss();
				}
				selectedArticle.setEvaluations(Arrays.asList(result));
				configureEvaluationsSummaryView(selectedArticle);
			}
		};
	}
	
	/**
	 * Configure the main view of the activity
	 * 1. Title - the article's title
	 * 2. Abstract - the article's abstract
	 * 3. Rating - the unified rating value from all evaluations
	 * @param article - the selectedArticle
	 */
	private void configureMainView(Article article)
	{
		TextView articleTitle = (TextView) findViewById(R.id.label_article_title);
		TextView articleAbstract = (TextView) findViewById(R.id.label_article_abstract_text);
		articleTitle.setText(article.getTitle());
		articleAbstract.setText(article.getAbztract());
	}
	
	/**
	 * Configure the view for listing the evaluations already made for
	 * a specific article
	 * 1. Evaluation notes
	 * 2. Evaluation overAll	
	 * @param article - the selected Article
	 */
	private void configureEvaluationsSummaryView(Article article)
	{
		RatingBar rating = (RatingBar) findViewById(R.id.rating_overall_evaluation);
		if(article.getEvaluations() == null || article.getEvaluations().isEmpty())
		{
			rating.setRating(0.0f);
		}
		else
		{
			float overall = 0.0f;
			for(Evaluation eval : article.getEvaluations())
			{
				overall += eval.getOverall();
			}
			overall = overall/article.getEvaluations().size();
			rating.setRating(overall);
		}
		EvaluationListAdapter adapter = new EvaluationListAdapter(this, R.layout.row_listview_article_evaluation_list, article.getEvaluations());
		evaluationListView = (ListView) findViewById(R.id.list_view_article_detail_evaluations);
		
		if(evaluationListView == null)
		{
			Log.d(this.getClass().getSimpleName(), "Nao foi possivel encontrar R.layout.row_listview_article_evaluation_list");
		}
		evaluationListView.setAdapter(adapter);
		evaluationListView.setOnItemClickListener(onItemClickEvaluationDetail);
	}
	
	/**
	 * Listener to handle the click on evaluations
	 */
	private OnItemClickListener onItemClickEvaluationDetail = new OnItemClickListener()
	{
		public void onItemClick(AdapterView adapter, View v, int position, long id) 
		{
			View view = v;
			if(view == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				inflater.inflate(R.layout.row_listview_article_evaluation_list, null);
			}
			
			Evaluation evaluation = (Evaluation) adapter.getItemAtPosition(position); 
			
			Intent intent = new Intent(ArticleDetailActivity.this,ArticleEvaluationDetailActivity.class);
			intent.putExtra(Evaluation.KEY_INSTANCE, evaluation);
			startActivity(intent);
		}
	};
	
	/**
	 * Here we are going to the pubnotes server in order to get the 
	 * evaluations from the article selected in the previous activity
	 * @param article
	 * @return
	 */
	private Evaluation[] retrieveEvaluations(Article article)
	{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/evaluation/evaluationsFromArticle?title=" + article.getTitle().trim();
		Evaluation[] entity = restTemplate
				.getForObject(Constants.URL_SERVER + url, Evaluation[].class);
				
		return entity;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE)
		{
			getEvaluationsAsyncTask();
			async.execute(selectedArticle);
		}
	}
}
