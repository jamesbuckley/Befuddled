package com.apps.jamesbuckley.flummoxed;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.jamesbuckley.flummoxed.gameLogic.AssessGuess;
import com.apps.jamesbuckley.flummoxed.gameLogic.GameStats;
import com.apps.jamesbuckley.flummoxed.gameLogic.RandomNumberGenerator;
import com.jaouan.revealator.Revealator;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private AssessGuess assess;
    private GameStats stats;
    private View numpad;
    private TextView guessText;
    private String currentGuess = "";
    private Stack<Button> disabledButtons = new Stack<Button>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        numpad = findViewById(R.id.numpad);
        numpad.setVisibility(View.INVISIBLE);
        setDisabledButtonsStartState();
        guessText = (TextView)findViewById(R.id.currentGuessView);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleNumpad();
            }
        });
        stats = new GameStats(5,RandomNumberGenerator.getNonrepeatingRandomNumber(GameStats.largestNumberAllowed(5)));
        assess = new AssessGuess(stats);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Button methods
    ////////////////////////////////////////////

    public void enterNumber(View numberButton){
        Button button = (Button) numberButton;
        String buttonText = button.getText().toString();
        if(currentGuess.length()<5){
            currentGuess = currentGuess+buttonText;
            guessText.setText(currentGuess);
            button.setEnabled(false);
            if(disabledButtons.isEmpty()){
                setDisabledButtonsGameState();
            }
            disabledButtons.push(button);
        }
        if(currentGuess.length()==5){
            Button enter = (Button) findViewById(R.id.button_number_enter);
            enter.setEnabled(true);
            enter.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void backspace(View view){
        if(currentGuess.length()>0){
            currentGuess = currentGuess.substring(0, currentGuess.length()-1);
            guessText.setText(currentGuess);
            disabledButtons.lastElement().setEnabled(true);
            disabledButtons.pop();
            Button enter = (Button) findViewById(R.id.button_number_enter);
            enter.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            enter.setEnabled(false);
        }
        if(disabledButtons.isEmpty()){
            setDisabledButtonsStartState();
        }
    }

    public void enterGuess(View view){
        int guessInt = Integer.parseInt(currentGuess);
        if(stats.newGuess(guessInt)){
            int[] guessFeedback = assess.evaluateGuess(guessInt);
            revertUI();
            showGuessFeedback(guessFeedback);
            currentGuess = "";
            decrementLives();
        }else{
            //Mark guess as invalid, repeat process
        }
    }

    // UI Control methods
    ////////////////////////////////////////////
    private void setDisabledButtonsStartState() {
        Button zero = (Button) findViewById(R.id.button_number_0);
        zero.setEnabled(false);
        Button enter = (Button) findViewById(R.id.button_number_enter);
        enter.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        enter.setEnabled(false);
        Button back = (Button) findViewById(R.id.button_number_back);
        back.setEnabled(false);
    }

    private void setDisabledButtonsGameState() {
        Button zero = (Button) findViewById(R.id.button_number_0);
        zero.setEnabled(true);
        Button back = (Button) findViewById(R.id.button_number_back);
        back.setEnabled(true);
    }

    private void toggleNumpad(){
        
        if(numpad.getVisibility()==View.VISIBLE){
            Revealator.unreveal(numpad).withDuration(350).start();
        }else{
            Revealator.reveal(numpad).withChildsAnimation().withRevealDuration(200).start();
        }
    }

    private void revertUI(){
        while(!disabledButtons.isEmpty()){
            disabledButtons.pop().setEnabled(true);
        }
        setDisabledButtonsStartState();
        guessText.setText("");
    }

    private void showGuessFeedback(int[] guessFeedbackInts){
        String guessTextViewString = "guess_textView_"+ stats.getNumberOfGuesses();
        String guessImageContainerString = "guess_imageLayout_"+stats.getNumberOfGuesses();
        TextView guessTextView = (TextView) findViewById(getResources().getIdentifier(guessTextViewString, "id", "com.apps.jamesbuckley.flummoxed"));
        guessTextView.setText(currentGuess);
        LinearLayout resultImageContainer = (LinearLayout)findViewById(getResources().getIdentifier(guessImageContainerString, "id", "com.apps.jamesbuckley.flummoxed"));
        for(int feedbackInt: guessFeedbackInts){
            if(feedbackInt!=0){
                resultImageContainer.addView(createImageView(feedbackInt));
            }
        }
    }

    private void decrementLives(){
        String lifeImage = "life_number_" + Integer.toString(stats.getLivesLeft());
        ImageView lifeCounter = (ImageView) findViewById(R.id.lifeCounterImageView);
        lifeCounter.setImageResource(getResources().getIdentifier(lifeImage, "drawable", "com.apps.jamesbuckley.flummoxed"));
    }

    private ImageView createImageView(int feedbackInt){
        ImageView resultImage = new ImageView(this);
        resultImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if(feedbackInt==1){
            resultImage.setImageResource(R.drawable.yellow_ball);
        }else if(feedbackInt == 2){
            resultImage.setImageResource(R.drawable.green_ball);
        }

        return resultImage;
    }

}
