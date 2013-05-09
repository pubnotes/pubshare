package br.ufrn.dimap.pubshare.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrn.dimap.pubshare.domain.Profile;
import br.ufrn.dimap.pubshare.domain.User;
import br.ufrn.dimap.pubshare.evaluation.ArticleEvaluationActivity;
import br.ufrn.dimap.pubshare.restclient.SaveEvaluationRestClient;
import br.ufrn.dimap.pubshare.restclient.SaveUserRestClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Aqui devo pegar as informacoes, criar um User 
		
		EditText nameText = (EditText) findViewById(R.id.reg_fullname);
		EditText mailText = (EditText) findViewById(R.id.reg_email);
		EditText passwordText = (EditText) findViewById(R.id.reg_password);
		
		//User estah se registrando, ainda com profile vazio, tags e amigos vazios
		user = new User();
		user.setUsername(nameText.getText().toString());
		user.setUseremail(mailText.getText().toString());
		user.setPassword(passwordText.getText().toString());
		
		//e mandar pra o servidor
		
		findViewById(R.id.btnRegister).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						SaveUserRestClient rest = new SaveUserRestClient();
						rest.execute(RegisterActivity.this.user);
						
						Toast.makeText(RegisterActivity	.this,
								"Registered account successfully!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
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
