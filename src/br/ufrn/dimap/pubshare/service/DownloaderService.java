package br.ufrn.dimap.pubshare.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;

/**
 * Service responsible for downloading articles from online libraries
 * 
 * @author luksrn
 * 
 * @see http://developer.android.com/guide/components/services.html
 * @see http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
 * @see http://developer.android.com/training/notify-user/display-progress.html
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
			
		NotificationManager nManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder notificationBuilder = buildNotification("Research on the Virtual Reality Simulation Engine", "Downloading...." );
		
		String urlToDownload = intent.getStringExtra( Article.KEY_REMOTE_URL );

		try {
			URL url = new URL(urlToDownload);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100% progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			//  OutputStream output = new FileOutputStream("/sdcard/...");

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				
				// publishing the progress....
				int progress = (int) (total * 100 / fileLength);				
				notificationBuilder.setProgress(100, progress, false);
				notificationBuilder.setContentText("Downloading article from IEEE... " + progress + "%");
				nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, notificationBuilder.build() );
 
				 try {
                      Thread.sleep(50);
                 } catch (InterruptedException e) {
                     Log.d( "dev", "sleep failure");
                 }

				 
			}

			  // When the loop is finished, updates the notification
			notificationBuilder.setContentText("Download complete!")
                    .setProgress(0,0,false)  // Removes the progress bar
                    .setContentText("Article 'Research on the Virtual Reality Simulation Engine' download from IEEE. Click to view")                    
		        .setWhen( System.currentTimeMillis() );
			// publish
			nManager.notify( DOWNLOADER_SERVICE_NOTIFICATION_ID , notificationBuilder.build());

            
			// output.flush();
			// output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	 
	}
	
	private NotificationCompat.Builder buildNotification(String title, String text){
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		        .setSmallIcon( R.drawable.ic_menu_download )
		        .setContentTitle( title )
		        .setContentText( text ); // TODO Add more info
		return mBuilder;
	}
	
 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast toast = Toast.makeText(this, "Starting download. see action bar to see the progress.", Toast.LENGTH_SHORT);
		toast.setGravity( Gravity.BOTTOM| Gravity.CENTER, 0, 0);
		toast.show();
		return super.onStartCommand(intent, flags, startId);
	}

 

}
