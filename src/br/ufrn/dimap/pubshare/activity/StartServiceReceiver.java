package br.ufrn.dimap.pubshare.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Intent service = new Intent(context, ServiceController.class);
	    context.startService(service);
	}

}
