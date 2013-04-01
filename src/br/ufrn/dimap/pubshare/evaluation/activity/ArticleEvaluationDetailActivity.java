package br.ufrn.dimap.pubshare.evaluation.activity;

import br.ufrn.dimap.pubshare.activity.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * Class responsible for detailing the information of a specific evaluation of an article
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
	}
}
