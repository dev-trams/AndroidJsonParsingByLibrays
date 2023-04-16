package com.kbu.exam.jsonbylibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetLottoAsyncTask extends AsyncTask<String, Void, String> {
    Context context;

    public GetLottoAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String line = "";
        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            streamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(streamReader);
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                reader.close();
                streamReader.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return builder.toString();
    }
}
