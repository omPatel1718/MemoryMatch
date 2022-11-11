package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    String gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gm = getIntent().getStringExtra("GAMEMODE");

        TextView modeText = findViewById(R.id.gameMode1);
        String text = "Game Mode: " + gm;
        modeText.setText(text);
    }

    public void backToGame(View view) {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void quitToTitle(View view) {
        Intent intent = new Intent(MenuActivity.this, TitleActivity.class);
        startActivity(intent);
    }

    public void instructions(View view) {
        Intent intent = new Intent(MenuActivity.this, InfoActivity.class);
        startActivity(intent);
        intent.putExtra("PREVSCREEN", "fromMenu");
    }
}