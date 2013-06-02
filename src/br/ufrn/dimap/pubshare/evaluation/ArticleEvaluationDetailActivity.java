package br.ufrn.dimap.pubshare.evaluation;

import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Evaluation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Class responsible for detailing the information of a specific evaluation of
 * an article
 * 
 * @author Daniel
 * 
 */
public class ArticleEvaluationDetailActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation_article_detail);
		setTitle(R.string.title_activity_evaluation_article_detail);
		Intent intent = getIntent();
		Evaluation evaluation = (Evaluation) intent.getSerializableExtra(Evaluation.KEY_INSTANCE);
		configureView(evaluation);
	}

	private void configureView(Evaluation evaluation)
	{
		RatingBar originality = (RatingBar) findViewById(R.id.rating_originality);
		RatingBar contribution = (RatingBar) findViewById(R.id.rating_contribution);
		RatingBar relevance = (RatingBar) findViewById(R.id.rating_relevance);
		RatingBar readability = (RatingBar) findViewById(R.id.rating_readability);
		RatingBar relatedworks = (RatingBar) findViewById(R.id.rating_relatedworks);
		RatingBar familiarity = (RatingBar) findViewById(R.id.rating_familiarity);
		TextView reviewerNotes = (TextView) findViewById(R.id.text_review_notes);
		TextView finalGrade = (TextView) findViewById(R.id.text_final_grade);

		originality.setRating(evaluation.getOriginality());
		contribution.setRating(evaluation.getContribution());
		relevance.setRating(evaluation.getRelevance());
		readability.setRating(evaluation.getReadability());
		relatedworks.setRating(evaluation.getRelatedWorks());
		familiarity.setRating(evaluation.getReviewerFamiliarity());
		reviewerNotes.setText(evaluation.getReviewerNotes());
		finalGrade.setText(String.valueOf(evaluation.getOverall()));

		originality.setEnabled(false);
		contribution.setEnabled(false);
		relevance.setEnabled(false);
		readability.setEnabled(false);
		relatedworks.setEnabled(false);
		familiarity.setEnabled(false);
		reviewerNotes.setEnabled(false);
		finalGrade.setEnabled(false);
	}
}
