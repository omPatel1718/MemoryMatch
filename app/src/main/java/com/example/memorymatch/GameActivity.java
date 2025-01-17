package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    int deckSize = 18;
    int startTime, lives, turns, score, time, countUnmatched = 0;
    boolean paused;
    String gameMode, difficulty;
    TextView scoreText;
    Card[] temp = new Card[deckSize];
    Card[] deck = new Card[deckSize];
    CountDownTimer clock;

    int[][] ogCoordinates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        scoreText = findViewById(R.id.display);
        ogCoordinates = new int[18][2];
        //initializes deck (but each pair is consecutive
        Intent intent = getIntent();
        gameMode = intent.getStringExtra("GAMEMODE");
        difficulty = intent.getStringExtra("DIFFICULTY");
        Log.i("HELP", "this is the gameMode text: " + gameMode);
        for(int i=0; i<deckSize; i+=2){
            Card add = makeCard();
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
            temp[i].setImage(whichImage(i));
            temp[i+1].setImage(temp[i].getImage());
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
        switch (gameMode) {
            case "Classic":
                switch (difficulty) {
                    case "Easy":
                        startTime = 60000;
                        break;
                    case "Medium":
                        startTime = 50000;
                        break;
                    case "Hard":
                        startTime = 40000;
                        break;
                    default:
                        startTime = 30000;
                        break;
                }
                break;
            case "Mastery":
                switch (difficulty) {
                    case "Easy":
                        startTime = 60000;
                        break;
                    case "Medium":
                        startTime = 50000;
                        break;
                    case "Hard":
                        startTime = 40000;
                        break;
                    default:
                        startTime = 30000;
                        break;
                }
                switch (difficulty) {
                    case "Easy":
                        lives = 20;
                        break;
                    case "Medium":
                        lives = 15;
                        break;
                    case "Hard":
                        lives = 10;
                        break;
                    default:
                        lives = 5;
                        break;
                }
                break;
            case "Blitz":
                switch (difficulty) {
                    case "Easy":
                        startTime = 30000;
                        break;
                    case "Medium":
                        startTime = 25000;
                        break;
                    case "Hard":
                        startTime = 20000;
                        break;
                    default:
                        startTime = 15000;
                        break;
                }
                break;
            case "Zen":
                switch (difficulty) {
                    case "Easy":
                        turns = 25;
                        break;
                    case "Medium":
                        turns = 20;
                        break;
                    case "Hard":
                        turns = 15;
                        break;
                    default:
                        turns = 10;
                        break;
                }
                break;
        }

        switch (difficulty) {
            case "Easy":
                flipAll(10000);
                break;
            case "Medium":
                flipAll(5000);
                break;
            case "Hard":
                flipAll(3000);
                break;
            default:
                flipAll(2000);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        paused = false;
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

        clock = new CountDownTimer((long) startTime, 1000) {
            public void onTick(long millisUntilFinished) {
                //updates score display
                if(!paused){
                    startTime-=1000;
                }
                time = startTime/1000;
                String text = "Score: " + score + "\nTimer: " + time;
                if(gameMode.equals("Mastery")){
                    text += "\nLives: " + lives;
                }
                scoreText.setText(text);
            }

            public void onFinish() {
                if(startTime>0){
                    timer();
                }else if(!paused){
                    paused = true;
                    gameOver();
                }
            }
        }.start();
    }

    public Card makeCard(){
        /*
         check for gamemode, randomize card type

         classic - 75% points 25% time
         zen - 80% points 20% turns (odds of a turn increase less than classic's time)
         blitz - 60% points 40% time (odds of a time increase more than classic's time)
         mastery - 80% points 15% time 5% lives (this is hard mode, time and lives should be lower odds)
        */
        String type = "";
        int value;

        //randomizes card type based on game mode
        int rand = (int)(Math.random()*100)+1;
        switch (gameMode) {
            case "Classic":
                if (rand < 76) {
                    type = "points";
                } else {
                    type = "time";
                }
                break;
            case "Zen":
                if (rand < 81) {
                    type = "points";
                } else {
                    type = "turns";
                }
                break;
            case "Mastery":
                if (rand < 81) {
                    type = "points";
                } else if (rand < 96) {
                    type = "time";
                } else {
                    type = "lives";
                }
                break;
            case "Blitz":
                if (rand < 61) {
                    type = "points";
                } else {
                    type = "time";
                }
                break;
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
        
        return new Card(value, type);
    }

    //helper method that assigns images to card pairs
    public int whichImage(int index){
        int imageInt = -1;                           //temp variable to hold image
        while(imageInt == -1){      //Card objects default to image == 0
            int rand = (int)(Math.random()*9);  //randomly generate a number 0 to 8
            //sets imageInt based on rand's value
            if(rand == 0){
                imageInt = R.drawable.card_the_chariot_small;
            }else if(rand == 1){
                imageInt = R.drawable.card_the_big_x_small;
            }else if(rand == 2){
                imageInt = R.drawable.card_the_fool_small;
            }else if(rand == 3){
                imageInt = R.drawable.card_the_hermit_small;
            }else if(rand == 4){
                imageInt = R.drawable.card_the_magician_small;
            }else if(rand == 5){
                imageInt = R.drawable.card_the_moon_small;
            }else if(rand == 6){
                imageInt = R.drawable.card_the_tower_small;
            }else if(rand == 7){
                imageInt = R.drawable.card_the_world_small;
            }else if(rand ==8){
                imageInt = R.drawable.card_the_wheel_of_fortune_small;
            }

            //iterates
            for(int i=0; i<=index; i+=2){
                if(imageInt == temp[i].getImage() || imageInt == -1) {
                    imageInt = -1;
                }
            }
        }
        return imageInt;
    }

    //checks what card was clicked, if they match, and changes variables, positions, animations, etc. accordingly
    public void cardClicked(View v) {
        for(int i=0; i<deckSize; i++){
            Point cardLocation = getLocationOnScreen(i);
            ogCoordinates[i][0] = cardLocation.x;
            ogCoordinates[i][1] = cardLocation.y;
            Log.e("AAAA", "index: " + i + " X: " + ogCoordinates[i][0] + " Y: " + ogCoordinates[i][1]);
        }

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
            ImageButton btn1 = (ImageButton)findViewById(v.getId());
            btn1.setImageResource(select.getImage());
            //loops through deck and for each card:
            for(int i=0; i<18; i++){
                //checks if it is also flipped and not the same card >:(
                if(deck[i].isFlipped() && cardNum!=i && !deck[i].isMatched()){
                    //if it is, check if they match, updating correct vars
                    if(deck[i].toString().equals(select.toString())){
                        updateMatched(deck[i],select);
                        disCard(v);
                        disCard(i);


                        Log.e("hello", "before if " + countUnmatched);
                        if(countUnmatched==9){
                            Log.e("hello", "before shuffle");
                            countUnmatched=0;
                            shuffle();
                            Log.e("hello", "after shuffle");
                        }

                    //if they don't match, unflip them and decrease lives if needed
                    }else{
                        deck[i].setFlipped(false);
                        select.setFlipped(false);

                        ImageButton btn2 = (ImageButton)findViewById(getButtonFromCard(i));
                        setDelay1(btn1, btn2);

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

    public void setDelay1(ImageButton a, ImageButton b){
        new CountDownTimer((long) 500, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                a.setImageResource(R.drawable.pixel_card_back);
                b.setImageResource(R.drawable.pixel_card_back);
            }
        }.start();
    }

    //redeals 18 new cards
    public void shuffle(){
        Log.e("bye", "hello");
        paused = true;
        for(int i=0; i<deckSize; i+=2){
            Log.e("bye", "card make:" + i);
            Card add = makeCard();
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
            temp[i].setImage(whichImage(i));
            temp[i+1].setImage(temp[i].getImage());

            deck[i] = null;
            deck[i+1] = null;
        }

        //randomizes deck order
        for(int i=0; i<deckSize; i++){
            Log.e("bye", "card shuffle:" + i);
            int rand = (int)(Math.random()*deckSize);
            while (deck[rand] != null){
                rand = (int)(Math.random()*deckSize);
            }
            deck[rand] = temp[i];
            deck[rand].setFlipped(false);
            deck[rand].setMatched(false);
        }

        Log.e("bye", "before delay");
        setDelay2();

        Log.e("bye", "switch case");
        switch (difficulty) {
            case "Easy":
                flipAll(10000);
                break;
            case "Medium":
                flipAll(5000);
                break;
            case "Hard":
                flipAll(3000);
                break;
            default:
                flipAll(2000);
                break;
        }
        paused = false;
    }

    public void setDelay2(){
        new CountDownTimer((long) 1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                dealCards();
            }
        }.start();
    }

    //helper method that updates variables if cards match
    public void updateMatched(Card one, Card two){
        countUnmatched++;
        score += one.getPoints();
        turns += one.getTurns();
        lives += one.getLives();
        startTime += one.getTime();
        one.setFlipped(false);
        two.setFlipped(false);
        one.setMatched(true);
        two.setMatched(true);
        Context popUp = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(popUp, one.display(), duration).show();
        if(gameMode.equals("Zen")){
            turns++;
        }
    }

    //calls menu activity and pauses game
    public void pause(View view) {
        paused = true;
        if(!gameMode.equals("Zen")){
            clock.cancel();
        }
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        intent.putExtra("GAMEMODE", gameMode);
        intent.putExtra("DIFFICULTY",difficulty);
        startActivity(intent);
    }

    //calls gameOver screen and ends game
    public void gameOver() {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("GAMEMODE", gameMode);
        intent.putExtra("DIFFICULTY",difficulty);
        intent.putExtra("SCORE",score);
        startActivity(intent);
    }

    //helper method that finds an objects coordinates
    public static Point getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    public Point getLocationOnScreen(int cardNum){
        return getLocationOnScreen(findViewById(getButtonFromCard(cardNum)));
    }


    //turns all the cards up right for a set duration
    public void flipAll(int sec){
        new CountDownTimer((long) sec, 1000) {
            public void onTick(long millisUntilFinished) {
                paused = true;
                for(int i=0; i<deckSize; i++){
                    deck[i].setFlipped(true);
                    ImageButton btn1 = (ImageButton)findViewById(getButtonFromCard(i));
                    btn1.setImageResource(deck[i].getImage());
                }
            }

            public void onFinish() {
                paused = false;
                for(int i=0; i<deckSize; i++){
                    deck[i].setFlipped(false);
                    ImageButton btn1 = (ImageButton)findViewById(getButtonFromCard(i));
                    btn1.setImageResource(R.drawable.pixel_card_back);
                }
            }
        }.start();
    }
    //finds the current card
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
        moveX.setDuration(500);
        moveX.start();
        float floatY = targetY - cardY;
        ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "translationY", 0f, floatY);
        moveY.setDuration(500);
        moveY.start();
    }

    //turns an int into a card id and then calls disCard
    public void disCard(int cardNum){
        disCard(findViewById(getButtonFromCard(cardNum)));
    }

    //helper method to redeal the cards
    public void dealCards(){
        Point deckPile = getLocationOnScreen(findViewById(R.id.deck));
        Point disCardPile = getLocationOnScreen(findViewById(R.id.discard));
        for (int i = 0; i < ogCoordinates.length; i++){
            int deckY = deckPile.y;
            int disCardY = disCardPile.y;
            float floatY = disCardY - deckY;
            ObjectAnimator y = ObjectAnimator.ofFloat(findViewById(getButtonFromCard(i)), "translationY", floatY);
            y.setDuration(5);
            y.start();
            Log.e("deal", "deck " + i);
        }
        for (int i = 0; i < ogCoordinates.length; i++){
            //int setCoordinateX = ogCoordinates[i][0];
            //int setCoordinateY = ogCoordinates[i][1];
            int setCoordinateX = 1867;
            int setCoordinateY = 732;
            Log.e("deal", "X: " + setCoordinateX + " Y: " + setCoordinateY);
            int deckX = deckPile.x;
            int deckY = deckPile.y;

            float floatX = deckX - setCoordinateX;
            float floatY = 320 + deckY - setCoordinateY;
            ObjectAnimator x = ObjectAnimator.ofFloat(findViewById(getButtonFromCard(i)), "translationX", floatX);
            x.setDuration(500);
            x.start();
            ObjectAnimator y = ObjectAnimator.ofFloat(findViewById(getButtonFromCard(i)), "translationY", 0f, floatY);
            y.setDuration(500);
            y.start();
            Log.e("deal", "reshuffle " + i + " deckX " + deckX + " deckY " + deckY + " floatX: "
                    + floatX + " floatY: " + floatY + " coorX " + setCoordinateX + " coorY " + setCoordinateY);
        }
    }
}