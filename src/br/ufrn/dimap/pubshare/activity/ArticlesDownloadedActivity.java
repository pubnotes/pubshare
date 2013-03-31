package br.ufrn.dimap.pubshare.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.adapters.ArticlesDownloadedListAdapter;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.mocks.ArticlesDownloadedMockFactory;

/**
 * Activity that presents articles downloaded.
 * 
 * @author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 *
 */
public class ArticlesDownloadedActivity extends Activity {

	private static final String TAG = ArticlesDownloadedActivity.class.getSimpleName();
	
	private ArrayAdapter<ArticleDownloaded> adapter;
	
	private ListView downloadsListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articles_downloaded);		
		configureActionBar();	
		List<ArticleDownloaded> downloads = ArticlesDownloadedMockFactory.makeArticleDownloadedList();
		configureListView(downloads);	
	}
	
	private void configureActionBar() {
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);	        
	    }
	}

	private void configureListView( List<ArticleDownloaded>  downloads) {
		
		adapter = new ArticlesDownloadedListAdapter(this, R.layout.row_listview_article_downloaded_list , downloads);
		downloadsListView = (ListView) findViewById(R.id.list_view_articles_downloaded);
		if ( downloadsListView == null ){
			Log.d(this.getClass().getSimpleName(), "Cant find R.layout.row_listview_article_list");
		}
		downloadsListView.setAdapter( adapter );
		registerForContextMenu( downloadsListView );
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {	
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_long_press_article_list, menu);
	    
	    MenuItem menuItem = (MenuItem)menu.findItem(R.id.contextual_menu_download);
	    menuItem.setVisible(false);

	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
			case R.id.contextual_menu_view:
				// view
				return true;
			 
			case R.id.contextual_menu_share:
				// share
				return true;
				
			case R.id.contextual_menu_delete:
				deleteDownloadedFile(info.position );
				return true;
		
		
			default:
				return super.onContextItemSelected(item);
		}

	}

	private void deleteDownloadedFile(int rowIndexInAdapter ) {

		if ( ! adapter.isEmpty() ){
			final ArticleDownloaded articleDownloaded = adapter.getItem( rowIndexInAdapter );
			
			
			  //Ask the user if they want really remove the article
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Confirmation of deletion")
			.setMessage("Do you really want to " + articleDownloaded.getArticle().getTitle() + "?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					// Remove!
					adapter.remove(articleDownloaded);
					adapter.notifyDataSetChanged();
					
					Toast toast = Toast.makeText( getApplicationContext() , "Article '" + articleDownloaded.getArticle().getTitle() +"' was removed", Toast.LENGTH_LONG);
					toast.setGravity( Gravity.BOTTOM| Gravity.CENTER, 0, 0);
					toast.show();  
				}

			})
			.setNegativeButton("No", null)
			.show();	
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		
	    MenuItem menuItem = (MenuItem) menu.findItem( R.id.menu_my_downloads );
	    menuItem.setVisible( false );	  
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, ArticleListActivity.class);
	            // See Using the App Icon for Navigation ref: http://developer.android.com/guide/topics/ui/actionbar.html
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	            
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
 
	}

}
