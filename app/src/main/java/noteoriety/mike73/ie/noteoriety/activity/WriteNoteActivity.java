package noteoriety.mike73.ie.noteoriety.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import noteoriety.mike73.ie.noteoriety.R;
import noteoriety.mike73.ie.noteoriety.database.DBHelper;
import noteoriety.mike73.ie.noteoriety.database.NoteDataProvider;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 */
public class WriteNoteActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mNoteTextEditText;

    private String mOldTitle;   // used to check if any changes were made when editing note
    private String mOldText;    // used to check if any changes were made when editing note

    private String mAction;     // flag for new note or editing existing note
    private String mNoteFilter; // contains 'WHERE ID=NOTE_ID' for currently loaded note if editing

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        mTitleEditText = (EditText) findViewById(R.id.titleEditText);
        mNoteTextEditText = (EditText) findViewById(R.id.noteEditText);

        // Check if any data was passed (note Uri).
        // If there was, then assume we are editing an existing note
        //      - We pull the note from the DB using passed in ID from Uri
        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(NoteDataProvider.CONTENT_ITEM_TYPE);
        if (uri == null) {
            // brand new note
            mAction = Intent.ACTION_INSERT;
            mTitleEditText.requestFocus();
        } else {
            // loading in existing note
            mAction = Intent.ACTION_EDIT;
            mNoteFilter = DBHelper.NOTE_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri, DBHelper.ALL_COLUMNS, mNoteFilter, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TITLE));
                mTitleEditText.setText(title);
                String text = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TEXT));
                mNoteTextEditText.setText(text);

                cursor.close();

                mOldTitle = title;
                mOldText = text;

                mTitleEditText.setText(title);
                mNoteTextEditText.setText(text);
            }
            mNoteTextEditText.requestFocus();
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
                submitCurrentNote();
                break;
            case R.id.action_clear_current_note:
                clearCurrentNote();
                break;
            default:
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        submitCurrentNote();
    }

    private void submitCurrentNote() {
        String title = mTitleEditText.getText().toString().trim();
        String text = mNoteTextEditText.getText().toString().trim();

        switch (mAction) {
            case Intent.ACTION_INSERT:
                if (title.length() == 0 && text.length() == 0) {
                    setResult(RESULT_CANCELED);
                } else {
                    insertCurrentNote(title, text);
                }
                break;

            case Intent.ACTION_EDIT:
                if (title.length() == 0 && text.length() == 0) {
                    deleteCurrentNote();
                } else if (text.equals(mOldText) && title.equals(mOldTitle)) {
                    setResult(RESULT_CANCELED);
                } else {
                    updateCurrentNote(title, text);
                }
                break;

            default:
        }
        finish();
    }

    private void clearCurrentNote() {
        mTitleEditText.setText("");
        mNoteTextEditText.setText("");
    }

    private void insertCurrentNote(String noteTitle, String noteText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTE_TITLE, noteTitle);
        contentValues.put(DBHelper.NOTE_TEXT, noteText);

        getContentResolver().insert(NoteDataProvider.CONTENT_URI, contentValues);
        Toast.makeText(WriteNoteActivity.this, R.string.noted_added, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void deleteCurrentNote() {
        getContentResolver().delete(NoteDataProvider.CONTENT_URI, mNoteFilter, null);
        Toast.makeText(WriteNoteActivity.this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void updateCurrentNote(String noteTitle, String noteText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTE_TITLE, noteTitle);
        contentValues.put(DBHelper.NOTE_TEXT, noteText);

        getContentResolver().update(NoteDataProvider.CONTENT_URI, contentValues, mNoteFilter, null);
        Toast.makeText(WriteNoteActivity.this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }
}
