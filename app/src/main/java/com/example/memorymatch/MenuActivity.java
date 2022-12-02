package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    String gameMode, difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gameMode = getIntent().getStringExtra("GAMEMODE");
        difficulty = getIntent().getStringExtra("DIFFICULTY");

        TextView modeText = findViewById(R.id.gameMode);
        modeText.setText("Game Mode: " + gameMode + "\nDifficulty: " + difficulty);
    }

    public void backToGame(View view) {
        finish();
        //Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        //startActivity(intent);

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