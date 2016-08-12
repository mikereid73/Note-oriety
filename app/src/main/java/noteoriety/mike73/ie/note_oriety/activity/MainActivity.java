package noteoriety.mike73.ie.note_oriety.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import noteoriety.mike73.ie.note_oriety.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int WRITE_NOTE_REQUEST_CODE = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, WriteNoteActivity.class);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == WRITE_NOTE_REQUEST_CODE && requestCode == RESULT_OK) {
            // activity returned successfully
        }
    }

    private static void startNewActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, WRITE_NOTE_REQUEST_CODE);
    }
}
