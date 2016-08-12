package noteoriety.mike73.ie.note_oriety.network;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: Michael Reid
 * Date: 12/08/2016
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

    private HttpManager() {
    }

    // My boiler plate code for getting stuff from web
    // Might not need it but no harm to have it ready
    public static String getTextResourceFromWeb(String uri) {
        if (uri == null) {
            throw new NullPointerException("uri must not be null");
        }

        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        return null;
    }

    // My boiler plate code for opening asset files
    // Might not need it but no harm to have it ready
    public static String openTextFileFromAssets(Context context, String filePath) {
        if (context == null || filePath == null) {
            throw new NullPointerException("context and file path must not be null");
        }

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filePath))
            );

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return null;
    }
}
