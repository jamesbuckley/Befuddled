package com.buckley.flummoxed.gameLogic;

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
            gameOver=true;
        }
    }

    public int getLivesLeft(){
        return livesLeft;
    }
    public boolean isGameOver(){return gameOver;}
    public int getNumberOfDigits(){return numberOfDigits;}
}
