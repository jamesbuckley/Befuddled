package com.buckley.flummoxed;

import com.buckley.flummoxed.gameLogic.*;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;

public class MainActivity extends ActionBarActivity {

	private AssessGuess assess;
	private GameStats stats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		stats = new GameStats(5,RandomNumberGenerator.getNonrepeatingRandomNumber(largestNumberAllowed(5)));
		assess = new AssessGuess(stats);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void enterNumber(View view){

		TextView guessbar = (TextView) findViewById(R.id.guessBar);

		if(canAcceptNumber(guessbar.getText().toString())){
			Button numberButton = (Button) view;
			String guess = (guessbar.getText().toString())+(numberButton.getText().toString());
			guessbar.setText(guess);
			//disableButton();
		}
	}

	/**
	 * @return
	 */
	private boolean canAcceptNumber(String guess) {
		if(guess.length()<5) return true;
		return false;
	}

	public void backspace(View view){
		TextView guessbar = (TextView) findViewById(R.id.guessBar);
		String guess = guessbar.getText().toString();
		if(guess.length()>0){
			guessbar.setText(guess.substring(0, guess.length()-1));
		}
	}

	public void enterGuess(View view){
		TextView guessbar = (TextView) findViewById(R.id.guessBar);
		String guess = guessbar.getText().toString();
		if(stats.newGuess(Integer.parseInt(guess))&&(guess.length()==5)){
			assess.evaluateGuess(Integer.parseInt(guess));
			guessbar.setText("");
			showResults(guess);
		}else{
			//Mark guess as invalid, repeat process
		}
	}

	/**
	 * 
	 */
	private void showResults(String guess) {
		TextView guessbar;
		TextView imageView;
		switch(stats.getLivesLeft()){
		case(9):
			guessbar = (TextView) findViewById(R.id.showAnswerText1);
		imageView = (TextView) findViewById(R.id.showAnswerImage1);
		guessbar.setText(("#1   ")+guess);
		imageView.setText(assess.getBalls());
		
		break;
		case(8):
			guessbar = (TextView) findViewById(R.id.showAnswerText2);
		imageView = (TextView) findViewById(R.id.showAnswerImage2);
		guessbar.setText(("#2   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(7):
			guessbar = (TextView) findViewById(R.id.showAnswerText3);
		imageView = (TextView) findViewById(R.id.showAnswerImage3);
		guessbar.setText(("#3   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(6):
			guessbar = (TextView) findViewById(R.id.showAnswerText4);
		imageView = (TextView) findViewById(R.id.showAnswerImage4);
		guessbar.setText(("#4   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(5):
			guessbar = (TextView) findViewById(R.id.showAnswerText5);
		imageView = (TextView) findViewById(R.id.showAnswerImage5);
		guessbar.setText(("#5   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(4):
			guessbar = (TextView) findViewById(R.id.showAnswerText6);
		imageView = (TextView) findViewById(R.id.showAnswerImage6);
		guessbar.setText(("#6   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(3):
			guessbar = (TextView) findViewById(R.id.showAnswerText7);
		imageView = (TextView) findViewById(R.id.showAnswerImage7);
		guessbar.setText(("#7   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(2):
			guessbar = (TextView) findViewById(R.id.showAnswerText8);
		imageView = (TextView) findViewById(R.id.showAnswerImage8);
		guessbar.setText(("#8   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(1):
			guessbar = (TextView) findViewById(R.id.showAnswerText9);
		imageView = (TextView) findViewById(R.id.showAnswerImage9);
		guessbar.setText(("#9   ")+guess);
		imageView.setText(assess.getBalls());
		break;
		case(0):
			guessbar = (TextView) findViewById(R.id.showAnswerText10);
		imageView = (TextView) findViewById(R.id.showAnswerImage10);
		guessbar.setText(("#10   ")+guess);
		imageView.setText(assess.getBalls());
		break;
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
