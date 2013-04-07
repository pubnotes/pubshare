/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Foobar is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.ufrn.dimap.pubshare.download.activity;

import java.io.File;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.ArticleListActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.download.adapters.ArticlesDownloadedListAdapter;
import br.ufrn.dimap.pubshare.download.sqlite.DownloadDao;
import br.ufrn.dimap.pubshare.util.AndroidUtils;

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
	
	private DownloadDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articles_downloaded);		
		configureActionBar();	
		dao = new DownloadDao(this);
		List<ArticleDownloaded> downloads = dao.findAll(); 
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
			Log.d( TAG , "Cant find R.layout.row_listview_article_list");
		}
		downloadsListView.setAdapter( adapter );
		downloadsListView.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleDownloaded articleDownloaded = (ArticleDownloaded) parent.getItemAtPosition(position);
                Intent showArticle = new Intent( Intent.ACTION_VIEW );
        		showArticle.setType( "application/pdf" );
        		showArticle.setData( Uri.parse( articleDownloaded.getPathSdCard() ));
        		startActivity( showArticle );
            }
            
        });
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
					.setIcon( android.R.drawable.ic_dialog_alert )
					.setTitle( R.string.confirmation_of_deletion )
					.setMessage( getResources().getString(R.string.do_you_really_want_to,articleDownloaded.getTitle() ) )
					.setPositiveButton( R.string.yes , new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					if ( ! AndroidUtils.isExternalStorageAvailable() ){
						Toast toast = Toast.makeText( getApplicationContext() , 
								getResources().getString(R.string.cant_remove_article )
								, Toast.LENGTH_LONG);
						toast.setGravity( Gravity.BOTTOM | Gravity.CENTER, 0, 0);
						toast.show(); 
						return;
					}
					
					File fileInSd = new File( articleDownloaded.getPathSdCard() );
					if ( fileInSd.exists() ){
						fileInSd.delete();
					}
					dao.remove( articleDownloaded );
					// Remove!
					adapter.remove(articleDownloaded);
					adapter.notifyDataSetChanged();
					
					Toast toast = Toast.makeText( getApplicationContext() , 
							getResources().getString(R.string.article_removed, articleDownloaded.getTitle() )
							, Toast.LENGTH_LONG);
					toast.setGravity( Gravity.BOTTOM | Gravity.CENTER, 0, 0);
					toast.show();  
				}

			})
			.setNegativeButton( R.string.no , null)
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
