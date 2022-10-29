package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Card[] deck;
        for(int i=0; i<30; i++){
            /*
             check for gamemode, randomize card type (come up with percentages as a group)

             classic - x% points x% time
             zen - x% points x% turns (odds of a turn increase less than classic's time)
             blitz - x% points x% time (odds of a time increase more than classic's time)
             mastery - x% points x% time x% lives (this is hard mode, time and lives should be lower odds)
             */

            /*
             randomize values based on what type of card and game mode
             turns - 1, 3, or 5
             lives - 1, 2, or 3
             time - 3, 5, or 10
             blitz time - 2, 3, or 5
             */
        }
    }
}