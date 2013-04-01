package br.ufrn.dimap.pubshare.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import br.ufrn.dimap.pubshare.activity.ArticlesDownloadedActivity;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.Article;

/**
 * Service responsible for downloading articles from online libraries
 * 
 *	@author Lucas Farias de Oliveira <i>luksrn@gmail.com</i>
 * 
 * @see http://developer.android.com/guide/components/services.html
 * @see http://developer.android.com/guide/topics/ui/notifiers/notifications.html Displaying a fixed-duration progress indicator 
 * @see http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog  
 * @see http://stackoverflow.com/questions/10508498/is-this-a-stock-android-notification-remoteview-layout to keep compatible with android < 3
 * 
 */
public class DownloaderService  extends IntentService {
	
	private static final String TAG = DownloaderService.class.getSimpleName();
	
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

		if ( !isExternalStorageAvailable() ){
			NotificationCompat.Builder notificationBuilder = notificationBuilder("External storage is unavailable", "Check media availability" ,R.drawable.ic_menu_notifications );
			nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, notificationBuilder.build() );
			return;
		}
		

		Article selectedArticle = (Article) intent.getSerializableExtra( Article.KEY_INSTANCE );
		
		NotificationCompat.Builder notificationBuilder = notificationBuilder( selectedArticle.getTitle() , "Downloading...." , R.drawable.ic_menu_download);
		
		try {
			URL url = new URL( selectedArticle.getRemoteLocation() );
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100% progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			String outputFile =  getExternalStorePath() + selectedArticle.generateFileName() ;
			OutputStream output = new FileOutputStream(outputFile);

			byte data[] = new byte[ 1024 * 8 ];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				
				output.write(data, 0, count);
				// publishing the progress....
				int progress = (int) (total * 100 / fileLength);				
				notificationBuilder.setProgress(100, progress, false); 
				
				nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, notificationBuilder.build() );			 
			}
			
			Log.i(getClass().getName(), "File write at " + getExternalStorePath() );

			output.flush();
			output.close();
			input.close();

			  // When the loop is finished, updates the notification
			notificationBuilder.setContentText("Download complete!")
                    .setProgress(0,0,false)  // Removes the progress bar
                    .setContentText("Article '" +  selectedArticle.getTitle() + "' download from IEEE. Click to view")                    
		        .setWhen( System.currentTimeMillis() );
			
			
			// Creates an explicit intent for an Activity in your app
			// A status bar notification should be used for any case in which a background service needs to alert 
			//the user about an event that requires a response. A background service should never launch an activity 
			//on its own in order to receive user interaction. The service should instead create a status bar notification 
			//that will launch the activity when selected by the user.
			Intent resultIntent = new Intent(Intent.ACTION_VIEW);
			resultIntent.setType("application/pdf");
			
			List<ResolveInfo> resolvers = getPackageManager().queryIntentActivities(resultIntent, PackageManager.MATCH_DEFAULT_ONLY);
			if ( !resolvers.isEmpty() ){
				File fileInDisk = new File(outputFile);
	            intent.setData( Uri.fromFile(fileInDisk) );
	            
	            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0 ); //
	            notificationBuilder.setContentIntent(pendingIntent);
			}
			
		 
			// publish
	
			nManager.notify( DOWNLOADER_SERVICE_NOTIFICATION_ID , notificationBuilder.build());

		} catch (IOException e) {
			Log.e( TAG , "Error: " + e.getMessage() );
			notificationBuilder = notificationBuilder("An error occurred while writing the file.",
					"Something wrong just happened" ,
					R.drawable.ic_menu_notifications );
			nManager.notify(DOWNLOADER_SERVICE_NOTIFICATION_ID, notificationBuilder.build() );
		}

	 
	}
	
	// TODO See port to 2.3.3
	/**
	 * Note: Except where noted, this guide refers to the NotificationCompat.Builder class 
	 * in the version 4 Support Library. The class Notification.Builder was added in Android 3.0. 
	 * 
	 * @param title
	 * @param text
	 * @return
	 */
	private NotificationCompat.Builder notificationBuilder(String title, String text, int icon){
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		        .setSmallIcon( icon )
		        .setContentTitle( title )
		        .setContentText( text ); // TODO Add more info
		return mBuilder;
	}
	

 
	private boolean isExternalStorageAvailable(){
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			return true;
		}
		return false;
	}
	
	private String getExternalStorePath(){
		File rootExternalStorage = Environment.getExternalStorageDirectory();
		
		return rootExternalStorage.getAbsolutePath() + File.separator + "Download" + File.separator;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast toast = Toast.makeText(this, "Starting download. see action bar to see the progress.", Toast.LENGTH_SHORT);
		toast.setGravity( Gravity.BOTTOM| Gravity.CENTER, 0, 0);
		toast.show();
		return super.onStartCommand(intent, flags, startId);
	}
}
