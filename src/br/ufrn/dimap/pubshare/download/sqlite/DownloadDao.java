package br.ufrn.dimap.pubshare.download.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;
import br.ufrn.dimap.pubshare.util.DateFormat;

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
		
		Log.d(TAG, "update( " + download + ")");
		
		ContentValues values = new ContentValues();
		values.put( DownloadOpenHelper.URL_SOURCE , download.getUrlSource() );
		values.put( DownloadOpenHelper.SIZE , download.getSize() );
		values.put( DownloadOpenHelper.DOWNLOADED_AT , DateFormat.iso8601Format( new Date() ) );
		values.put( DownloadOpenHelper.PATH , download.getPathSdCard() );
		values.put( DownloadOpenHelper.TITLE , download.getTitle() );
		 
		database.update( DownloadOpenHelper.DOWNLOAD_TABLE_NAME ,
				values, 
				DownloadOpenHelper.ID + " = ?",
				new String [] { Long.toString(download.getId() ) });
	}
	
	public ArticleDownloaded getById( long downloadId ){
		
		Log.d(TAG, "getById( " + downloadId + ")");
		
		Cursor cursor = database.query( DownloadOpenHelper.DOWNLOAD_TABLE_NAME ,
				new String[] {
					DownloadOpenHelper.ID,
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
	
	public List<ArticleDownloaded> findAll() {
		String[] queryColumns = {
				DownloadOpenHelper.ID,
				DownloadOpenHelper.DOWNLOAD_KEY,
				DownloadOpenHelper.TITLE , 				
				DownloadOpenHelper.DOWNLOADED_AT, 
				DownloadOpenHelper.SIZE , 
				DownloadOpenHelper.URL_SOURCE,
				DownloadOpenHelper.PATH
		};
		
		Cursor cursor = database.query( DownloadOpenHelper.DOWNLOAD_TABLE_NAME, 
				queryColumns, 
				null,
				null, 
				null, 
				null, 
				DownloadOpenHelper.DOWNLOADED_AT + " DESC", 
				null);
		List<ArticleDownloaded> downloads = new ArrayList<ArticleDownloaded>();
		
		while ( cursor.moveToNext() ){
			int id = cursor.getInt( cursor.getColumnIndex( DownloadOpenHelper.ID ) );
			int downloadKey = cursor.getInt( cursor.getColumnIndex( DownloadOpenHelper.DOWNLOAD_KEY ) );
			String title = cursor.getString( cursor.getColumnIndex( DownloadOpenHelper.TITLE ) );
			String path = cursor.getString( cursor.getColumnIndex( DownloadOpenHelper.PATH ) );
			String date = cursor.getString( cursor.getColumnIndex( DownloadOpenHelper.DOWNLOADED_AT ));
			String url = cursor.getString( cursor.getColumnIndex( DownloadOpenHelper.URL_SOURCE ));
			long size = cursor.getLong( cursor.getColumnIndex( DownloadOpenHelper.SIZE ));
			
			ArticleDownloaded article = new ArticleDownloaded();
			article.setId( id );
			article.setTitle( title );
			article.setDownloadKey( downloadKey );
			article.setSize( size );
			article.setPathSdCard( path );
			article.setDigitalLibrary("<TODO>");
			article.setUrlSource(url);
			article.setDownloadedAt( DateFormat.iso8601Format( date ) );
			
			downloads.add( article );			
		}
		
		return downloads;
	}

	private ArticleDownloaded getArticleDownloaded( Cursor cursor ){
		ArticleDownloaded articleDownloaded = new ArticleDownloaded();
		
		int idIndex = cursor.getColumnIndex( DownloadOpenHelper.ID );
		int downloadKeyIndex =  cursor.getColumnIndex(DownloadOpenHelper.DOWNLOAD_KEY) ;
		int pathIndex = cursor.getColumnIndex( DownloadOpenHelper.PATH );
		
		articleDownloaded.setId( cursor.getInt( idIndex ));
		articleDownloaded.setDownloadKey( cursor.getLong( downloadKeyIndex ) );
		articleDownloaded.setPathSdCard( cursor.getString( pathIndex ) );
		
		return articleDownloaded;
	}

	
}
