package com.buckley.flummoxed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;

public class VictoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_victory);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.victory, menu);
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
		Intent intent = new Intent(this, TitleActivity.class);
		startActivity(intent);
	}
	
	public void quit(){
		//System.exit(0);
	}
}
