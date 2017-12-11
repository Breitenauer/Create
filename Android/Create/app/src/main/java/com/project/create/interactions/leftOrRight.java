package com.project.create.interactions;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.project.create.R;
import com.project.create.StartActivity;
import com.project.create.global.Variables;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class leftOrRight extends AppCompatActivity {
    ImageView poestlingberg;
    ImageView mariendom;
    boolean state = true;

    private leftOrRight.DidInteraction didInteraction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftorright);
        poestlingberg = (ImageView) findViewById(R.id.poest);
        mariendom = (ImageView) findViewById(R.id.dom);

        mariendom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performChoiceA();
            }
        });

        poestlingberg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performChoiceB();
            }
        });
    }

    private void performChoiceA() {
        System.out.println("Mariendom");
        didInteraction = new leftOrRight.DidInteraction(Variables.timestamp1);
        didInteraction.execute((Void) null);
    }

    private void performChoiceB() {
        System.out.println("Pöstlingberg");
        didInteraction = new leftOrRight.DidInteraction(Variables.timestamp2);
        didInteraction.execute((Void) null);
    }

    public class DidInteraction extends AsyncTask<Void, Void, Boolean> {

        String timestamp;

        public DidInteraction(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String result = null;

            try {
                result = doPostRequest("http://vm18.htl-leonding.ac.at:8080/api/create/madeDecision/" + Variables.gameId, timestamp);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return result.equals("true");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            didInteraction = null;

            if (success) {
                startActivity(new Intent(leftOrRight.this, StartActivity.class));
                finish();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            didInteraction = null;
        }
    }


    public String doPostRequest(String stringUrl, String timestamp) {

        DataOutputStream printout;

        final String REQUEST_METHOD = "POST";
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
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "{\"timestamp\":\"" + timestamp + "\"}";

            byte[] outputInBytes = body.getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write(outputInBytes);
            os.close();

            connection.connect();

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
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
}