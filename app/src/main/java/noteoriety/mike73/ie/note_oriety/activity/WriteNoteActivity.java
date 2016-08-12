package noteoriety.mike73.ie.note_oriety.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import noteoriety.mike73.ie.note_oriety.R;
import noteoriety.mike73.ie.note_oriety.database.DBHelper;
import noteoriety.mike73.ie.note_oriety.database.NoteDataProvider;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 */
public class WriteNoteActivity extends AppCompatActivity {

    private static final String TAG = "WriteNoteActivity";
    private EditText mTitleTextView;
    private EditText mNoteTextView;

    private String mNoteFilter; // contains 'WHERE ID=NOTE_ID' for currently loaded note if editing
    private String mOldTitle;   // used to check if any changes were made when editing note
    private String mOldText;    // used to check if any changes were made when editing note

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        mTitleTextView = (EditText) findViewById(R.id.titleEditText);
        mNoteTextView = (EditText) findViewById(R.id.noteEditText);

        // Check if any data was passed (note Uri).
        // If there was, then assume we are editing an existing note
        //      - We pull the note from the DB using passed in ID from Uri
        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(NoteDataProvider.CONTENT_ITEM_TYPE);
        if(uri == null) {
            // brand new note
            Log.wtf(TAG, "TODO");
        } else {
            // loading in existing note
            String noteFilter = DBHelper.NOTE_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri, DBHelper.ALL_COLUMNS, noteFilter, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TITLE));
                mTitleTextView.setText(title);
                String text = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TEXT));
                mNoteTextView.setText(text);

                cursor.close();

                mOldTitle = title;
                mOldText = text;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                break;
            case R.id.action_clear_current_note:
                clearCurrentNote();
                break;
            case R.id.action_delete_note:
                break;
            default:
        }

        return true;
    }

    private void clearCurrentNote() {
        mTitleTextView.setText("");
        mNoteTextView.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
