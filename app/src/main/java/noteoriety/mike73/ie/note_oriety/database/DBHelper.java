package noteoriety.mike73.ie.note_oriety.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    private static final String DATABASE_NAME = "noteoriety.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_NOTES = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_TEXT = "text";
    public static final String NOTE_TIMESTAMP = "date";

    //SQL to create table
    private static final String _SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TITLE + "TEXT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_TIMESTAMP + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public static final String[] ALL_COLUMNS = {
            NOTE_ID, NOTE_TITLE, NOTE_TEXT, NOTE_TIMESTAMP
    };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(_SQL_CREATE_TABLE);
        Log.d(TAG, "DB created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do nothing for now
        Log.d(TAG, "DB updated");
    }
}
