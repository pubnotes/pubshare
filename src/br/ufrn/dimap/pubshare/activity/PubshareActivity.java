package br.ufrn.dimap.pubshare.activity;

import br.ufrn.dimap.pubshare.evaluation.activity.ArticleEvaluationListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Entry point of application. It can be used to perform some processing, 
 * redirecting to some activity by some logic.
 *
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 */
public class PubshareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//When you develop, change this line to start your activity. (Dev Only)
		Intent i = new Intent(this, ArticleEvaluationListActivity.class);        	        
				    
		startActivity(i);
		finish();
	}
}
