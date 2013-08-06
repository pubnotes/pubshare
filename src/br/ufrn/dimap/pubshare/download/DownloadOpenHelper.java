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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DownloadOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    
    public static final String ID = "_id";
    
    public static final String DOWNLOAD_TABLE_NAME = "downloads";
    
    public static final String PATH = "path";

    public static final String MIMETYPE = "mimetype";

    public static final String DOWNLOAD_KEY = "downloadKey";        
    
    public static final String DOWNLOADED_AT = "downloadedAt";        
    
    public static final String SIZE = "size";
    
    public static final String URL_SOURCE = "url_source";
    
    public static final String TITLE = "title"; 
    
    private static final String DOWNLOAD_TABLE_CREATE =
					                "CREATE TABLE " + DOWNLOAD_TABLE_NAME + " (" +
					                 ID  + " integer primary key autoincrement," +
					                 DOWNLOAD_KEY + " REAL," +
					                 PATH + " TEXT, " +
					                 TITLE + " TEXT, " +
					                 MIMETYPE + " TEXT," + 
					                 SIZE + " INTEGER," +
					                 URL_SOURCE + " TEXT," +
					                 DOWNLOADED_AT + " TEXT);";

    DownloadOpenHelper(Context context) {
        super(context, DOWNLOAD_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( DOWNLOAD_TABLE_CREATE );
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   	
    }    
}
