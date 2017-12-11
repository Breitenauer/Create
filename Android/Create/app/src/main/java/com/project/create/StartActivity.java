package com.project.create;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.create.global.GlobalWebSocket;
import com.project.create.global.Variables;

import java.io.IOException;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        try {
            Variables.webSocket = new GlobalWebSocket(getApplicationContext());
        } catch(Exception e) {
            e.printStackTrace();
        }

        //Variables.webSocket.ws.send("{\"linkcode\":\"XXXX\",\"device\":\"IP\"}");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        }, 2500);
    }

}
