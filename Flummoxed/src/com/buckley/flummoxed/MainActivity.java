package com.buckley.flummoxed;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		}
	}

	/**
	 * @return
	 */
	private boolean canAcceptNumber(String guess) {
		if(guess.length()<5) return true;
		return false;
	}
}
