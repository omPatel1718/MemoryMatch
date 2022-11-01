package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    String gameMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //initializes variables according to game mode
        Card[] deck;
        for(int i=0; i<30; i++){
            /*
             check for gamemode, randomize card type (come up with percentages as a group)

             classic - 75% points 25% time
             zen - 90% points 10% turns (odds of a turn increase less than classic's time)
             blitz - 60% points 40% time (odds of a time increase more than classic's time)
             mastery - 80% points 15% time 5% lives (this is hard mode, time and lives should be lower odds)
             */
            Intent intent = getIntent();
            gameMode = intent.getStringExtra("GAMEMODE");
            String value = "";
            int type = 0;

            //randomizes gamemode
            int rand = (int)(Math.random()*100)+1;
            if(gameMode.equals("Classic")){
                if(rand<76){
                    value = "points";
                }else{
                    value = "time";
                }
            }else if(gameMode.equals("Zen")){
                if(rand<91){
                    value = "points";
                }else{
                    value = "turns";
                }
            }else if(gameMode.equals("Mastery")){
                if(rand<81){
                    value = "points";
                }else if(rand<96){
                    value = "time";
                }else{
                    value = "lives";
                }
            }else if(gameMode.equals("Blitz")){
                if(rand<61){
                    value = "points";
                    value = "time";
                }
            }

            /*
             randomize values based on what type of card and game mode
             turns - 1, 3, or 5
             lives - 1, 2, or 3
             time - 3, 5, or 10
             blitz time - 2, 3, or 5
             */
            rand = (int)(Math.random()*3)+1;
            if(value.equals("turns")){
                if(rand==1){

                }else if(rand==2){

                }else{

                }
            }else if(value.equals("lives")){
                if(rand==1){

                }else if(rand==2){

                }else{

                }
            }else if(value.equals("time") && gameMode.equals("Blitz")){
                if(rand==1){

                }else if(rand==2){

                }else{

                }
            }else if(value.equals("time")){
                if(rand==1){

                }else if(rand==2){

                }else{

                }
            }else{
                if(rand==1){

                }else if(rand==2){

                }else{

                }
            }
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
                gameOver();
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
                    if(Card[i].toString.equals(card.
                    2)){
                        whatever needs to change does
                    }else{
                        Card[i].setFlipped(false);
                        thisCard.setFlipped(false);
                        if(game mode is mastery){
                        lives--;
                        if(lives==0){
                            gameOver();
                        }
                    }
                    }
                    if(game mode is zen){
                        turns--;
                        if(turns==0){
                            gameOver();
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

    public void gameOver() {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        startActivity(intent);
    }
}