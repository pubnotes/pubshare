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

package br.ufrn.dimap.pubshare.download.receiver;

import java.util.Date;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.download.sqlite.DownloadDao;

/**
 * 
 * @author Lucas Oliveira
 *
 */
public class DownloadCompleteReceiver extends BroadcastReceiver{

	private static final String TAG = DownloadCompleteReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "calling onReceive");
		
		Long downloadKey = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
		
		if ( downloadKey == 0 ){
			return;
		}
		
		DownloadManager dm = (DownloadManager) context.getSystemService( Context.DOWNLOAD_SERVICE );
		
		Query query = new Query();
		query.setFilterById(downloadKey);
		query.setFilterByStatus( DownloadManager.STATUS_SUCCESSFUL );
 		
		Cursor cursor = dm.query(query);		
 
		if ( cursor.moveToFirst() ){
	 
			DownloadDao downloadDao = new DownloadDao(context);
			ArticleDownloaded articleDownloaded = downloadDao.getById( downloadKey );
			if ( articleDownloaded == null ){
				return;
			}

			articleDownloaded = populeArticleDownloadedFromCursor(articleDownloaded, cursor);
	 
			downloadDao.update( articleDownloaded );
			
			Log.i(TAG, "Article found and created for key " + downloadKey + " saved at " + articleDownloaded );
			
			notifyDownloadComplete(context, articleDownloaded);

		}else{
			Log.i(TAG, "Article not found for key " + downloadKey );
		}							
	}

	private ArticleDownloaded populeArticleDownloadedFromCursor(ArticleDownloaded articleDownloaded, Cursor cursor ) {
		// Find the column indexes for the data we require.
		int descriptionIdx = cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION);
		int titleIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE);
		int fileSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);		 
		int uriToBeDownloaded = cursor.getColumnIndex( DownloadManager.COLUMN_URI );
		int localPathIdx = cursor.getColumnIndex( DownloadManager.COLUMN_LOCAL_URI );
		 
		articleDownloaded.setTitle( cursor.getString( titleIdx ) );
		articleDownloaded.setDigitalLibrary( cursor.getString( descriptionIdx ) );
		articleDownloaded.setPathSdCard( cursor.getString( localPathIdx ) );
		articleDownloaded.setDownloadedAt( new Date() );
		articleDownloaded.setSize( cursor.getLong(fileSizeIdx));
		articleDownloaded.setUrlSource( cursor.getString( uriToBeDownloaded ) );
		return articleDownloaded; 
	}

	private void notifyDownloadComplete(Context context, ArticleDownloaded articleDownloaded) {
		// Create an Intent that will open the main Activity
		// if the notification is clicked.
		Intent showArticle = new Intent( Intent.ACTION_VIEW);
		showArticle.setType( "application/pdf" );
		showArticle.setData( Uri.parse( articleDownloaded.getPathSdCard() ));
		PendingIntent pi = PendingIntent.getActivity(context, 1, showArticle, 0);
		// Set the Notification UI parameters
		Notification notification = new Notification(R.drawable.ic_menu_download,
					"Open " + articleDownloaded.getTitle() , System.currentTimeMillis());
		notification.setLatestEventInfo(context, articleDownloaded.getTitle() , context.getResources().getString( R.string.download_complete ) , pi);
		// Set the Notification as ongoing
		notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
		
		NotificationManager nManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(0, notification);
	}		 

}
