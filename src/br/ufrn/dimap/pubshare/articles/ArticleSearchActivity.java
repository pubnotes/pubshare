package br.ufrn.dimap.pubshare.articles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.SearchLibraries;

/**
 * Activity to search articles. 
 * @author itamir
 */
public class ArticleSearchActivity extends Activity {
	
	final int AUTHOR = 1, KEYWORD = 2;

	Button searchButton;
	EditText searchField;
	Spinner librariesOptions;
	RadioGroup opcao;
	int radioType = KEYWORD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_search);
		
		searchField = (EditText) findViewById(R.id.titleText);
		librariesOptions = (Spinner)findViewById(R.id.libsSpinner);
		
		opcao = (RadioGroup) findViewById(R.id.options);
		opcao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
				 RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
				 if(checkedRadioButton.getText().equals("Autor")) 
					 radioType = AUTHOR;
				 else 
					 radioType = KEYWORD;
			}
		});
		
		
		ArrayAdapter<String> libraries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SearchLibraries.getLibraries());
		libraries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		librariesOptions.setAdapter(libraries);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(searchField.getText() != null) {
//					Intent i = new Intent(BuscarArtigosActivity.this, ListarArtigosActivity.class);
//					i.putExtra("titulo", campoBusca.getText().toString());
//					i.putExtra("tipoBusca", tipoBusca);
//					startActivity(i);
					
				}
			}
		});
				
	}


}
