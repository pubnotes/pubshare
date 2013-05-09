package br.ufrn.dimap.pubshare.evaluation;

import java.util.List;

import br.ufrn.dimap.pubshare.activity.PubnotesActivity;
import br.ufrn.dimap.pubshare.activity.PubshareActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.activity.R.id;
import br.ufrn.dimap.pubshare.activity.R.layout;
import br.ufrn.dimap.pubshare.activity.R.menu;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.mocks.UserMockFactory;
import br.ufrn.dimap.pubshare.restclient.SaveEvaluationRestClient;
import br.ufrn.dimap.pubshare.util.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class ArticleEvaluationActivity extends PubnotesActivity {

	private Evaluation evaluation;
	private SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_evaluation);
		this.configureUI();
		
		session = new SessionManager(getApplicationContext());
		
		this.evaluation = new Evaluation();
		//List<User> users = UserMockFactory.makeUserList();
		
		Article selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		User user = UserMockFactory.makeSingleUser(); //getCurrentUser();
		this.evaluation.setUser(user); 
		this.evaluation.setArticle(selectedArticle); 
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
				
				SaveEvaluationRestClient rest = new SaveEvaluationRestClient();
				
				rest.execute(ArticleEvaluationActivity.this.evaluation);
				
				Toast.makeText(getApplicationContext(),  "Evaluation Saved...", Toast.LENGTH_LONG).show();
			}
		};
		
		((Button)findViewById(R.id.button_save)).setOnClickListener(save_listener);
	}
}
