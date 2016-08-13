package noteoriety.mike73.ie.noteoriety.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 */
public class NoteDataProvider extends ContentProvider {

    private static final String AUTHORITY = "noteoriety.mike73.ie.noteoriety";
    private static final String BASE_DIR = "noteoriety";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_DIR);

    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    public static final String CONTENT_ITEM_TYPE = "note";

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_DIR, NOTES);
        uriMatcher.addURI(AUTHORITY, BASE_DIR + "/#", NOTES_ID);
    }

    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = new DBHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == NOTES_ID) {
            selection = DBHelper.NOTE_ID + "=" + uri.getLastPathSegment();
        }
        return mDatabase.query(
                DBHelper.TABLE_NAME,
                DBHelper.ALL_COLUMNS,
                selection,
                null, null, null, // String[] selectionArgs, String groupBy, String having
                DBHelper.NOTE_TIMESTAMP + " DESC" // order by time
        );
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = mDatabase.insert(DBHelper.TABLE_NAME, null, values);
        return Uri.parse(BASE_DIR + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return mDatabase.delete(DBHelper.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mDatabase.update(DBHelper.TABLE_NAME, values, selection, selectionArgs);
    }
}
