package com.project.create;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.create.global.Variables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinkcodeActivity extends AppCompatActivity {

    private EditText email, passwordRepeat, linkcode;
    private LinkcodeActivity.CompareLinkcodeTask AuthenticationTask;

    String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkcode);

        email = (EditText) findViewById(R.id.email);
        passwordRepeat = (EditText) findViewById(R.id.passwordRepeat);

        email.setVisibility(View.INVISIBLE);
        passwordRepeat.setVisibility(View.INVISIBLE);

        linkcode = (EditText) findViewById(R.id.linkcode);

    }

    public void connectToAppleTV(View view) {
        code = linkcode.getText().toString();

        if (code.length() == 4 && code.matches("[0-9]+")) {
            AuthenticationTask = new LinkcodeActivity.CompareLinkcodeTask(code);
            AuthenticationTask.execute((Void) null);
        } else {
            linkcode.setError("Not a valid linkcode!");
        }
    }
    public class CompareLinkcodeTask extends AsyncTask<Void, Void, Boolean> {

        private final String code;

        public CompareLinkcodeTask(String code) {
            this.code = code;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String result = "false";

            try {
                result = doGetRequest("http://vm18.htl-leonding.ac.at:8080/api/create/linkcode/" + code);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.equals("true");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            AuthenticationTask = null;

            if (success) {
                String message = "{\"linkcode\":\"" + code + "\",\"device\":\"IP\"}";
                Variables.webSocket.ws.send(message);
                Intent myIntent = new Intent(LinkcodeActivity.this, ProjectActivity.class);
                myIntent.putExtra("linkcode",code);
                startActivity(myIntent);
                finish();
            } else {
                linkcode.setError("This linkcode is incorrect!");
            }
        }

        @Override
        protected void onCancelled() {
            AuthenticationTask = null;
        }
    }

    public String doGetRequest(String stringUrl) {
        final String REQUEST_METHOD = "GET";
        final int READ_TIMEOUT = 3000;
        final int CONNECTION_TIMEOUT = 3000;

        String result;
        String inputLine;

        try {

            URL myUrl = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
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
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }

        return result;

    }
}