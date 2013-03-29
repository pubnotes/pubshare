package br.ufrn.dimap.pubshare.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ArticlesDownloadedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articles_downloaded);
		configureActionBar();

	}

	private void configureActionBar() {
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);	        
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, ArticleListActivity.class);
	            // See Using the App Icon for Navigation ref: http://developer.android.com/guide/topics/ui/actionbar.html
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
 
	}

}
