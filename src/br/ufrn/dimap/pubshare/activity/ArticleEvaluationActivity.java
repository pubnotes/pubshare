package br.ufrn.dimap.pubshare.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ArticleEvaluationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_evaluation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_evaluation, menu);
		return true;
	}

}
