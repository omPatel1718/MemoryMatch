package com.example.memorymatch;

public class Card {
    int points, turns, time, lives;
    boolean flipped, matched;

    public Card(int value, int type){
        flipped = false;
        matched = false;

        if(type == 1){                         //points - 1
            points = value;
            time = 0;
            turns = 0;
            lives = 0;
        }else if(type == 2){                   //time - 2
            points = 0;
            time = value;
            turns = 0;
            lives = 0;
        }else if(type == 3){                   //turns - 3  (Zen only)
            points = 0;
            turns = 0;
            time = value;
            lives = 0;
        }else if(type==0){                     //lives - 0  (Mastery only)
            points = 0;
            time = 0;
            turns = 0;
            lives = value;
        }
    }

    public void clicked(){
        if(!flipped){
            flipped = true;
        }
    }

    @Override
    public String toString() {
        int value = 0;
        String type = "";
        if(points>value){
            value = points;
            type = "points";
        }
        if(time>value){
            value = time;
            type = "time";
        }
        if(turns>value){
            value = turns;
            type = "turns";
        }
        if(lives>value){
            value = lives;
            type = "lives";
        }
        return "" + value + " " + type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
