package br.ufrn.dimap.pubshare.download.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ClickDownloadedReceiver extends BroadcastReceiver {

	private static final String TAG = ClickDownloadedReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context ctx, Intent intent) {
		Log.d(TAG, "calling onReceive");
		
	}

}
