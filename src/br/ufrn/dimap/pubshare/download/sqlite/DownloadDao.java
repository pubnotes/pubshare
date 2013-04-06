package br.ufrn.dimap.pubshare.download.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;

/**
 * http://stackoverflow.com/questions/754684/how-to-insert-a-sqlite-rreturn null;ecord-with-a-datetime-set-to-now-in-android-applicatio
 * 
 * @author Lucas Farias
 *
 */
public class DownloadDao {

	private static final String TAG = DownloadDao.class.getSimpleName();
	
	// Database fields
	private SQLiteDatabase database;
	private DownloadOpenHelper dbHelper;
	  
	public DownloadDao(Context ctx) {
		dbHelper = new DownloadOpenHelper(ctx);
		database = dbHelper.getWritableDatabase();		
	}
	  
	public void insert( ArticleDownloaded download ){
		ContentValues values = new ContentValues();
		values.put( DownloadOpenHelper.DOWNLOAD_KEY , download.getDownloadKey() );
		values.put( DownloadOpenHelper.PATH , download.getPathSdCard() );		
		//values.put( DownloadOpenHelper.DOWNLOADED_AT , "datetime()" );		
		
		database.insert( DownloadOpenHelper.DOWNLOAD_TABLE_NAME , null , values);
	}		
	
	public void update( ArticleDownloaded download ){
		ContentValues values = new ContentValues();
		values.put( DownloadOpenHelper.URL_SOURCE , download.getUrlSource() );
		values.put( DownloadOpenHelper.SIZE , download.getSize() );
		values.put( DownloadOpenHelper.DOWNLOADED_AT , "getdate()" );
		values.put( DownloadOpenHelper.PATH , download.getPathSdCard() );
		values.put( DownloadOpenHelper.TITLE , download.getTitle() );
		 
		database.update( DownloadOpenHelper.DOWNLOAD_TABLE_NAME ,
				values, 
				DownloadOpenHelper.ID + " = ?",
				new String [] { Long.toString(download.getDownloadKey()) });
	}
	
	public ArticleDownloaded getById( long downloadId ){
		
		Log.d(TAG, "getById( " + downloadId + ")");
		
		Cursor cursor = database.query( DownloadOpenHelper.DOWNLOAD_TABLE_NAME ,
				new String[] {
					DownloadOpenHelper.DOWNLOAD_KEY,
					DownloadOpenHelper.PATH					
		} , DownloadOpenHelper.DOWNLOAD_KEY + " = " + downloadId ,
			null, null, null, null);
		
		ArticleDownloaded articleDownloaded = null;
		if ( cursor.moveToFirst() ){
			articleDownloaded =  getArticleDownloaded(cursor);			
		}
				
		cursor.close();
		
		return articleDownloaded; 		
	}
	
	private ArticleDownloaded getArticleDownloaded( Cursor cursor ){
		ArticleDownloaded articleDownloaded = new ArticleDownloaded();
		articleDownloaded.setDownloadKey( cursor.getInt(0) );
		articleDownloaded.setPathSdCard( cursor.getString(1) );
		
		return articleDownloaded;
	}
}
