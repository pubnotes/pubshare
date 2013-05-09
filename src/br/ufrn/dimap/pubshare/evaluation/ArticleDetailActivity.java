package br.ufrn.dimap.pubshare.evaluation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import br.ufrn.dimap.pubshare.mocks.ArticleMockFactory;
import br.ufrn.dimap.pubshare.util.Constants;

/**
 * Class for detailing a specific Article
 * @author Daniel
 *
 */
public class ArticleDetailActivity extends PubnotesActivity
{
	private ProgressDialog dialog; 
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
		final Article selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		configureMainView(selectedArticle);
		
		/** getting evaluations from the server using the async task**/
		AsyncTask<Article, Void, Evaluation[]> async = new AsyncTask<Article, Void, Evaluation[]>(){
			
			
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
		async.execute(new Article[]{selectedArticle});
		/** done **/
		
		/** giving life to the evaluation button **/
		findViewById(R.id.button_evaluate).setOnClickListener(
				new View.OnClickListener() 
				{
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(ArticleDetailActivity.this, ArticleEvaluationActivity.class);
						intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
						startActivity(intent);
					}
				});
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
		RatingBar rating = (RatingBar) findViewById(R.id.rating_overall_evaluation);
		
		articleTitle.setText(article.getTitle());
		//shouldn't we put abstract in the article class?
		articleAbstract.setText("abstract here!");
		//we must create a method in the article for retrieving the unified evaluation value
		rating.setRating(3.5f);
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
		EvaluationListAdapter adapter = new EvaluationListAdapter(this, R.layout.row_listview_article_evaluation_list, article.getEvaluations());
		ListView evaluationListView = (ListView) findViewById(R.id.list_view_article_detail_evaluations);
		
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
		HttpHeaders headers = new HttpHeaders();
		Map<String, String> body = new HashMap<String,String>();
		body.put("article", article.getTitle());
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/evaluation/evaluationsFromArticle";
		ResponseEntity<Evaluation[]> entity = restTemplate.exchange(
				Constants.URL_SERVER + url, 
				HttpMethod.POST, 
				new HttpEntity<Object>(body, headers), 
				Evaluation[].class);
		
		Evaluation[] evaluationsFromUser = entity.getBody();
				
		return evaluationsFromUser;
	}
}
