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
    String spinnerSelectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        gameSpinner = findViewById(R.id.modeSpinner);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void startGame(View view) {
        spinnerSelectedText = gameSpinner.getSelectedItem().toString();
        Intent intent = new Intent(TitleActivity.this, GameActivity.class);
        Log.i("HELP", "this is the spinner text: " + spinnerSelectedText);
        intent.putExtra("GAMEMODE", spinnerSelectedText);
        startActivity(intent);
    }

    public void instructions(View view) {
        Intent intent = new Intent(TitleActivity.this, InfoActivity.class);
        startActivity(intent);
        intent.putExtra("PREVSCREEN", "fromTitle");
    }

}