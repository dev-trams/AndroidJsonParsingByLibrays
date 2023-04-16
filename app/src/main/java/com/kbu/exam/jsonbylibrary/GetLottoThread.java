package com.kbu.exam.jsonbylibrary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import java.util.Objects;

public class GetLottoThread extends Thread {
    private String baseUrl;
    private StringBuilder builder = new StringBuilder();
    private Context context;
    private String line = "";

    public GetLottoThread(String baseUrl, Context context) {
        this.baseUrl = baseUrl;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "IOException:GetLottoThread:36 [".concat(Objects.requireNonNull(e.getMessage())).concat("]"));
        }

        super.run();
    }

    public String getResult() {
        return builder.toString();
    }
}
