package br.ufrn.dimap.pubshare.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;

/**
 * Service responsible for downloading articles from online libraries
 * 
 * @author luksrn
 * 
 * @see http://developer.android.com/guide/components/services.html
 * @see http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
 * 
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

		nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID,
				buildNotification("Download article", "Downloading....") );
		
		downloadArticle();
		
		nManager.cancel(DOWNLOADER_SERVICE_NOTIFICATION_ID);
	 
		nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, buildNotification("Download complete!","Article xpto.pdf download from IEEE.") );
	}
	
	private Notification buildNotification(String title, String text){
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_menu_download)
		        .setContentTitle( title )
		        .setContentText( text )
		        .setWhen( System.currentTimeMillis() ); // TODO Add more info
		return mBuilder.build();
	}
	
	private void downloadArticle() {
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
