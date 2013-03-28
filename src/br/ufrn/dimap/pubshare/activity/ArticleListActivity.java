package br.ufrn.dimap.pubshare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Responsible for managing the activity of displaying articles available.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
public class ArticleListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.article_list, menu);
		return true;
	}

}
