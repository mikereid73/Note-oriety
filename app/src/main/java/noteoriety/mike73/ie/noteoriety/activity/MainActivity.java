package noteoriety.mike73.ie.noteoriety.activity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import noteoriety.mike73.ie.noteoriety.R;
import noteoriety.mike73.ie.noteoriety.database.NoteDataProvider;
import noteoriety.mike73.ie.noteoriety.database.NoteorietyCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int WRITE_NOTE_REQUEST_CODE = 333;

    private CursorAdapter mNoteCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteCursorAdapter = new NoteorietyCursorAdapter(this);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(mNoteCursorAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WriteNoteActivity.class);
                Uri uri = Uri.parse(NoteDataProvider.CONTENT_URI + "/" + id);
                intent.putExtra(NoteDataProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, WRITE_NOTE_REQUEST_CODE);
            }
        });

        // This ugly button is temporary and will be replaced with
        // something a lot prettier. Needed some way of changing to write note activity for now
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WriteNoteActivity.class);
                startActivityForResult(intent, WRITE_NOTE_REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete_all_notes:
                deleteAllNotes();
                break;
            case R.id.action_view_about:
                Toast.makeText(MainActivity.this, R.string.author_details, Toast.LENGTH_LONG).show();
                break;

            default:
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WRITE_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            restartLoader();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                NoteDataProvider.CONTENT_URI,
                null, null, // String[] projection, String selection
                null, null //  String[] selectionArgs, String sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mNoteCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNoteCursorAdapter.swapCursor(null);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    private void deleteAllNotes() {
        if (mNoteCursorAdapter.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    R.string.no_notes_to_delete,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {

                            getContentResolver().delete(NoteDataProvider.CONTENT_URI, null, null);
                            restartLoader();
                            Toast.makeText(MainActivity.this,
                                    R.string.all_notes_deleted,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure)
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }
}
