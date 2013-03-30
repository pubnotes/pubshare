package br.ufrn.dimap.pubshare.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;

/**
 * 
 * @author luksrn
 * 
 * @see http://developer.android.com/guide/components/services.html
 */
public class DownloaderService  extends IntentService {
	
	private static final int DOWNLOADER_SERVICE_NOTIFICATION_ID = 1;
	
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
		Log.i( getClass().getSimpleName(), "onHandleIntent on DownloadService");
			
		NotificationManager nManager = (NotificationManager)
				getSystemService(Context.NOTIFICATION_SERVICE);
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_menu_download)
		        .setContentTitle("Download article")
		        .setContentText("Downloading....")
		        .setWhen( System.currentTimeMillis() ); // TODO Add more info
		

		nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, mBuilder.build() );
		
		doSomeWork();
		
		nManager.cancel(DOWNLOADER_SERVICE_NOTIFICATION_ID);
		
		NotificationCompat.Builder mBuilderEnd = new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_menu_download)
		        .setContentTitle("Download complete!")
		        .setContentText("Article xpto.pdf download from IEEE.")
		        .setWhen( System.currentTimeMillis() );
		nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, mBuilderEnd.build() );
	}
	
	private void doSomeWork() {
		   // For while, just sleep for 15 seconds.
	      long endTime = System.currentTimeMillis() + 15*1000;
	      while (System.currentTimeMillis() < endTime) {
	          synchronized (this) {
	              try {
	                  wait(endTime - System.currentTimeMillis());
	              } catch (Exception e) {
	              }
	          }
	      }

		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast toast = Toast.makeText(this, "Starting download in background...", Toast.LENGTH_SHORT);
		toast.setGravity( Gravity.BOTTOM| Gravity.CENTER, 0, 0);
		toast.show();
		return super.onStartCommand(intent, flags, startId);
	}

 

}
