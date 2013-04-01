package br.ufrn.dimap.pubshare.evaluation.activity;

import java.util.List;

import br.ufrn.dimap.pubshare.activity.ArticlesDownloadedActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.adapters.ArticleListAdapter;
import br.ufrn.dimap.pubshare.evaluation.adapters.EvaluationListAdapter;
import br.ufrn.dimap.pubshare.evaluation.domain.Evaluation;
import br.ufrn.dimap.pubshare.evaluation.mocks.EvaluationListMockFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Class responsible for showing the evaluations made for a specific article
 * @author Daniel
 *
 */
public class ArticleEvaluationListActivity extends Activity
{
	private final String TAG = "br.ufrn.dimap.pubshare.activity.ArticleEvaluationListActivity";
	private ListView evaluationsListView;
	private EvaluationListAdapter adapter; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation_article_list);
		setTitle(R.string.title_activity_evaluation_article_list);
		
		List<Evaluation> evals = EvaluationListMockFactory.makeEvaluationList();
		
		adapter = new EvaluationListAdapter(this, R.layout.row_listview_article_evaluation_list , evals);
		
		evaluationsListView = (ListView) findViewById(R.id.list_view_article_evaluation);
		if ( evaluationsListView == null ){
			Log.d(this.getClass().getSimpleName(), "Não foi possível encontrar R.row_listview_article_evaluation_list");
		}
		evaluationsListView.setAdapter( adapter );	
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
			case R.id.menu_my_downloads:
				showMyDownloadActivity();
				return true;	
			default:
		        return super.onOptionsItemSelected(item);
		}
	}
	
	public void showMyDownloadActivity(){
		Log.d(TAG, "Display My Downloads Activity");
		
		Intent intent = new Intent(this, ArticlesDownloadedActivity.class);		
		startActivity(intent);
	}*/
}
