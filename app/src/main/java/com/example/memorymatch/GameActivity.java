package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    int deckSize = 18;
    int startTime, lives, turns, score, time = 0;
    boolean paused;
    String gameMode, difficulty;
    TextView scoreText;
    Card[] temp = new Card[deckSize];
    Card[] deck = new Card[deckSize];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        scoreText = findViewById(R.id.display);
        paused = false;

        //initializes deck (but each pair is consecutive
        Intent intent = getIntent();
        gameMode = intent.getStringExtra("GAMEMODE");
        difficulty = intent.getStringExtra("DIFFICULTY");
        Log.i("HELP", "this is the gameMode text: " + gameMode);
        for(int i=0; i<deckSize; i+=2){
            Card add = makeCard(i);
            temp[i] = add;
            int tempImage = add.getImage();
            if(add.getPoints()>0){
                temp[i+1] = new Card(add.getPoints(),"points", tempImage);
            }else if(add.getLives()>0){
                temp[i+1] = new Card(add.getLives(),"lives", tempImage);
            }else if(add.getTurns()>0){
                temp[i+1] = new Card(add.getTurns(),"turns", tempImage);
            }else if(add.getTime()>0){
                temp[i+1] = new Card(add.getTime(),"time", tempImage);
            }
            if (i == 0 || i == 1) {
                add.setImage(R.drawable.card_the_chariot_small);
            } else if (i == 2 || i == 3) {
                add.setImage(R.drawable.card_the_big_x_small);
            } else if (i == 4 || i == 5) {
                add.setImage(R.drawable.card_the_fool_small);
            } else if (i == 6 || i == 7) {
                add.setImage(R.drawable.card_the_hermit_small);
            } else if (i == 8 || i == 9) {
                add.setImage(R.drawable.card_the_magician_small);
            } else if (i == 10 || i == 11) {
                add.setImage(R.drawable.card_the_moon_small);
            } else if (i == 12 || i == 13) {
                add.setImage(R.drawable.card_the_tower_small);
            } else if (i == 14 || i == 15) {
                add.setImage(R.drawable.card_the_world_small);
            } else {
                add.setImage(R.drawable.card_the_wheel_of_fortune_small);
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
            if(difficulty.equals("Easy")){
                startTime = 60000;
            }else if(difficulty.equals("Medium")){
                startTime = 50000;
            }else if(difficulty.equals("Hard")){
                startTime = 40000;
            }else{
                startTime = 30000;
            }
        }else if(gameMode.equals("Mastery")){
            if(difficulty.equals("Easy")){
                startTime = 60000;
            }else if(difficulty.equals("Medium")){
                startTime = 50000;
            }else if(difficulty.equals("Hard")){
                startTime = 40000;
            }else{
                startTime = 30000;
            }
            if(difficulty.equals("Easy")){
                startTime = 20;
            }else if(difficulty.equals("Medium")){
                startTime = 15;
            }else if(difficulty.equals("Hard")){
                startTime = 10;
            }else{
                startTime = 5;
            }
        }else if(gameMode.equals("Blitz")){
            if(difficulty.equals("Easy")){
                startTime = 30000;
            }else if(difficulty.equals("Medium")){
                startTime = 25000;
            }else if(difficulty.equals("Hard")){
                startTime = 20000;
            }else{
                startTime = 15000;
            }
        }else if(gameMode.equals("Zen")){
            if(difficulty.equals("Easy")){
                turns = 25;
            }else if(difficulty.equals("Medium")){
                turns = 20;
            }else if(difficulty.equals("Hard")){
                turns = 15;
            }else{
                turns = 10;
            }
        }

        if(difficulty.equals("Easy")){
            flipAll(10000);
        }else if(difficulty.equals("Medium")){
            flipAll(5000);
        }else if(difficulty.equals("Hard")){
            flipAll(3000);
        }else{
            flipAll(2000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!gameMode.equals("Zen")){
            timer();
        }
    }

    public void timer(){
        /*
         found some decreasing timer code on https://developer.android.com/reference/android/os/CountDownTimer#java
         depicts a simple 30 second timer
         we need to figure out how to pause and add to said timer mid countdown

         okay figured out some nightmare spaghetti code that might work
         - since you can't increase the time remaining mid countdown,
         create a variable
         - then use that variable as your actual timer and make timer recursive
         */

        new CountDownTimer((long) startTime, 1000) {
            public void onTick(long millisUntilFinished) {
                //updates score display
                if(!paused){
                    startTime-=1000;
                }
                time = startTime/1000;
                String text = "Score: " + score + "\nTimer: " + time;
                if(gameMode.equals("Mastery")){
                    text +="\nLives: " + lives;
                }
                scoreText.setText(text);

                //updates card faces
                for(int i=0; i<deckSize; i++){
                    if(deck[i].isFlipped()){
                        //take this card int and do the flipped update code
                        getButtonFromCard(i);
                    }else{
                        //take this card int and do the back side update code
                        getButtonFromCard(i);
                    }
                }
            }

            public void onFinish() {
                if(startTime>0){
                    timer();
                }
                if(!paused){
                    gameOver();
                }
                paused = true;
            }
        }.start();
    }

    public Card makeCard(int index){
        /*
         check for gamemode, randomize card type

         classic - 75% points 25% time
         zen - 60% points 40% turns (odds of a turn increase less than classic's time)
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
            if(rand<61){
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
         turns - 1-5
         lives - 1-3
         time - 3-10
         blitz time - 2-5
        */
        if(type.equals("turns")){
            value = (int)(Math.random()*5)+1;
        }else if(type.equals("lives")){
            value = (int)(Math.random()*3)+1;
        }else if(type.equals("time") && gameMode.equals("Blitz")){
            value = 1000 * ((int)(Math.random()*4)+2);
        }else if(type.equals("time")){
            value = 1000 * ((int)(Math.random()*8)+3);
        }else{
            value = 50 * ((int)(Math.random()*5)+2);
        }
        Context cardImage;
        if(index == 0){

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

        //checks if the card is already flipped and if in freeze state
        if(!select.isFlipped() && !paused){
            select.setFlipped(true);
            v.setBackgroundResource(select.getImage());
            //loops through deck and for each card:
            for(int i=0; i<18; i++){
                //checks if it is also flipped and not the same card >:(
                if(deck[i].isFlipped() && cardNum!=i && !deck[i].isMatched()){
                    //if it is, check if they match, updating correct vars
                    if(deck[i].toString().equals(select.toString())){
                        updateMatched(deck[i],select);
                        disCard(v);
                        disCard(i);

                        boolean temp = false;
                        for (Card c: deck) {
                            if(!c.isMatched()){
                                temp = true;
                            }
                        }
                        if(temp){
                            shuffle();
                        }
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

    public void shuffle(){
        for(int i=0; i<deckSize; i+=2){
            Card add = makeCard(i);
            temp[i] = add;
            int tempImage = add.getImage();
            if(add.getPoints()>0){
                temp[i+1] = new Card(add.getPoints(),"points", tempImage);
            }else if(add.getLives()>0){
                temp[i+1] = new Card(add.getLives(),"lives", tempImage);
            }else if(add.getTurns()>0){
                temp[i+1] = new Card(add.getTurns(),"turns", tempImage);
            }else if(add.getTime()>0){
                temp[i+1] = new Card(add.getTime(),"time", tempImage);
            }
        }
    }

    public void updateMatched(Card one, Card two){
        score += one.getPoints();
        turns += one.getTurns();
        lives += one.getLives();
        startTime += one.getTime();
        one.setFlipped(false);
        two.setFlipped(false);
        one.setMatched(true);
        two.setMatched(true);
    }

    public void pause(View view) {
        paused = true;
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
        disCard(findViewById(getButtonFromCard(cardNum)));
    }

    public void flipAll(int sec){
        paused = true;
        for(int i=0; i<deckSize; i++){
            deck[i].setFlipped(true);
        }

        new CountDownTimer((long) sec, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                paused = false;
                for(int i=0; i<deckSize; i++){
                    deck[i].setFlipped(false);
                }
            }
        }.start();
    }

    public int getButtonFromCard(int cardNum){
        if(cardNum==0){
            return R.id.card1;
        }else if(cardNum==1){
            return R.id.card2;
        }else if(cardNum==2){
            return R.id.card3;
        }else if(cardNum==3){
            return R.id.card4;
        }else if(cardNum==4){
            return R.id.card5;
        }else if(cardNum==5){
            return R.id.card6;
        }else if(cardNum==6){
            return R.id.card7;
        }else if(cardNum==7){
            return R.id.card8;
        }else if(cardNum==8){
            return R.id.card9;
        }else if(cardNum==9){
            return R.id.card10;
        }else if(cardNum==10){
            return R.id.card11;
        }else if(cardNum==11){
            return R.id.card12;
        }else if(cardNum==12){
            return R.id.card13;
        }else if(cardNum==13){
            return R.id.card14;
        }else if(cardNum==14){
            return R.id.card15;
        }else if(cardNum==15){
            return R.id.card16;
        }else if(cardNum==16){
            return R.id.card17;
        }else{
            return R.id.card18;
        }
    }
}