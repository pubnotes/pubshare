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
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

/**
 * 
 * @author Lucas Oliveira
 *
 */
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
		query.setFilterById( downloadKey );
		query.setFilterByStatus( DownloadManager.STATUS_SUCCESSFUL );
		
		Cursor cursor = dm.query(query);
		
		if ( cursor.moveToNext() ){
			Log.d(TAG, "Clicked download successful completed.");
		}else{
			Log.d(TAG, "clicking on a download that is not complete. Just let us have patience ...");
		}				
	}
}
