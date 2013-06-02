package br.ufrn.dimap.pubshare.evaluation;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.ufrn.dimap.pubshare.activity.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.util.Constants;

/**
 * Class for detailing a specific Article
 * 
 * @author Daniel Costa
 * 
 */
public class ArticleDetailActivity extends PubnotesActivity
{
	private final static int REQUEST_CODE = 14566;
	private ProgressDialog dialog;
	AsyncTask<Article, Void, Evaluation[]> async;
	Article selectedArticle;

	/**
	 * Daniel Costa 1. prepare the content view 2. prepare the title 3. get the
	 * selected article from the previous activity 4. use async-task to get the
	 * evaluations of this article from the server 5. register the button
	 * 'evaluate' to an listener
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
		findViewById(R.id.button_evaluate).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				LinearLayout evaluationsLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_evaluations_list);
				User user = getCurrentUser();
				Evaluation evalFromUser = null;

				for (int i = 0; i < evaluationsLinearLayout.getChildCount(); i++)
				{
					EvaluationLinearLayout evaluationRow = (EvaluationLinearLayout) evaluationsLinearLayout
							.getChildAt(i);
					Evaluation eval = evaluationRow.getEvaluation();
					if (eval.getUser().getId() == user.getId())
					{
						evalFromUser = eval;
					}
				}

				Intent intent = null;
				if (evalFromUser != null)
				{
					intent = new Intent(ArticleDetailActivity.this,
							ArticleEvaluationDetailActivity.class);
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

	/**
	 * Daniel Costa This method creates the AsyncTask which retrieves the
	 * evaluations from the server
	 */
	public void getEvaluationsAsyncTask()
	{
		/** getting evaluations from the server using the async task **/
		async = new AsyncTask<Article, Void, Evaluation[]>()
		{

			protected void onPreExecute()
			{
				dialog = new ProgressDialog(ArticleDetailActivity.this);
				super.onPreExecute();
				dialog.setMessage("Retrieving the evaluations...");
				dialog.show();
			}

			protected Evaluation[] doInBackground(Article... article)
			{
				return retrieveEvaluations(article[0]);

			}

			/** now lets update the interface **/
			protected void onPostExecute(Evaluation[] result)
			{
				if (dialog.isShowing())
				{
					dialog.dismiss();
				}
				selectedArticle.setEvaluations(Arrays.asList(result));
				configureEvaluationsSummaryView(selectedArticle);
			}
		};
	}

	/**
	 * Configure the main view of the activity 1. Title - the article's title 2.
	 * Abstract - the article's abstract 3. Rating - the unified rating value
	 * from all evaluations
	 * 
	 * @param article
	 *            - the selectedArticle
	 */
	private void configureMainView(Article article)
	{
		TextView articleTitle = (TextView) findViewById(R.id.label_article_title);
		TextView articleAbstract = (TextView) findViewById(R.id.label_article_abstract_text);
		articleTitle.setText(article.getTitle());
		articleAbstract.setText(article.getAbztract());
	}

	/**
	 * Daniel Costa Configure the view for listing the evaluations already made
	 * for a specific article 1. Evaluation notes 2. Evaluation overAll
	 * 
	 * @param article
	 *            - the selected Article
	 */
	private void configureEvaluationsSummaryView(Article article)
	{
		RatingBar rating = (RatingBar) findViewById(R.id.rating_overall_evaluation);
		if (article.getEvaluations() == null || article.getEvaluations().isEmpty())
		{
			rating.setRating(0.0f);
		}
		else
		{
			float overall = 0.0f;
			for (Evaluation eval : article.getEvaluations())
			{
				overall += eval.getOverall();
			}
			overall = overall / article.getEvaluations().size();
			rating.setRating(overall);
		}

		List<Evaluation> evaluations = article.getEvaluations();
		createEvaluationsRows(evaluations);

	}

	/**
	 * Daniel Costa Listener to handle the click on evaluations
	 */
	private OnClickListener onClickEvaluation = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			EvaluationLinearLayout evaluationRow = (EvaluationLinearLayout) view;
			Evaluation evaluation = evaluationRow.getEvaluation();

			Intent intent = new Intent(ArticleDetailActivity.this,
					ArticleEvaluationDetailActivity.class);
			intent.putExtra(Evaluation.KEY_INSTANCE, evaluation);
			startActivity(intent);
		}
	};

	/**
	 * Daniel Costa Here we are going to the pubnotes server in order to get the
	 * evaluations from the article selected in the previous activity
	 * 
	 * @param article
	 * @return
	 */
	private Evaluation[] retrieveEvaluations(Article article)
	{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

		String url = "/evaluation/evaluationsFromArticle?title=" + article.getTitle().trim();
		Evaluation[] entity = restTemplate.getForObject(Constants.URL_SERVER + url,
				Evaluation[].class);

		return entity;
	}

	@Override
	/**
	 * Daniel Costa
	 * verifies if this activity should retrieve the evaluations from the server once again
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE)
		{
			getEvaluationsAsyncTask();
			async.execute(selectedArticle);
		}
	}

	/**
	 * DanielCosta
	 * 
	 * This method is responsible for dynamically create the evaluations list
	 * within this activity
	 * 
	 * @param evaluations
	 */
	private void createEvaluationsRows(List<Evaluation> evaluations)
	{
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linear_layout_evaluations_list);
		mainLayout.removeAllViews();
		
		for (Evaluation evaluation : evaluations)
		{
			EvaluationLinearLayout evaluationRow = new EvaluationLinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			evaluationRow.setEvaluation(evaluation);
			evaluationRow.setLayoutParams(params);
			evaluationRow.setOrientation(LinearLayout.VERTICAL);

			TextView date = new TextView(this);
			date.setText(evaluation.getEvalDate().toString());

			TextView notes = new TextView(this);
			notes.setText(evaluation.getReviewerNotes());

			RatingBar overall = new RatingBar(this, null, android.R.attr.ratingBarStyleSmall);
			LayoutParams overallParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			overall.setLayoutParams(overallParams);
			overall.setNumStars(5);
			overall.setRating(evaluation.getOverall());

			evaluationRow.addView(date);
			evaluationRow.addView(notes);
			evaluationRow.addView(overall);
			evaluationRow.setDescendantFocusability(LinearLayout.FOCUS_BLOCK_DESCENDANTS); //block the children to receive focus
			evaluationRow.setClickable(true);
			evaluationRow.setOnClickListener(onClickEvaluation);

			mainLayout.addView(evaluationRow);
		}
	}
}
