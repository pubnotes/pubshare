package br.ufrn.dimap.pubshare.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SearchPeopleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_people);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_people, menu);
		return true;
	}

}
