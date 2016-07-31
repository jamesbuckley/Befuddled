package com.apps.jamesbuckley.flummoxed.gameLogic;

import java.util.Arrays;

/**
 * Created by ejambuc on 08/08/14.
 */
public class AssessGuess {

    private int[] status;
    private boolean[] guessStatus;
    private GameStats stats;

    public AssessGuess(GameStats stats){
        this.stats=stats;
    }

    public int[] evaluateGuess(int guess){

        status = new int[stats.getNumberOfDigits()];
        guessStatus = new boolean[stats.getNumberOfDigits()];
        String stringGuess = Integer.toString(guess);
        assessBlackBalls(stringGuess);
        assessWhiteBalls(stringGuess);
        isGameWon();
        return status;
    }

    public boolean isGameWon(){
    	int[] winning = {2,2,2,2,2};
    	if(Arrays.equals(status, winning)){
    		return true;
    	}
		return false;
    }

    private void assessBlackBalls(String guess){
        for(int i=0;i<stats.getNumberOfDigits();i++){
            if(stats.getAnswer().charAt(i)==guess.charAt(i)){
                status[i]=2;guessStatus[i]=true;
            }
        }
    }

    private void assessWhiteBalls(String guess){
        for(int i=0;i<stats.getNumberOfDigits();i++){
            if(status[i]==2){}
            else{
                 for(int j=0;j<guess.length();j++){
                     //status[j]!=2 doesnt make sense to me
                        if(stats.getAnswer().charAt(i)==guess.charAt(j)&&(i!=j)&&(status[j]!=2)&&(guessStatus[j]!=true)){
                            status[j]=1;guessStatus[j]=true;
                        }
                    }
                }
            }
    }

	/**
	 * @param i
	 * @return
	 */
	public CharSequence getBalls() {
		String guessResults="";
		
		for(Integer i: status){
			if(i==2){
				guessResults+="G";
			}else if(i==1){
				guessResults+="O";
			}else if(stats.isTutorial()){
				guessResults+="R";
			}
		}
		
		return guessResults;
	}
		
}
