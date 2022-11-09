package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


    }

    public void quitToTitle(View view) {
        Intent intent = new Intent(GameOverActivity.this, TitleActivity.class);
        startActivity(intent);
    }

    public void playAgain(View view){
        Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
        startActivity(intent);
    }
}