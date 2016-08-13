package noteoriety.mike73.ie.noteoriety.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 * <p/>
 * Note: Only working with one table for now, so this should be ok.
 * Might need to rethink if future add-ons cause problems.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "noteoriety.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_NAME = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_TEXT = "text";
    public static final String NOTE_TIMESTAMP = "date";

    // SQL statement to create table
    private static final String _SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TITLE + " TEXT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_TIMESTAMP + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    // handy access to all columns in table, might not be needed
    public static final String[] ALL_COLUMNS = {
            NOTE_ID, NOTE_TITLE, NOTE_TEXT, NOTE_TIMESTAMP
    };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(_SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
