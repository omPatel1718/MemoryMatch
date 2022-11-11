package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    String origin = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        //extract String data USERNAME
        origin = intent.getStringExtra("PREVSCREEN");
    }

    public void back(View view) {
        if(origin.equals("fromMenu")) {
            Intent intent = new Intent(InfoActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        if(origin.equals("fromTitle")) {
            Intent intent = new Intent(InfoActivity.this, TitleActivity.class);
            startActivity(intent);
        }
    }
}