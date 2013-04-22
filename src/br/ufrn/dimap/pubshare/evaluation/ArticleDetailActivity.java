package br.ufrn.dimap.pubshare.evaluation;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.EditProfileActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.evaluation.ArticleEvaluationDetailActivity;
import br.ufrn.dimap.pubshare.evaluation.EvaluationListAdapter;
import br.ufrn.dimap.pubshare.evaluation.EvaluationListMockFactory;

/**
 * Class for detailing a specific Article
 * @author Daniel
 *
 */
public class ArticleDetailActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_article_detail);
		setTitle(R.string.title_activity_article_detail);
		
		Article selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		configureMainView(selectedArticle);
		
		List<Evaluation> evaluations = EvaluationListMockFactory.makeEvaluationList();
	    selectedArticle.setEvaluations(evaluations);
		configureEvaluationsSummaryView(selectedArticle);
		
		findViewById(R.id.button_evaluate).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(getApplicationContext(), ArticleEvaluationActivity.class);
		                startActivity(i);
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
	 * Listener to handle the click on evaluations
	 */
	private OnClickListener onClickEvaluationCreate = new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			/** put the logic for register evaluation view*/
		}
	};
}
