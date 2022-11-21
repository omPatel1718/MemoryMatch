package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    String origin = "";
    int page = 1;
    TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        //extract String data USERNAME
        origin = intent.getStringExtra("PREVSCREEN");

        infoText = findViewById(R.id.instructions);
    }

    public void pageCheck(){
        if(page==1){
            infoText.setText("Welcome to Memory Match!\nThis game is designed to test your memory.\nWhen you start the game, you'll get 18 cards to match" +
                    " and matching a pair will net you rewards!");
        }else if(page==2){
            infoText.setText("Classic: Cards get you points or more time. Try to make as many ");
        }
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