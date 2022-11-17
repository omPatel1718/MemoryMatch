package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    int deckSize = 18;
    int startTime, lives, turns, score, time = 0;
    String gameMode;
    TextView scoreText;
    Card[] temp = new Card[deckSize];
    Card[] deck = new Card[deckSize];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreText = findViewById(R.id.display);

        //initializes deck (but each pair is consecutive
        Intent intent = getIntent();
        gameMode = intent.getStringExtra("GAMEMODE");
        Log.i("HELP", "this is the gameMode text: " + gameMode);
        for(int i=0; i<deckSize; i+=2){
            Card add = makeCard(i);
            temp[i] = add;
            if(add.getPoints()>0){
                temp[i+1] = new Card(add.getPoints(),"points");
            }else if(add.getLives()>0){
                temp[i+1] = new Card(add.getLives(),"lives");
            }else if(add.getTurns()>0){
                temp[i+1] = new Card(add.getTurns(),"turns");
            }else if(add.getTime()>0){
                temp[i+1] = new Card(add.getTime(),"time");
            }
        }

        //randomizes deck order
        for(int i=0; i<deckSize; i++){
            int rand = (int)(Math.random()*deckSize);
            while (deck[rand] != null){
                rand = (int)(Math.random()*deckSize);
            }
            deck[rand] = temp[i];
        }

        //if blitz, classic, or mastery, set up timer
        if(gameMode.equals("Classic")){
            startTime = 60000;
            timer();
        }else if(gameMode.equals("Mastery")){
            startTime = 60000;
            lives = 15;
            timer();
        }else if(gameMode.equals("Blitz")){
            startTime = 30000;
            timer();
        }else if(gameMode.equals("Zen")){
            turns = 15;
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
         - then use that variable as your actual timer and make timer recursive
         */

        new CountDownTimer((long) startTime, 1000) {
            public void onTick(long millisUntilFinished) {
                startTime--;
                time = startTime/1000;
                String text = "Score:" + score + "\nTimer: " + time;
                if(gameMode.equals("Mastery")){
                    text +="\nLives: " + lives;
                }
                scoreText.setText(text);
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
                value = 2000;
            }else if(rand==2){
                value = 3000;
            }else{
                value = 5000;
            }
        }else if(type.equals("time")){
            if(rand==1){
                value = 3000;
            }else if(rand==2){
                value = 5000;
            }else{
                value = 10000;
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
        int cardNum=0;

        if(v.getId() == R.id.card1){
            cardNum = 0;
        }else if(v.getId() == R.id.card2){
            cardNum = 1;
        }else if(v.getId() == R.id.card3){
            cardNum = 2;
        }else if(v.getId() == R.id.card4){
            cardNum = 3;
        }else if(v.getId() == R.id.card5){
            cardNum = 4;
        }else if(v.getId() == R.id.card6){
            cardNum = 5;
        }else if(v.getId() == R.id.card7){
            cardNum = 6;
        }else if(v.getId() == R.id.card8){
            cardNum = 7;
        }else if(v.getId() == R.id.card9){
            cardNum = 8;
        }else if(v.getId() == R.id.card10){
            cardNum = 9;
        }else if(v.getId() == R.id.card11){
            cardNum = 10;
        }else if(v.getId() == R.id.card12){
            cardNum = 11;
        }else if(v.getId() == R.id.card13){
            cardNum = 12;
        }else if(v.getId() == R.id.card14){
            cardNum = 13;
        }else if(v.getId() == R.id.card15){
            cardNum = 14;
        }else if(v.getId() == R.id.card16){
            cardNum = 15;
        }else if(v.getId() == R.id.card17){
            cardNum = 16;
        }else{
            cardNum = 17;
        }

        Card select = deck[cardNum];

        //checks if the card is already flipped
        if(!select.isFlipped()){
            select.setFlipped(true);
            //loops through deck and for each card:
            for(int i=0; i<18; i++){
                //checks if it is also flipped and not the same card >:(
                if(deck[i].isFlipped() && cardNum!=i){
                    //if it is, check if they match, updating correct vars
                    if(deck[i].toString().equals(select.toString())){
                        score += deck[i].getPoints();
                        turns += deck[i].getTurns();
                        lives += deck[i].getLives();
                        startTime += deck[i].getTime();
                        deck[i].setFlipped(false);
                        select.setFlipped(false);
                        disCard(v);
                        disCard(i);
                    //if they don't match, unflip them and decrease lives if needed
                    }else{
                        deck[i].setFlipped(false);
                        select.setFlipped(false);
                        if(gameMode.equals("Mastery")){
                            lives--;
                            String text = "Score:" + score + "\nTimer: " + time + "\nLives: " + lives;
                            scoreText.setText(text);
                            if(lives==0){
                                gameOver();
                            }
                        }
                    }
                    //decreases turns and updates screen for zen mode (since timer is never called)
                    if(gameMode.equals("Zen")){
                        turns--;
                        TextView scoreText = findViewById(R.id.display);
                        String text = "Score:" + score + "\nTurns: " + turns;
                        scoreText.setText(text);
                        if(turns==0){
                            gameOver();
                        }
                    }
                }
            }
        }
    }

    public void pause(View view) {
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void gameOver() {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("MODE", gameMode);
        intent.putExtra("SCORE",score);
        startActivity(intent);
    }

    public static Point getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    //actual discard animation
    public void disCard(View view){
        Point cardCoordinates = getLocationOnScreen(view);
        int cardX = cardCoordinates.x;
        int cardY = cardCoordinates.y;
        View discardPile = findViewById(R.id.discard);
        Point targetCoordinates = getLocationOnScreen(discardPile);
        int targetX = targetCoordinates.x;
        int targetY = targetCoordinates.y;
        float floatX = targetX - cardX;
        ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "translationX", 0f, floatX);
        moveX.setDuration(5);
        moveX.start();
        float floatY = targetY - cardY;
        ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "translationY", 0f, floatY);
        moveY.setDuration(5);
        moveY.start();
    }

    //turns an int into a card id and then calls disCard
    public void disCard(int cardNum){
        if(cardNum==0){
            disCard(findViewById(R.id.card1));
        }else if(cardNum==1){
            disCard(findViewById(R.id.card2));
        }else if(cardNum==2){
            disCard(findViewById(R.id.card3));
        }else if(cardNum==3){
            disCard(findViewById(R.id.card4));
        }else if(cardNum==4){
            disCard(findViewById(R.id.card5));
        }else if(cardNum==5){
            disCard(findViewById(R.id.card6));
        }else if(cardNum==6){
            disCard(findViewById(R.id.card7));
        }else if(cardNum==7){
            disCard(findViewById(R.id.card8));
        }else if(cardNum==8){
            disCard(findViewById(R.id.card9));
        }else if(cardNum==9){
            disCard(findViewById(R.id.card10));
        }else if(cardNum==10){
            disCard(findViewById(R.id.card11));
        }else if(cardNum==11){
            disCard(findViewById(R.id.card12));
        }else if(cardNum==12){
            disCard(findViewById(R.id.card13));
        }else if(cardNum==13){
            disCard(findViewById(R.id.card14));
        }else if(cardNum==14){
            disCard(findViewById(R.id.card15));
        }else if(cardNum==15){
            disCard(findViewById(R.id.card16));
        }else if(cardNum==16){
            disCard(findViewById(R.id.card17));
        }else{
            disCard(findViewById(R.id.card18));
        }
    }
}