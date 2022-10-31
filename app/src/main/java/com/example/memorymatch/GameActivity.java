package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*
         initializes variables according to game mode
         */
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

        // found some decreasing timer code on https://developer.android.com/reference/android/os/CountDownTimer#java
        // depicts a simple 30 second timer
        // we need to figure out how to pause and add to said timer mid countdown

        //if blitz, classic, or mastery
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //mTextField.setText("done!");
            }
        }.start();
    }

    public void cardClicked(View v){
        /*
        if(!thisCard.isFlipped){
            thisCard.setFlipped(true);
            String card2 = thisCard.toString();
            for(int i=0; i<30; i++){
                if(Card[i].isFlipped()){
                    if(Card[i].toString.equals(card2)){
                        whatever needs to change does
                    }else{
                        Card[i].setFlipped(false);
                        thisCard.setFlipped(false);
                        if(game mode is mastery){
                        lives--;
                        if(lives==0){
                            end game
                        }
                    }
                    }
                    if(game mode is zen){
                        turns--;
                        if(turns==0){
                            end game
                        }
                    }
                }
            }
        }
        */
    }

    public void pause(View view) {
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void gameOver(View view) {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        startActivity(intent);
    }
}