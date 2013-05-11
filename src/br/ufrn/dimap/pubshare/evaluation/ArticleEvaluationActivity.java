package br.ufrn.dimap.pubshare.evaluation;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
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
import br.ufrn.dimap.pubshare.activity.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import br.ufrn.dimap.pubshare.restclient.results.EvaluationResult;
import br.ufrn.dimap.pubshare.util.Constants;
import br.ufrn.dimap.pubshare.util.SessionManager;

public class ArticleEvaluationActivity extends PubnotesActivity {

	private Evaluation evaluation;
	private SessionManager session;
	private ProgressDialog dialog;
	private Article selectedArticle;
	private User user;
	AsyncTask<Evaluation, Void, EvaluationResult> async;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_evaluation);
		this.configureUI();
		
		session = new SessionManager(getApplicationContext());
		
		this.evaluation = new Evaluation();
		selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		user = UserMockFactory.makeSingleUser(); //getCurrentUser();
		this.evaluation.setUser(user); 
		this.evaluation.setArticle(selectedArticle);
		async = new AsyncTask<Evaluation, Void, EvaluationResult>(){
			
			
			protected void onPreExecute() {
				dialog = new ProgressDialog(ArticleEvaluationActivity.this);
				super.onPreExecute();
				dialog.setMessage("Saving Evaluation...");
				dialog.show();
			}
			
			protected EvaluationResult doInBackground(Evaluation... evaluation) {
				return saveEvaluation(evaluation[0]);
			}
			
			/** now lets update the interface **/
			protected void onPostExecute(EvaluationResult result) {
				if(dialog.isShowing())
				{
					dialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),  "Evaluation Saved...", Toast.LENGTH_LONG).show();
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_evaluation, menu);
		return true;
	}

	
	private void configureUI(){
		OnRatingBarChangeListener listener = new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				Evaluation e = ArticleEvaluationActivity.this.evaluation; 
				e.setOriginality( ((RatingBar)findViewById(R.id.ratingBar_originality)).getRating() );
				e.setContribution( ((RatingBar)findViewById(R.id.ratingBar_contribution)).getRating() );
				e.setRelevance( ((RatingBar)findViewById(R.id.ratingBar_relevance)).getRating() );
				e.setReadability( ((RatingBar)findViewById(R.id.ratingBar_readability)).getRating() );
				e.setRelatedWorks( ((RatingBar)findViewById(R.id.ratingBar_relatedworks)).getRating() );
				e.setReviewerFamiliarity( ((RatingBar)findViewById(R.id.ratingBar_reviewerfamiliarity)).getRating() );
				
				((RatingBar)findViewById(R.id.ratingBar_overall)).setRating( e.getOverall() );
			}
		};
		
		((RatingBar)findViewById(R.id.ratingBar_originality)).setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar)findViewById(R.id.ratingBar_contribution)).setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar)findViewById(R.id.ratingBar_relevance)).setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar)findViewById(R.id.ratingBar_readability)).setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar)findViewById(R.id.ratingBar_relatedworks)).setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		((RatingBar)findViewById(R.id.ratingBar_reviewerfamiliarity)).setOnRatingBarChangeListener((OnRatingBarChangeListener) listener);
		
		OnClickListener save_listener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				TextView notes = (TextView) findViewById(R.id.editText_comments);
				ArticleEvaluationActivity.this.evaluation.setReviewerNotes(notes.getText().toString());
				async.execute(evaluation);
				//Toast.makeText(getApplicationContext(),  "Evaluation Saved...", Toast.LENGTH_LONG).show();
			}
		};
		
		((Button)findViewById(R.id.button_save)).setOnClickListener(save_listener);
	}
	
	private EvaluationResult saveEvaluation(Evaluation evaluation)
	{
		try {
			HttpHeaders requestHeaders = new HttpHeaders(); 
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			 
			RestTemplate restTemplate = new RestTemplate();
 	
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			
			String url = "";
			if (evaluation.getId()==0){
				//url = "/evaluation/new";
				url = "/evaluation/";
			}else{
				url = "/evaluation/" + evaluation.getId()+ "/edit";
			}
			
			ResponseEntity<EvaluationResult> response = restTemplate.exchange(  
					Constants.URL_SERVER + url, 
					HttpMethod.POST, 
					new HttpEntity<Object>(evaluation, requestHeaders), EvaluationResult.class);
			
			Log.d("SaveEvaluationRestClient", "ResponseEntity " + response.getBody() );
			return response.getBody();
			
 		} catch (HttpClientErrorException e) {		
 			Log.d("SaveEvaluationRestClient", "Erro ao tentar editar avaliação: " + e.getMessage() );
 			e.printStackTrace();
			return new EvaluationResult();
		}
	}
	
}
