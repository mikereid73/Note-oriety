package noteoriety.mike73.ie.note_oriety.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import noteoriety.mike73.ie.note_oriety.R;

public class MainActivity extends AppCompatActivity {

    private Button mNewNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewNoteButton = (Button) findViewById(R.id.button);
        mNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, WriteNoteActivity.class);
            }
        });
    }

    private static void startNewActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
