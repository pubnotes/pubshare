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

package br.ufrn.dimap.pubshare.recomendation;

import java.util.List;

import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityRecommendation extends Activity {

	Context tela;
	
	Button botao;
	
	EditText title;
	EditText author;
	EditText conference;
	EditText link;
	EditText obs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommendation);
		final Article selectedArticle = (Article) getIntent().getSerializableExtra(Article.KEY_INSTANCE);
		
		tela = this;
		
		title = (EditText) findViewById(R.id.editTextTitle);
		author = (EditText) findViewById(R.id.editTextAuthor);
		conference = (EditText) findViewById(R.id.editTextConference);
		link = (EditText) findViewById(R.id.editTextArticle);
		obs = (EditText) findViewById(R.id.editTextObs);
		
		title.setText(selectedArticle.getTitle());
		List<String> autores = selectedArticle.getAuthors();
		String autoresStr = "";
		for(int i =0; i<autores.size(); i++){
			if(i>0)
				autoresStr += "; ";
			autoresStr += autores.get(i);
		}
		author.setText(autoresStr);
		conference.setText(selectedArticle.getEventInformation());
		link.setText(selectedArticle.getDownloadLink());

		
		Button botao = (Button) findViewById(R.id.buttonRecommend);
		botao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent intent = new Intent();
        	   	intent.setClass(ActivityRecommendation.this, ActivityFriends.class);
        	   	if(title.getText().equals(""))
        	   		intent.putExtra("titulo", "Title");
        	   	else
        	   		intent.putExtra("titulo", title.getText());
        	   	
        	   	if(author.getText().equals(""))
        	   		intent.putExtra("autor", "Author");
        	   	else
        	   		intent.putExtra("autor", author.getText());
        	   	
        	   	if(conference.getText().equals(""))
        	   		intent.putExtra("conf", "Conference");
        	   	else
        	   		intent.putExtra("conf", conference.getText());
        	   	
        	   	if(link.getText().equals(""))
        	   		intent.putExtra("link", "Link");
        	   	else
        	   		intent.putExtra("link", link.getText());
        	   	
        	   	if(obs.getText().equals(""))
        	   		intent.putExtra("obs", "Obs");
        	   	else
        	   		intent.putExtra("obs", obs.getText());

        	   	startActivity(intent);
        	   	
//        	   	
//				AlertDialog show = new AlertDialog.Builder(tela)
//		           .setMessage("Recommend to friends or by another application?")
//		           .setCancelable(false)
//		           .setPositiveButton("Friends", new DialogInterface.OnClickListener() {
//		               public void onClick(DialogInterface dialog, int id) {
//		            	   	Intent intent = new Intent();
//		            	   	intent.setClass(ActivityRecommendation.this, ActivityFriends.class);
//		            	   	startActivity(intent);
//		               }
//		           })
//		           .setNeutralButton("Other applications", new DialogInterface.OnClickListener() {
//		               public void onClick(DialogInterface dialog, int id) {				   
//						   Intent sendIntent = new Intent();
//						   sendIntent.setAction(Intent.ACTION_SEND);
//						   sendIntent.putExtra(Intent.EXTRA_TEXT, "Exemplo de artigo a compartilhar. http://www.google.com.br");
//						   sendIntent.setType("text/plain");
//						   startActivity(sendIntent);
//		               }
//		           })
//		           .show();
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