package com.buckley.flummoxed.gameLogic;

/**
 * Created by ejambuc on 12/08/14.
 */
public class Controller {

    private AssessGuess assess;
    private GameStats stats;

    public Controller(int numberOfDigits){
        stats = new GameStats(numberOfDigits,RandomNumberGenerator.getNonrepeatingRandomNumber(largestNumberAllowed(numberOfDigits)));
        assess = new AssessGuess(stats);
    }

    public void enterGuess(int guess){
        if(stats.newGuess(guess)){
            assess.evaluateGuess(guess);
        }else{
            //Mark guess as invalid, repeat process
        }
    }

    private static int largestNumberAllowed(int requestedNumberOfDigits){
        switch(requestedNumberOfDigits){
            case(4): return 10000;
            case(5): return 100000;
            case(6): return 1000000;
            default: return 100000;
        }
    }
}
