package br.ufrn.dimap.pubshare.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ShowProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_profile);
		
		//Aqui eu devo pegar a ref do User, fazer uma busca no banco
		//pegar o Profile do user e setar os campos da activity
		
		Button btnMenu = (Button) findViewById(R.id.btnMainMenu);
				btnMenu.setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent i = new Intent(ShowProfileActivity.this, MenuActivity.class);
				                startActivity(i);
							}
						});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_profile, menu);
		return true;
	}

}
