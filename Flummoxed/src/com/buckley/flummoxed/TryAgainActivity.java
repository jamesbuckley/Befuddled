package com.buckley.flummoxed;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

public class TryAgainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_try_again);
		TextView textAnswer = (TextView) findViewById(R.id.losing_answer);
		textAnswer.setText("Sorry! The Answer was " + getAnswer());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.try_again, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}
	
	public void playAgain(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	private String getAnswer(){
		Intent intent = getIntent();
		String answer = intent.getStringExtra("com.buckley.flummoxed.LosingAnswer");
		return answer;
	}
}
