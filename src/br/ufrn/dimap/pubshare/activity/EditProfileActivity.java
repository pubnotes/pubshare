package br.ufrn.dimap.pubshare.activity;

import java.util.HashMap;

import br.ufrn.dimap.pubshare.domain.Profile;
import br.ufrn.dimap.pubshare.util.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends Activity {
	
	// Session Manager Class
	//usar sempre que precisar pegar info do usuario logado atualmente
    SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		 // Session class instance
        session = new SessionManager(getApplicationContext());
		
		//Aqui devo pegar todas as informacoes, criar um Profile
		
		//Basic information
		EditText e1 = (EditText) findViewById(R.id.einstitution);
		EditText e2 = (EditText) findViewById(R.id.edegree);
		EditText e3 = (EditText) findViewById(R.id.ecountry);
		EditText e4 = (EditText) findViewById(R.id.egender);
		//Personal Information
		EditText e5 = (EditText) findViewById(R.id.ebirthday);
		EditText e6 = (EditText) findViewById(R.id.eaboutme);
		//Contact information
		EditText e7 = (EditText) findViewById(R.id.efacebook);
		EditText e8 = (EditText) findViewById(R.id.eemail);
		EditText e9 = (EditText) findViewById(R.id.ephone);
		
		Profile profile = new Profile();
		
		profile.setInstitution(e1.getText().toString());
		profile.setDegree(e2.getText().toString());
		profile.setLocation(e3.getText().toString());
		profile.setGender(e4.getText().toString());
		profile.setBirthday(e5.getText().toString());
		profile.setAboutme(e6.getText().toString());
		profile.setFacebook(e7.getText().toString());
		profile.setEmail(e8.getText().toString());
		profile.setPhone(e9.getText().toString());
			
		 /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
		
		//acessar o servidor atraves do (username) e atualizar o Profile do user lah
		//Por enquanto usando mock...
        
		findViewById(R.id.btnEditDone).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(EditProfileActivity.this,
								"Updated profile!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(EditProfileActivity.this, ShowProfileActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
					}
				}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

}
