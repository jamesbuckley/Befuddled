package com.buckley.flummoxed.gameLogic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by ejambuc on 08/08/14.
 */
public class RandomNumberGenerator {

    public static int getNonrepeatingRandomNumber(int max){
        Random random = new Random();
        int candidateNumber = random.nextInt(max);
        while(!validNumber(candidateNumber, max)){
            candidateNumber = random.nextInt(max);
        }
        return candidateNumber;
    }

    private static boolean areDigitsUnique(int candidateNumber){
        String stringCandidate=Integer.toString(candidateNumber);
        Set uniqueDigits = new HashSet<String>();
        for (int i=0; i<stringCandidate.length();i++){
            uniqueDigits.add(stringCandidate.substring(i,i+1));
        }
        if(uniqueDigits.size()==stringCandidate.length()) return true;
        return false;
    }

    private static boolean validNumber(int candidateNumber, int max){
        if(candidateNumber<=((max/10)-1)){
            return false;
        }else{
            return areDigitsUnique(candidateNumber);
        }
    }

}
