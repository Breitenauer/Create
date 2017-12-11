package com.project.create;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.create.global.Variables;

public class ProjectActivity extends AppCompatActivity {

    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        Intent myIntent = getIntent();
        code = myIntent.getStringExtra("linkcode");
    }

    public void startProject(View view) {

        Variables.webSocket.ws.send("{\"linkcode\":\"" + code + "\",\"device\":\"IP\",\"start\":\"start\"}");
    }
}
