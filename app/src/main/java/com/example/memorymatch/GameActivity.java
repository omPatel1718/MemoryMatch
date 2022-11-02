package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    int deckSize = 18;
    int startTime = 0;
    String gameMode;
    Card[] deck = new Card[deckSize];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //initializes deck
        Intent intent = getIntent();
        gameMode = intent.getStringExtra("GAMEMODE");

        for(int i=0; i<deckSize; i+=2){
            Card temp = makeCard(i);
            deck[i] = temp;
            deck[i+1] = temp;
        }


        //if blitz, classic, or mastery, set up timer
        if(gameMode.equals("Classic") || gameMode.equals("Mastery")){
            startTime = 30;
            timer();
        }else if(gameMode.equals("Blitz")){
            startTime = 20;
            timer();
        }

    }

    public void timer(){
        /*
         found some decreasing timer code on https://developer.android.com/reference/android/os/CountDownTimer#java
         depicts a simple 30 second timer
         we need to figure out how to pause and add to said timer mid countdown

         okay figured out some nightmare spagetti code that might work
         - since you can't increase the time remaining mid countdown,
         create a variable
         */

        new CountDownTimer(startTime* 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                startTime--;
            }

            public void onFinish() {
                if(startTime>0){
                    timer();
                }
                gameOver();
            }
        }.start();
    }

    public Card makeCard(int index){
        /*
         check for gamemode, randomize card type

         classic - 75% points 25% time
         zen - 90% points 10% turns (odds of a turn increase less than classic's time)
         blitz - 60% points 40% time (odds of a time increase more than classic's time)
         mastery - 80% points 15% time 5% lives (this is hard mode, time and lives should be lower odds)
        */
        String type = "";
        int value = 0;

        //randomizes card type based on game mode
        int rand = (int)(Math.random()*100)+1;
        if(gameMode.equals("Classic")){
            if(rand<76){
                type = "points";
            }else{
                type = "time";
            }
        }else if(gameMode.equals("Zen")){
            if(rand<91){
                type = "points";
            }else{
                type = "turns";
            }
        }else if(gameMode.equals("Mastery")){
            if(rand<81){
                type = "points";
            }else if(rand<96){
                type = "time";
            }else{
                type = "lives";
            }
        }else if(gameMode.equals("Blitz")){
            if(rand<61){
                type = "points";
            }else{
                type = "time";
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
        if(type.equals("turns")){
            if(rand==1){
                value = 1;
            }else if(rand==2){
                value = 3;
            }else{
                value = 5;
            }
        }else if(type.equals("lives")){
            if(rand==1){
                value = 1;
            }else if(rand==2){
                value = 2;
            }else{
                value = 3;
            }
        }else if(type.equals("time") && gameMode.equals("Blitz")){
            if(rand==1){
                value = 2;
            }else if(rand==2){
                value = 3;
            }else{
                value = 5;
            }
        }else if(type.equals("time")){
            if(rand==1){
                value = 3;
            }else if(rand==2){
                value = 5;
            }else{
                value = 10;
            }
        }else{
            if(rand==1){
                value = 100;
            }else if(rand==2){
                value = 200;
            }else{
                value = 300;
            }
        }
        return new Card(value, type);
    }



    public void cardClicked(View v){
        /*
        if(!thisCard.isFlipped){
            thisCard.setFlipped(true);
            String card2 = thisCard.toString();
            for(int i=0; i<30; i++){
                if(Card[i].isFlipped()){
                    if(deck[i].toString.equals(card.2)){
                        if(deck[i]
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