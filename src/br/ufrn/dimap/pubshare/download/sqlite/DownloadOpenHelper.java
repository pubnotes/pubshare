package br.ufrn.dimap.pubshare.download.sqlite;

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
    
    private static final String DOWNLOAD_TABLE_CREATE =
					                "CREATE TABLE " + DOWNLOAD_TABLE_NAME + " (" +
					                 ID  + " integer primary key autoincrement," +
					                 DOWNLOAD_KEY + " REAL," +
					                 PATH + " TEXT, " +
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
