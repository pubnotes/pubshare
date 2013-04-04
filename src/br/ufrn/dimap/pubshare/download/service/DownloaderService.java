package br.ufrn.dimap.pubshare.download.service;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.download.sqlite.DownloadDao;

/**
 * Service responsible for downloading articles from online libraries
 * 
 *	@author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 * 
 */
public class DownloaderService  extends IntentService {
	
	private static final String TAG = DownloaderService.class.getSimpleName();
		
	public DownloaderService() {
		super("DownloaderService");
	}

	/**
	* The IntentService calls this method from the default worker thread with
	* the intent that started the service. When this method returns, IntentService
	* stops the service, as appropriate.
	*/
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i( TAG , "onHandleIntent on DownloadService");
		
		if ( ! isExternalStorageAvailable () ){
			// Generate Menssages
			return;
		}
		
		DownloadManager dowloadManager = (DownloadManager) getSystemService( Context.DOWNLOAD_SERVICE ); // since API level 9
		
		Article selectedArticle = (Article) intent.getSerializableExtra( Article.KEY_INSTANCE );
		
		Request request = new Request( Uri.parse( selectedArticle.getRemoteLocation() ));
		request.setAllowedNetworkTypes( Request.NETWORK_WIFI | Request.NETWORK_MOBILE );
		request.setTitle( selectedArticle.getTitle() );
		//request.setNotificationVisibility( Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
		request.setDestinationInExternalFilesDir( this, Environment.DIRECTORY_DOWNLOADS	, selectedArticle.generateFileName() );
		
		long enqueue = dowloadManager.enqueue(request);
		
		
		Log.d( TAG, "Download enqueue..." + enqueue );
		
		ArticleDownloaded articleDownloaded = new ArticleDownloaded();		
		articleDownloaded.setDownloadKey( enqueue );
		articleDownloaded.setPathSdCard( getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + selectedArticle.generateFileName() );
		
		DownloadDao downloadDao = new DownloadDao(this);
		downloadDao.insert(articleDownloaded);
		
		Log.d( TAG, "Insert ArticleDownloaded in SqLite: OK");			
	}	
 
	private boolean isExternalStorageAvailable(){
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			return true;
		}
		return false;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast toast = Toast.makeText(this, "Starting download. see action bar to see the progress.", Toast.LENGTH_SHORT);
		toast.setGravity( Gravity.BOTTOM| Gravity.CENTER, 0, 0);
		toast.show();
		return super.onStartCommand(intent, flags, startId);
	}
}
