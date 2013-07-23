package br.ufrn.dimap.pubshare.people;

import br.ufrn.dimap.pubshare.activity.R;
import br.ufrn.dimap.pubshare.recomendation.Recommendation_View;
import br.ufrn.dimap.pubshare.recomendation.Recommendation_list;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ServiceController extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}
	ServiceController getService() {
	      return ServiceController.this;
	    }
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		Toast.makeText(ServiceController.this, "Verificando recomendações", Toast.LENGTH_SHORT).show();
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle("You have recommendations on PubShare")
		        .setContentText("You received an article recommendation on PubShare");

		Intent resultIntent = new Intent(this, Recommendation_list.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(Recommendation_View.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(1, mBuilder.build());
		super.stopSelf();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	

	
}
