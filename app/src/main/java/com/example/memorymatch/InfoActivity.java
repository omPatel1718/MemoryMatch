package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    String origin = "";
    int leftPage = 1;
    TextView infoText1, infoText2, pageNum1, pageNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        //extract String data USERNAME
        origin = intent.getStringExtra("PREVSCREEN");

        infoText1 = findViewById(R.id.instructions1);
        infoText2 = findViewById(R.id.instructions2);
        pageNum1 = findViewById(R.id.pageNumber1);
        pageNum2 = findViewById(R.id.pageNumber2);
    }

    public void pageRight(View v){
        if(leftPage<5){
            leftPage+=2;
        }
        pageCheck();
    }

    public void pageLeft(View v){
        if(leftPage>1){
            leftPage-=2;
        }
        pageCheck();
    }

    @SuppressLint("SetTextI18n")
    public void pageCheck(){
        pageNum1.setText(""+leftPage);
        pageNum2.setText(""+(leftPage+1));
        if(leftPage==1){
            infoText1.setText("Welcome to Memory Match!\nThis game is designed to test your memory.\nWhen you start the game, you'll get 18 cards to match" +
                    " and matching a pair will net you rewards!");
            infoText2.setText("Classic: A Good Beginning\nCards get you points or more time.\nTry to make as many matches as you can with the time you've got!");
        }else if(leftPage==3){
            infoText1.setText("Zen: Strategy is Key\nInstead of a timer, you have turns.\nEach time you flip 2 cards, you lose a turn whether or not you make a match." +
                    "\nTake as much time as you need to get a good score!\nCards get you points or more turns.");
            infoText2.setText("Blitz: GO GO GO\nIn this fast paced game mode, you start with less time and time increases are lower, but time cards are more common." +
                    "\nStay focused under pressure and do your best.\nCards give points or more time.");
        }else if(leftPage==5){
            infoText1.setText("Mastery: The Ultimate Challenge\nIn the final test of skill, you have both a ticking timer, but also lives." +
                    "\nEach time you make a wrong match you lose a life.\nCards give points, more lives, and more time.\nGood Luck.");
            infoText2.setText("What does difficulty do?:\nIt changes starting lives, turns, and time. " +
                    "\nIt also shortens the time the cards are revealed to you at the beginning");
        }
    }

    public void back(View view) {
        if(origin.equals("fromMenu")) {
            Intent intent = new Intent(InfoActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        if(origin.equals("fromTitle")) {
            Intent intent = new Intent(InfoActivity.this, TitleActivity.class);
            startActivity(intent);
        }
    }
}