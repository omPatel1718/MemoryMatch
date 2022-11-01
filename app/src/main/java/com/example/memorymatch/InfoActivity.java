package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    String orgin = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        //extract String data USERNAME
        orgin = intent.getStringExtra("PREVSCREEN");
    }

    public void back(View view) {
        if(orgin.equals("fromMenu")) {
            Intent intent = new Intent(InfoActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        if(orgin.equals("fromTitle")) {
            Intent intent = new Intent(InfoActivity.this, TitleActivity.class);
            startActivity(intent);
        }
    }
}