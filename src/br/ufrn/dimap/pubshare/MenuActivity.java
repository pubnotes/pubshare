/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    PubShare is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PubShare.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.ufrn.dimap.pubshare;


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
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.articles.ArticleListActivity;
import br.ufrn.dimap.pubshare.download.ArticlesDownloadedActivity;
import br.ufrn.dimap.pubshare.people.EditProfileActivity;
import br.ufrn.dimap.pubshare.people.SearchPeopleActivity;
import br.ufrn.dimap.pubshare.people.ShowFriendsActivity;
import br.ufrn.dimap.pubshare.util.SessionManager;

public class MenuActivity extends Activity {
	
	// Session Manager Class
	//usar sempre que precisar pegar info do usuario logado atualmente
	String searchType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		 // Session class instance
        final SessionManager session = new SessionManager(getApplicationContext());
		
        final Spinner spinner = (Spinner) findViewById(R.id.sprinnerLibraries);
        final EditText campoConsulta = (EditText) findViewById(R.id.search);		

        RadioGroup option = (RadioGroup) findViewById(R.id.radioSearchop);
		searchType = "Title";
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
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
					}
				});
		
		findViewById(R.id.imageButton2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent i = new Intent(MenuActivity.this, ShowFriendsActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
					}
				});
		
		
		findViewById(R.id.imageButton3).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i = new Intent(MenuActivity.this, ArticlesDownloadedActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
