package noteoriety.mike73.ie.note_oriety.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import noteoriety.mike73.ie.note_oriety.R;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 */
public class WriteNoteActivity extends AppCompatActivity {

    private EditText mTitleTextView;
    private EditText mNoteTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        mTitleTextView = (EditText) findViewById(R.id.titleEditText);
        mNoteTextView = (EditText) findViewById(R.id.noteEditText);
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
