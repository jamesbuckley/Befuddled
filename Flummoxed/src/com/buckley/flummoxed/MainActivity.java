package com.buckley.flummoxed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

import com.buckley.flummoxed.gameLogic.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends Activity {

	private AssessGuess assess;
	private GameStats stats;
	private Stack<Button> disabledButtons = new Stack<Button>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F19B28"));
		stats = new GameStats(5,RandomNumberGenerator.getNonrepeatingRandomNumber(largestNumberAllowed(5)));
		assess = new AssessGuess(stats);
		isTutorial();
		setLongClickListeners();
	}

	/**
	 * 
	 */
	private void isTutorial() {
		Intent intent = getIntent();
		String isTutorialExtra = intent.getStringExtra("com.buckley.flummoxed.Tutorial");
		if(isTutorialExtra!=null){
			stats.setTutorial(true);
		}
		
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

		if(canAcceptNumber(guessbar.getText().toString()) && view.isClickable()){
			Button numberButton = (Button) view;
			String guess = (guessbar.getText().toString())+(numberButton.getText().toString());
			guessbar.setText(guess);
			numberButton.setEnabled(false);
			disabledButtons.add(numberButton);
			Button zeroButton = (Button) findViewById(R.id.buttonNumber0);
			zeroButton.setEnabled(true);
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
			Button enableButton = disabledButtons.lastElement();
			enableButton.setEnabled(true);
			disabledButtons.remove(enableButton);
			if(guess.substring(0, guess.length()-1).length()==0){
				Button zeroButton = (Button) findViewById(R.id.buttonNumber0);
				zeroButton.setEnabled(false);
			}			
		}
	}

	public void enterGuess(View view){
		TextView guessbar = (TextView) findViewById(R.id.guessBar);
		String guess = guessbar.getText().toString();
		if((!guess.isEmpty())&& (guess.length()==5)&& stats.newGuess(Integer.parseInt(guess))){
			assess.evaluateGuess(Integer.parseInt(guess));
			guessbar.setText("");
			showResults(guess);
			resetButtons();
			if(assess.isGameWon()){
				Intent intent = new Intent(this, VictoryActivity.class);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(intent);
			}
		}else{
			//Mark guess as invalid, repeat process
		}
	}

	/**
	 * 
	 */
	private void resetButtons() {
		while(!disabledButtons.isEmpty()){
			Button enable = disabledButtons.pop();
			enable.setEnabled(true);
		}
		Button zeroButton = (Button) findViewById(R.id.buttonNumber0);
		zeroButton.setEnabled(false);
		
	}
	
	private void setLongClickListeners(){
		 ArrayList<Button> buttons = new ArrayList<Button>();
		 buttons.add((Button) findViewById(R.id.buttonNumber0));
		 buttons.add((Button)  findViewById(R.id.buttonNumber1));
		 buttons.add((Button)  findViewById(R.id.buttonNumber2));
		 buttons.add((Button)  findViewById(R.id.buttonNumber3));
		 buttons.add((Button)  findViewById(R.id.buttonNumber4));
		 buttons.add((Button)  findViewById(R.id.buttonNumber5));
		 buttons.add((Button)  findViewById(R.id.buttonNumber6));
		 buttons.add((Button)  findViewById(R.id.buttonNumber7));
		 buttons.add((Button)  findViewById(R.id.buttonNumber8));
		 buttons.add((Button)  findViewById(R.id.buttonNumber9));
		 
		 for (Button button: buttons){
		     button.setOnLongClickListener(new View.OnLongClickListener() {
		         public boolean onLongClick(View v) {
		        	 includeExcludeButton(v);
		             return true;
		         }
		     });
		 }
	}
	
	private void includeExcludeButton(View button) {
		if(button.isClickable()){
	 		button.setClickable(false);
			button.setBackgroundColor(0xFFFF0000);
		}else{
	 		button.setClickable(true);
	 		button.setBackgroundResource(android.R.drawable.btn_default);
		}

		
	}

	/**
	 * 
	 */
 	private void showResults(String guess) {
		TextView guessbar;
		switch(stats.getLivesLeft()){
		case(9):
			guessbar = (TextView) findViewById(R.id.showAnswerText1);
		guessbar.setText(("#1   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine1));
		break;
		case(8):
			guessbar = (TextView) findViewById(R.id.showAnswerText2);
		guessbar.setText(("#2   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine2));
		break;
		case(7):
			guessbar = (TextView) findViewById(R.id.showAnswerText3);
		guessbar.setText(("#3   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine3));
		break;
		case(6):
			guessbar = (TextView) findViewById(R.id.showAnswerText4);
		guessbar.setText(("#4   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine4));
		break;
		case(5):
			guessbar = (TextView) findViewById(R.id.showAnswerText5);
		guessbar.setText(("#5   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine5));
		break;
		case(4):
			guessbar = (TextView) findViewById(R.id.showAnswerText6);
		guessbar.setText(("#6   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine6));
		break;
		case(3):
			guessbar = (TextView) findViewById(R.id.showAnswerText7);
		guessbar.setText(("#7   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine7));
		break;
		case(2):
			guessbar = (TextView) findViewById(R.id.showAnswerText8);
		guessbar.setText(("#8   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine8));
		break;
		case(1):
			guessbar = (TextView) findViewById(R.id.showAnswerText9);
		guessbar.setText(("#9   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine9));
		break;
		case(0):
			guessbar = (TextView) findViewById(R.id.showAnswerText10);
		guessbar.setText(("#10   ")+guess);
		setBallImages(assess.getBalls(), (LinearLayout) findViewById(R.id.answerLine10));
		break;
		}
	}

	/**
	 * @param balls
	 */
	private void setBallImages(CharSequence balls, LinearLayout answerRow) {
		ImageView ballImage;
		
		for(int i=1; i<balls.length()+1;i++){
			ballImage = (ImageView) answerRow.getChildAt(i);
			if(balls.charAt(i-1)=='O'){
				ballImage.setImageResource(R.drawable.orange_ball);
			}
			else if(balls.charAt(i-1)=='G'){
				ballImage.setImageResource(R.drawable.green_ball);
			}
			else if(balls.charAt(i-1)=='R'){
				ballImage.setImageResource(R.drawable.red_ball);
			}
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
