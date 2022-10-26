package com.example.memorymatch;

public class Card {
    int points, time, flips;
    boolean flipped, matched;

    public Card(int p, int t, int f){
        points = p;
        time = t;
        flips = f;
        flipped = false;
        matched = false;
    }
}
