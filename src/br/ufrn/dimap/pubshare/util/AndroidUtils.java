package br.ufrn.dimap.pubshare.util;

import android.os.Environment;

public class AndroidUtils {
	
	public static boolean isExternalStorageAvailable(){
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			return true;
		}
		return false;
	}

}
