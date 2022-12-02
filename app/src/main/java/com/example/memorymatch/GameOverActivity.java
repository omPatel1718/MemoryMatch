package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    String gameMode, difficulty;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        gameMode = intent.getStringExtra("MODE");
        difficulty = intent.getStringExtra("DIFFICULT");
        score = intent.getIntExtra("SCORE", 0);

        TextView scoreText = findViewById(R.id.score);
        scoreText.setText("Score: " + score + " points");
        TextView modeText = findViewById(R.id.gameMode);
        modeText.setText("GameMode: " + gameMode);
    }

    public void quitToTitle(View view) {
        Intent intent = new Intent(GameOverActivity.this, TitleActivity.class);
        startActivity(intent);
    }

    public void playAgain(View view){
        Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
        intent.putExtra("GAMEMODE", gameMode);
        intent.putExtra("DIFFICULTY", difficulty);
        startActivity(intent);
    }
}