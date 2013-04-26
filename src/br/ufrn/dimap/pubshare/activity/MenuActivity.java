package br.ufrn.dimap.pubshare.activity;


import br.ufrn.dimap.pubshare.util.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends Activity {
	
	// Session Manager Class
	//usar sempre que precisar pegar info do usuario logado atualmente
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		 // Session class instance
        session = new SessionManager(getApplicationContext());
		
		findViewById(R.id.imageButton1).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(getApplicationContext(), SearchPeopleActivity.class);
		                startActivity(i);
					}
				});
		
		findViewById(R.id.imageButton2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(getApplicationContext(), ShowFriendsActivity.class);
		                startActivity(i);
					}
				});
		
		findViewById(R.id.imageButton4).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
		                startActivity(i);
					}
				});
		
		
		
		findViewById(R.id.logout_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						session.logoutUser();
					}
				});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
