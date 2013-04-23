package br.ufrn.dimap.pubshare.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Activity to search articles. 
 * @author itamir
 */
public class ArticleSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_article_search, menu);
		return true;
	}

}
