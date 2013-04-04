package br.ufrn.dimap.pubshare.download.receiver;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

public class ClickDownloadedReceiver extends BroadcastReceiver {

	private static final String TAG = ClickDownloadedReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context ctx, Intent intent) {
		Log.d(TAG, "calling onReceive");
		
		Long downloadKey = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
		
		if ( downloadKey == 0 ){
			return;
		}
		
		DownloadManager dm = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
	
		Query query = new Query();
		query.setFilterById(downloadKey);
		query.setFilterByStatus( DownloadManager.STATUS_SUCCESSFUL );
		
		Cursor cursor = dm.query(query);
		
		if ( cursor.moveToNext() ){
			Log.d(TAG, "Clicked download successful completed.");
		}else{
			Log.d(TAG, "clicking on a download that is not complete. Just let us have patience ...");
		}
				
	}

}
