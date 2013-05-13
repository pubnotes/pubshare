package br.ufrn.dimap.pubshare.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import br.ufrn.dimap.pubshare.util.SessionManager;

public class MenuActivity extends Activity {
	
	// Session Manager Class
	//usar sempre que precisar pegar info do usuario logado atualmente
	SessionManager session;
	Spinner spinner;
	RadioGroup option;
	String searchType;
	EditText campoConsulta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		 // Session class instance
        session = new SessionManager(getApplicationContext());
		
		spinner = (Spinner) findViewById(R.id.sprinnerLibraries);
		campoConsulta = (EditText) findViewById(R.id.search);		

		option = (RadioGroup) findViewById(R.id.radioSearchop);
		option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
				RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
				searchType = checkedRadioButton.getText().toString();
			}
		});
		
        findViewById(R.id.imageButtonsearch).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String textoConsulta = campoConsulta.getText().toString(); 
				if(!textoConsulta.trim().equals("")) {
					Intent i = new Intent(MenuActivity.this, ArticleListActivity.class);
					i.putExtra("textoConsulta", textoConsulta);
					i.putExtra("library", spinner.getSelectedItem().toString());
					i.putExtra("searchType", searchType);
					startActivity(i);
				} else {
					campoConsulta.setError("Demanded field.");
				}
				
			}
		});
        
        findViewById(R.id.imageButton1).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(MenuActivity.this, SearchPeopleActivity.class);
		                startActivity(i);
					}
				});
		
		findViewById(R.id.imageButton2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(MenuActivity.this, ShowFriendsActivity.class);
		                startActivity(i);
					}
				});
		
		findViewById(R.id.imageButton4).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(MenuActivity.this, EditProfileActivity.class);
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
