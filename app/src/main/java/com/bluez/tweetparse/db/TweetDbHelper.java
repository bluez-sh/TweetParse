package com.bluez.tweetparse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bluez.tweetparse.db.TweetContract.TweetEntry;

/**
 * Created by bluez on 7/10/18.
 */

public class TweetDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TweetEntry.TABLE_NAME + " (" +
                    TweetEntry.COL_TWEETID + " INTEGER PRIMARY KEY, " +
                    TweetEntry.COL_COIN_NAME + " TEXT NOT NULL, " +
                    TweetEntry.COL_COIN_SYMBOL + " TEXT NOT NULL, " +
                    TweetEntry.COL_COIN_HANDLE + " TEXT NOT NULL, " +
                    TweetEntry.COL_TWEET + " TEXT NOT NULL, " +
                    TweetEntry.COL_URL + " TEXT NOT NULL, " +
                    TweetEntry.COL_DATE + " TEXT NOT NULL, " +
                    TweetEntry.COL_KEYWORD + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TweetEntry.TABLE_NAME;


    public TweetDbHelper(Context context) {
        super(context, TweetContract.DB_NAME, null, TweetContract.DB_VERSION);
        Log.d("db", "Database constructor called...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("db", "Table Created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.d("db", "Table Upgraded...");
        onCreate(db);
    }
}
