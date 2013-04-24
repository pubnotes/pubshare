package br.ufrn.dimap.pubshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findViewById(R.id.btnRegister).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(RegisterActivity	.this,
								"Registered account successfully!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(getApplicationContext(), LoginActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
					}
				}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
