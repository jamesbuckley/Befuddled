package com.buckley.flummoxed.gameLogic;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ejambuc on 12/08/14.
 */
public class GameStats {

    private int livesLeft = 10;
    private String name="Guest";
    private Set previousGuesses = new HashSet();
    private boolean gameOver;
    private int answer;
    private int numberOfDigits;

    public GameStats(int numberOfDigits, int answer){
        this.answer=answer;
        this.numberOfDigits=numberOfDigits;
    }

    public GameStats(int numberOfDigits, int answer,int numberOfLives){
        this.answer=answer;
        System.out.println(answer);
        livesLeft=numberOfLives;
    }

    public GameStats(int answer,int numberOfLives, String name){
        this.answer=answer;
        livesLeft=numberOfLives;
        this.name=name;
    }

    public boolean newGuess(int guess){
        if(previousGuesses.contains(guess)){
            return false;
        }
        else{
            previousGuesses.add(guess);
            decrementLives();
            return true;
        }
    }

    public String getAnswer(){
        return Integer.toString(answer);
    }

    private void decrementLives(){
        livesLeft--;
        if(livesLeft==0){
            setGameOver();
        }
    }

    public int getLivesLeft(){
        return livesLeft;
    }
    public boolean isGameOver(){return gameOver;}
    private void setGameOver(){gameOver=true;}
    public int getNumberOfDigits(){return numberOfDigits;}
}
