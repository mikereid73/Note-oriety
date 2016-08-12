package noteoriety.mike73.ie.note_oriety.database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 * Note: Class used to attach data from DB to appropriate components
 */
public class NoteorietyCursorAdapter extends CursorAdapter {

    public NoteorietyCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // return LayoutInflater.from(context).inflate(R.layout.single_note_item, false);
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /*
        String title = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TITLE));
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        titleTextView.setText(title);

        String text = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TEXT));
        TextView textTextView = (TextView) view.findViewById(R.id.titleTextView);
        textTextView.setText(text);
        */
    }
}
