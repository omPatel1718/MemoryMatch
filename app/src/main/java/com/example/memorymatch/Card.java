package com.example.memorymatch;

public class Card {
    int value;
    boolean flipped, matched;

    public Card(int v){
        flipped = false;
        matched = false;
        value = v;
    }

    public void clicked(){
        if(!flipped){
            flipped = true;
        }
    }

    public int getPoints() {
        return value;
    }

    public void setPoints(int value) {
        this.value = value;
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
