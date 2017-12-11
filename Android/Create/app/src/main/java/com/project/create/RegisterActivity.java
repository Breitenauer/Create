package com.project.create;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.create.entity.Player;
import com.project.create.rest.HttpPostRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private TextView email, password, passwordRepeat;

    private RegisterActivity.UserLoginTask AuthenticationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        passwordRepeat = (EditText) findViewById(R.id.passwordRepeat);
    }

    public void registerNow(View view) {
        email.setError(null);
        password.setError(null);
        passwordRepeat.setError(null);

        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();
        String passwordRepeatInput = passwordRepeat.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(passwordRepeatInput)) {
            if (isEmailValid(emailInput)) {
                if (isPasswordValid(passwordInput)) {
                    if (passwordInput.equalsIgnoreCase(passwordRepeatInput)) {
                        AuthenticationTask = new RegisterActivity.UserLoginTask(emailInput, passwordInput);
                        AuthenticationTask.execute((Void) null);
                    } else {
                        passwordRepeat.setError("Both passwords have to be the same!");
                        focusView = passwordRepeat;
                        cancel = true;
                    }
                } else {
                    password.setError("Use at least 5 letters!");
                    focusView = password;
                    cancel = true;
                }
            } else {
                email.setError("This is not a valid email!");
                focusView = email;
                cancel = true;
            }

        } else if (TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(passwordRepeatInput)) {
            email.setError("This field is required!");
            focusView = email;
            cancel = true;
        } else if (!TextUtils.isEmpty(emailInput) && TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(passwordRepeatInput)) {
            password.setError("This field is required!");
            focusView = password;
            cancel = true;
        } else if (!TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput) && TextUtils.isEmpty(passwordRepeatInput)) {
            passwordRepeat.setError("This field is required!");
            focusView = passwordRepeat;
            cancel = true;
        } else if (!TextUtils.isEmpty(emailInput) && TextUtils.isEmpty(passwordInput) && TextUtils.isEmpty(passwordRepeatInput)) {
            password.setError("This field is required!");
            focusView = password;
            cancel = true;
        } else if (TextUtils.isEmpty(emailInput) && TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(passwordRepeatInput)) {
            email.setError("This field is required!");
            focusView = passwordRepeat;
            cancel = true;
        } else if (!TextUtils.isEmpty(emailInput) && TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(passwordRepeatInput)) {
            email.setError("This field is required!");
            focusView = passwordRepeat;
            cancel = true;
        } else {
            email.setError("This field is required!");
            password.setError("This field is required!");
            passwordRepeat.setError("This field is required!");
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

    public void backToSignIn(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String emailInput, passwordInput;


        public UserLoginTask(String emailInput, String passwordInput) {
            this.emailInput = emailInput;
            this.passwordInput = passwordInput;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String result = null;

            try {
                /*HttpPostRequest getRequest = new HttpPostRequest(new Player(emailInput, passwordInput));
                result = getRequest.execute("localhost:8080/api/create/createNewPlayer").get();*/
                result = doPostRequest("http://vm18.htl-leonding.ac.at:8080/api/create/createUser", new Player(emailInput, passwordInput));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return result.equals("true");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            AuthenticationTask = null;

            if (success) {
                startActivity(new Intent(RegisterActivity.this, LinkcodeActivity.class));
                finish();
            } else {
                email.setError("This e-mail is already registered!");
                email.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            AuthenticationTask = null;
        }
    }

    public String doPostRequest(String stringUrl, Player player) {

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

            String body = "{\"email\":\"" + player.getEmail() + "\",\"password\":\"" + player.getPassword() + "\"}";

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
