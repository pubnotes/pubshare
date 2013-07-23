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

package br.ufrn.dimap.pubshare.download;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.util.AndroidUtils;

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
		Log.d( TAG , "onHandleIntent on DownloadService");
		

		Article selectedArticle = (Article) intent.getSerializableExtra( Article.KEY_INSTANCE );
		
		
		if ( ! AndroidUtils.isExternalStorageAvailable () ){
			// Generate Menssages
		    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		   			.setSmallIcon( R.drawable.ic_menu_notifications )
					.setContentTitle( getResources().getString(R.string.external_storage_unavailable) )
					.setContentText( getResources().getString( R.string.check_media_availability )); 
			Notification notification = mBuilder.build();
			// Set the Notification as ongoing
			notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL ;
			
			NotificationManager nManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			nManager.notify(0, notification);
			return;
		}
		
		DownloadManager dowloadManager = (DownloadManager) getSystemService( Context.DOWNLOAD_SERVICE ); // since API level 9
		
		Request request = new Request( Uri.parse( selectedArticle.getRemoteLocation() ));
		request.setAllowedNetworkTypes( Request.NETWORK_WIFI | Request.NETWORK_MOBILE );
		request.setTitle( selectedArticle.getTitle() );
		request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS	, selectedArticle.generateFileName() );
		request.setVisibleInDownloadsUi(true);
		
		long enqueue = dowloadManager.enqueue(request);		
		
		Log.d( TAG, "Download enqueue..." + enqueue );
		
		ArticleDownloaded articleDownloaded = new ArticleDownloaded();		
		articleDownloaded.setDownloadKey( enqueue );
		
		DownloadDao downloadDao = new DownloadDao(this);
		downloadDao.insert(articleDownloaded);
		
		Log.d( TAG, "Insert " + articleDownloaded + " in SqLite: OK");			
	}	
 
	 
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast toast = Toast.makeText(this, "Starting download. see action bar to see the progress.", Toast.LENGTH_SHORT);
		toast.setGravity( Gravity.BOTTOM| Gravity.CENTER, 0, 0);
		toast.show();
		return super.onStartCommand(intent, flags, startId);
	}
}
