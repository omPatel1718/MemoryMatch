package com.example.memorymatch;

public class Card {
    int points, turns, time;
    boolean flipped, matched;

    public Card(int value, String type){
        flipped = false;
        matched = false;

        if(type.equals("points")){
            points = value;
            turns = 0;
            time = 0;
        }else if(type.equals("turns")){
            points = 0;
            turns = value;
            time = 0;
        }
        else{
            points = value;
            turns = 0;
            time = 0;
        }
    }

    public void clicked(){
        if(!flipped){
            flipped = true;
        }
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
