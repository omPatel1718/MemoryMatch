package com.example.memorymatch;
//May want to check later when animating the cards
//https://developer.android.com/develop/ui/views/animations/reposition-view
//https://developer.android.com/reference/android/animation/ObjectAnimator
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class TitleActivity extends AppCompatActivity {

    Spinner spinner;
    String spinnerSelectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void startGame(View view) {
        spinnerSelectedText = spinner.getSelectedItem().toString();
        int gameNum = 0;
        String gameMode = "";

        //checks each spinner option
        for (int i = 1; i < 4; i++) {
            if (spinnerSelectedText.equals(getResources().
                    getStringArray(R.array.game_mode)[i])) {
                gameNum = 4-i;
                break;
            }
        }

        //sets gameMode based on spinner check
        if(gameNum==3){
            gameMode="Classic";
        }else if(gameNum==2){
            gameMode="Zen";
        }else if(gameNum==1){
            gameMode="Mastery";
        }else{
            gameMode="Blitz";
        }

        Intent intent = new Intent(TitleActivity.this, GameActivity.class);
        startActivity(intent);
        intent.putExtra("GAMEMODE", gameMode);
    }

    public void instructions(View view) {
        Intent intent = new Intent(TitleActivity.this, InfoActivity.class);
        startActivity(intent);
        intent.putExtra("PREVSCREEN", "fromTitle");
    }

}