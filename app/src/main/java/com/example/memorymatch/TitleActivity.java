package com.example.memorymatch;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class TitleActivity extends AppCompatActivity {

    Spinner gameSpinner, difficultySpinner;
    String spinnerSelectedTextGame, spinnerSelectedTextDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        gameSpinner = findViewById(R.id.modeSpinner);
        difficultySpinner = findViewById(R.id.modeSpinner2);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void startGame(View view) {
        spinnerSelectedTextGame = gameSpinner.getSelectedItem().toString();
        spinnerSelectedTextDifficulty = difficultySpinner.getSelectedItem().toString();
        Intent intent = new Intent(TitleActivity.this, GameActivity.class);
        intent.putExtra("GAMEMODE", spinnerSelectedTextGame);
        intent.putExtra("DIFFICULTY", spinnerSelectedTextDifficulty);
        startActivity(intent);
    }

    public void instructions(View view) {
        Intent intent = new Intent(TitleActivity.this, InfoActivity.class);
        startActivity(intent);
        intent.putExtra("PREVSCREEN", "fromTitle");
    }

}