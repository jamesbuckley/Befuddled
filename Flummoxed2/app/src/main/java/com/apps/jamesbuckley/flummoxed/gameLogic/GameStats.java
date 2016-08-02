package com.apps.jamesbuckley.flummoxed.gameLogic;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ejambuc on 12/08/14.
 */
public class GameStats {

    private int livesLeft = 10;
    private String name="Guest";
    private Set<Integer> previousGuesses = new HashSet<Integer>();
    private boolean gameOver;
    private int answer;
    private int numberOfDigits;
    private boolean isTutorial;
    private int numberOfGuesses = 0;

    /**
	 * @return the isTutorial
	 */
	public boolean isTutorial() {
		return isTutorial;
	}

	/**
	 * @param isTutorial the isTutorial to set
	 */
	public void setTutorial(boolean isTutorial) {
		this.isTutorial = isTutorial;
	}

	public GameStats(int numberOfDigits, int answer){
        this.answer=answer;
        this.numberOfDigits=numberOfDigits;
    }

    public GameStats(int numberOfDigits, int answer,int numberOfLives){
        this.answer=answer;
        this.numberOfDigits=numberOfDigits;
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
            numberOfGuesses++;
            return true;
        }
    }

    public String getAnswer(){
        return Integer.toString(answer);
    }

    private void decrementLives(){
        livesLeft--;
        if(livesLeft==0){
            gameOver=true;
        }
    }

    public int getLivesLeft(){
        return livesLeft;
    }
    public boolean isGameOver(){return gameOver;}
    public int getNumberOfDigits(){return numberOfDigits;}
    public int getNumberOfGuesses(){return numberOfGuesses;}

    public static int largestNumberAllowed(int requestedNumberOfDigits){
        switch(requestedNumberOfDigits){
            case(4): return 10000;
            case(5): return 100000;
            case(6): return 1000000;
            default: return 100000;
        }
    }
}
