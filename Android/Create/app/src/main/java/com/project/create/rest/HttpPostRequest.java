package com.project.create.rest;

import android.os.AsyncTask;

import com.project.create.entity.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpPostRequest extends AsyncTask<String, Void, String> {

    private Object object;

    DataOutputStream printout;
    DataInputStream input;

    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 3000;
    public static final int CONNECTION_TIMEOUT = 3000;

    public HttpPostRequest(Object object) {
        this.object = object;
    }

    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        String result;
        String inputLine;
        try {

            URL myUrl = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Content-Type","application/json");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("object", object);

            printout = new DataOutputStream(connection.getOutputStream ());
            printout.writeBytes(URLEncoder.encode(jsonParam.toString(),"UTF-8"));
            printout.flush ();
            printout.close ();

            connection.connect();

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}