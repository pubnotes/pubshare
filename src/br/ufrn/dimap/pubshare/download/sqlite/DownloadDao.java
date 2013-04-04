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

package br.ufrn.dimap.pubshare.download.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.ufrn.dimap.pubshare.domain.ArticleDownloaded;

/**
 * http://stackoverflow.com/questions/754684/how-to-insert-a-sqlite-rreturn null;ecord-with-a-datetime-set-to-now-in-android-applicatio
 * 
 * @author Lucas Farias
 *
 */
public class DownloadDao {

	// Database fields
	private SQLiteDatabase database; 
	  
	public DownloadDao(Context ctx) {
		DownloadOpenHelper dbHelper = new DownloadOpenHelper(ctx);
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
		
	}
	
	public ArticleDownloaded getById( long downloadId ){
		
		Cursor cursor = database.query( DownloadOpenHelper.DOWNLOAD_TABLE_NAME ,
				new String[] {
					DownloadOpenHelper.DOWNLOAD_KEY,
					DownloadOpenHelper.PATH					
		} , DownloadOpenHelper.DOWNLOAD_KEY + " = " + downloadId ,
			null, null, null, null);
		
		cursor.moveToFirst();
		
		ArticleDownloaded articleDownloaded =  getArticleDownloaded(cursor);
		
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
