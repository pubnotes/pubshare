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

package br.ufrn.dimap.pubshare.evaluation;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.restclient.EvaluationResult;
import br.ufrn.dimap.pubshare.util.Constants;
import br.ufrn.dimap.pubshare.util.SessionManager;

public class ArticleEvaluationActivity extends PubnotesActivity {

	private Evaluation evaluation;
	private SessionManager session;
	private ProgressDialog dialog;
	private Article selectedArticle;
	private User user;
	AsyncTask<Evaluation, Void, EvaluationResult> async_evaluate;
	AsyncTask<Evaluation, Void, EvaluationResult> async_publish;
	AsyncTask<User, Void, Evaluation> async_get;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_evaluation);
		selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		this.configureAsyncGet();
		this.configureUI();
		session = new SessionManager(getApplicationContext());
		user = getCurrentUser();
		evaluation = (Evaluation) getIntent().getSerializableExtra(Evaluation.KEY_INSTANCE);
		
		if(evaluation != null)
		{
			/* in case the author already configured an evaluation **/
			configureUIWithValues();
			Toast.makeText(getApplicationContext(), "Evaluation Retrieved",
					Toast.LENGTH_LONG).show();
		}
		else
		{
			async_get.execute(user);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_evaluation, menu);
		return true;
	}

	private void configureUI() {
		OnRatingBarChangeListener listener = new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				Evaluation e = ArticleEvaluationActivity.this.evaluation;
				e.setOriginality(((RatingBar) findViewById(R.id.ratingBar_originality))
						.getRating());
				e.setContribution(((RatingBar) findViewById(R.id.ratingBar_contribution))
						.getRating());
				e.setRelevance(((RatingBar) findViewById(R.id.ratingBar_relevance))
						.getRating());
				e.setReadability(((RatingBar) findViewById(R.id.ratingBar_readability))
						.getRating());
				e.setRelatedWorks(((RatingBar) findViewById(R.id.ratingBar_relatedworks))
						.getRating());
				e.setReviewerFamiliarity(((RatingBar) findViewById(R.id.ratingBar_reviewerfamiliarity))
						.getRating());

				((RatingBar) findViewById(R.id.ratingBar_overall)).setRating(e
						.getOverall());
			}
		};

		((RatingBar) findViewById(R.id.ratingBar_originality))
				.setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar) findViewById(R.id.ratingBar_contribution))
				.setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar) findViewById(R.id.ratingBar_relevance))
				.setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar) findViewById(R.id.ratingBar_readability))
				.setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar) findViewById(R.id.ratingBar_relatedworks))
				.setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar) findViewById(R.id.ratingBar_reviewerfamiliarity))
				.setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);

		OnClickListener evaluate_listener = new OnClickListener() {
			@Override
			public void onClick(View v) {

				configureAsyncEvaluate();
				TextView notes = (TextView) findViewById(R.id.editText_comments);
				ArticleEvaluationActivity.this.evaluation
						.setReviewerNotes(notes.getText().toString());
				
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						ArticleEvaluationActivity.this.evaluation
							.setPublished( which == DialogInterface.BUTTON_POSITIVE );
						
						async_evaluate.execute(evaluation);
					};
				};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ArticleEvaluationActivity.this);
				builder.setMessage("Do you want to publish this evaluation?");
				builder.setPositiveButton("Yes", dialogClickListener);
				builder.setNegativeButton("No", dialogClickListener);
				builder.show();
			}
		};	

		((Button) findViewById(R.id.button_evaluate))
		.setOnClickListener(evaluate_listener);
	}
	
	
	private void configureAsyncGet()
	{
		/************** GET ******************/
		async_get = new AsyncTask<User, Void, Evaluation>() {

			protected void onPreExecute() {
				dialog = new ProgressDialog(ArticleEvaluationActivity.this);
				super.onPreExecute();
				dialog.setMessage("Verifying if you were already editing an evaluation...");
				dialog.show();
			}

			protected Evaluation doInBackground(User... users) {
				
				User user = users[0];
				Evaluation evaluation = getEvaluation(user,selectedArticle);
				return evaluation;
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(Evaluation result) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				
				evaluation = result;
				if(evaluation != null)
				{
					configureUIWithValues();
					Toast.makeText(getApplicationContext(), "Evaluation retrieved!",
							Toast.LENGTH_LONG).show();
				}
				else
				{
					evaluation = new Evaluation();
					evaluation.setArticle(selectedArticle);
					evaluation.setUser(user);
					Toast.makeText(getApplicationContext(), "Start your evaluation",
							Toast.LENGTH_LONG).show();
				}
				
				
			}
		};
	}
	
	private void configureAsyncEvaluate()
	{
		/************** SAVE ******************/
		async_evaluate = new AsyncTask<Evaluation, Void, EvaluationResult>() {

			protected void onPreExecute() {
				dialog = new ProgressDialog(ArticleEvaluationActivity.this);
				super.onPreExecute();
				dialog.setMessage("Saving Evaluation...");
				dialog.show();
			}

			protected EvaluationResult doInBackground(Evaluation... evaluations) {
				
				Evaluation eval = evaluations[0];
				if(eval.getId()!=0)
				{
					return updateEvaluation(eval);
				}
				else
				{
					return saveEvaluation(eval);
				}
			}

			/** now lets update the interface **/
			protected void onPostExecute(EvaluationResult result) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(getApplicationContext(), "Evaluation Saved...",
						Toast.LENGTH_LONG).show();
				
				finish();
				//Intent intent = new Intent(ArticleEvaluationActivity.this, ArticleDetailActivity.class);
				//intent.putExtra(Article.KEY_INSTANCE, selectedArticle);
				//startActivity(intent);
			}
		};
	}
	
	/**
	 * Auxiliary method for async_save
	 * 
	 * @param evaluation
	 * @return
	 */
	private EvaluationResult saveEvaluation(Evaluation evaluation) {
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			String url = "";
			if (evaluation.getId() == 0) {
				// url = "/evaluation/new";
				url = "/evaluation/";
			} else {
				url = "/evaluation/" + evaluation.getId() + "/edit";
			}

			ResponseEntity<EvaluationResult> response = restTemplate.exchange(
					Constants.URL_SERVER + url, HttpMethod.POST,
					new HttpEntity<Object>(evaluation, requestHeaders),
					EvaluationResult.class);

			Log.d("SaveEvaluationRestClient",
					"ResponseEntity " + response.getBody());
			return response.getBody();

		} catch (HttpClientErrorException e) {
			Log.d("SaveEvaluationRestClient",
					"Erro ao tentar editar avaliação: " + e.getMessage());
			e.printStackTrace();
			return new EvaluationResult();
		}
	}

	/**
	 * Auxiliary method for async_publish
	 * 
	 * @param evaluation
	 * @return
	 */
	private EvaluationResult updateEvaluation(Evaluation evaluation) {
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());
			String url = "/evaluation/";

			ResponseEntity<EvaluationResult> response = restTemplate.exchange(
					Constants.URL_SERVER + url, HttpMethod.PUT,
					new HttpEntity<Object>(evaluation, requestHeaders),
					EvaluationResult.class);

			Log.d("PublishEvaluationRestClient",
					"ResponseEntity " + response.getBody());
			return response.getBody();

		} catch (HttpClientErrorException e) {
			Log.d("PublishEvaluationRestClient",
					"Erro ao tentar editar avaliação: " + e.getMessage());
			e.printStackTrace();
			return new EvaluationResult();
		}
	}
	
	/**
	 * Auxiliary method for async_get
	 * @param article
	 * @return
	 */
	private Evaluation getEvaluation(User user, Article article)
	{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		String url = "/evaluation?id=" + user.getId() + "&article=" + article.getTitle();
		Evaluation entity = restTemplate
				.getForObject(Constants.URL_SERVER + url, Evaluation.class);
				
		return entity;
	}
	
	private void configureUIWithValues()
	{
		float originality = evaluation.getOriginality();
		float contribution = evaluation.getContribution();
		float relevance = evaluation.getRelevance();
		float readability = evaluation.getReadability();
		float relatedWorkds = evaluation.getRelatedWorks();
		float reviewerFamiliarity = evaluation.getReviewerFamiliarity();
		
		((RatingBar) findViewById(R.id.ratingBar_originality)).setRating(originality);
		((RatingBar) findViewById(R.id.ratingBar_contribution)).setRating(contribution);
		((RatingBar) findViewById(R.id.ratingBar_relevance)).setRating(relevance);
		((RatingBar) findViewById(R.id.ratingBar_readability)).setRating(readability);
		((RatingBar) findViewById(R.id.ratingBar_relatedworks)).setRating(relatedWorkds);
		((RatingBar) findViewById(R.id.ratingBar_reviewerfamiliarity)).setRating(reviewerFamiliarity);
		((TextView) findViewById(R.id.editText_comments)).setText(evaluation.getReviewerNotes());
	}

}
