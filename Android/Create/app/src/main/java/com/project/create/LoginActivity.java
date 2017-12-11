package com.project.create;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.create.entity.Player;
import com.project.create.rest.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView email, password;

    private UserLoginTask AuthenticationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

    }

    public void signIn(View view) {
        email.setError(null);
        password.setError(null);

        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput)) {
            if (isEmailValid(emailInput)) {
                if (isPasswordValid(passwordInput)) {
                    AuthenticationTask = new UserLoginTask(emailInput, passwordInput);
                    AuthenticationTask.execute((Void) null);
                } else {
                    password.setError("This password is incorrect!");
                    focusView = password;
                    cancel = true;
                }
            } else {
                email.setError("This is not a valid email!");
                focusView = email;
                cancel = true;
            }

        } else if (TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput)) {
            email.setError("This field is required!");
            focusView = email;
            cancel = true;
        } else if (!TextUtils.isEmpty(emailInput) && TextUtils.isEmpty(passwordInput)) {
            password.setError("This field is required!");
            focusView = password;
            cancel = true;
        } else {
            email.setError("This field is required!");
            password.setError("This field is required!");
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
    }

    private boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    private boolean isEmailValid(String emailInput) {
        return emailInput.contains("@") && emailInput.contains(".");
    }

    private boolean isPasswordValid(String passwordInput) {
        return passwordInput.length() > 4;
    }

    public void notRegistered(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String emailInput, passwordInput;

        public UserLoginTask(String emailInput, String passwordInput) {
            this.emailInput = emailInput;
            this.passwordInput = passwordInput;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            Boolean check = false;
            ArrayList<Player> datas = new ArrayList<Player>();
            System.out.println("test");

            try {
                String result = doGetRequest("http://vm18.htl-leonding.ac.at:8080/api/create/getAllUser");
                ObjectMapper objectMapper = new ObjectMapper();
                datas = objectMapper.readValue(
                        result,
                        objectMapper.getTypeFactory().constructParametricType(List.class, Player.class)
                );
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Player data : datas) {
                if (data.getEmail() != null && data.getPassword() != null) {
                    if (data.getEmail().equalsIgnoreCase(emailInput)) {
                        if (data.getPassword().equals(passwordInput)) {
                            check = true;
                        }
                    }
                }
            }
            return check;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            AuthenticationTask = null;

            if (success) {
                startActivity(new Intent(LoginActivity.this, LinkcodeActivity.class));
                finish();
            } else {
                password.setError("This password is incorrect!");
                password.requestFocus();
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


