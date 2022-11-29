package com.example.memorymatch;

import android.content.Context;
import android.media.Image;

public class Card {
    int points, turns, time, lives;
    boolean flipped, matched;
    int image;

    public Card(int value, String type, int imageName){
        flipped = false;
        matched = false;

        if(type.equals("points")){                         //points
            points = value;
            time = 0;
            turns = 0;
            lives = 0;
        }else if(type.equals("time")){                   //time
            points = 0;
            time = value;
            turns = 0;
            lives = 0;
        }else if(type.equals("turns")){                   //turns       (Zen only)
            points = 0;
            turns = 0;
            time = value;
            lives = 0;
        }else if(type.equals("lives")){                     //lives     (Mastery only)
            points = 0;
            time = 0;
            turns = 0;
            lives = value;
        }
        image = imageName;
    }

    public Card(int value, String type){
        flipped = false;
        matched = false;

        if(type.equals("points")){                         //points
            points = value;
            time = 0;
            turns = 0;
            lives = 0;
        }else if(type.equals("time")){                   //time
            points = 0;
            time = value;
            turns = 0;
            lives = 0;
        }else if(type.equals("turns")){                   //turns       (Zen only)
            points = 0;
            turns = 0;
            time = value;
            lives = 0;
        }else if(type.equals("lives")){                     //lives     (Mastery only)
            points = 0;
            time = 0;
            turns = 0;
            lives = value;
        }
        image = 0;
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
        return "" + value + " " + type + " " + image;
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
