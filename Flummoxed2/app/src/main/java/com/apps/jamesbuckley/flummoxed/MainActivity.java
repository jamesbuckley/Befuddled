package com.apps.jamesbuckley.flummoxed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.jamesbuckley.flummoxed.gameLogic.AssessGuess;
import com.apps.jamesbuckley.flummoxed.gameLogic.GameStats;
import com.apps.jamesbuckley.flummoxed.gameLogic.RandomNumberGenerator;
import com.easyandroidanimations.library.ExplodeAnimation;
import com.jaouan.revealator.Revealator;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private AssessGuess assess;
    private GameStats stats;
    private View numpad;
    private TextView guessText;
    private String currentGuess = "";
    private Stack<Button> disabledButtons = new Stack<Button>();
    private FloatingActionButton fab;
    private String difficultyLevel;
    private SharedPreferences sharedPreferences;
    private ViewGroup mContainerView;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private int winStreak;
    private int totalWins;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        difficultyLevel = intent.getStringExtra(MenuActivity.DIFFICULTY_LEVEL);
        initialiseViews();
        setDisabledButtonsStartState();
        newGameCounter();
        int numberOfLives= getNumberOfLives();
        showTutorial();
        setStartingLivesImage(numberOfLives);
        mp = MediaPlayer.create(this, R.raw.guess_entered_sound);

        stats = new GameStats(5,RandomNumberGenerator.getNonrepeatingRandomNumber(GameStats.largestNumberAllowed(5)), numberOfLives);
        assess = new AssessGuess(stats);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
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
        }else if(id == R.id.new_game_action){
            //pass in any view, not used in restart
            winStreak=0;
            switch (difficultyLevel.toLowerCase()){
                case "beginner":
                    editor.putInt("winStreakBeginner",winStreak);
                    break;
                case "intermediate":
                    editor.putInt("winStreakIntermediate",winStreak);
                    break;
                case "expert":
                    editor.putInt("winStreakExpert",winStreak);
            }
            editor.commit();
            restart(numpad);
        }else if(id == R.id.main_menu_action){
            mainMenu(numpad);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        ViewGroup helperInfo = (ViewGroup)findViewById(R.id.info_indicator_layout);
        if (helperInfo.getVisibility()==View.VISIBLE && difficultyLevel.equalsIgnoreCase("beginner")) {
            YoYo.with(Techniques.FadeOutUp).delay(15000).duration(5000).playOn(helperInfo);
            YoYo.with(Techniques.Flash).delay(6000).duration(5000).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    YoYo.with(Techniques.Flash).delay(5000).duration(5000).playOn(fab);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(fab);
        }
    }


//    // Button methods
//    ////////////////////////////////////////////
//
    public void enterNumber(View numberButton){
        Button button = (Button) numberButton;
        String buttonText = button.getText().toString();
        if(currentGuess.length()<5){
            currentGuess = currentGuess+buttonText;
            guessText.setText(currentGuess);
            YoYo.with(Techniques.FadeIn).duration(200).playOn(guessText);
            button.setEnabled(false);
            if(disabledButtons.isEmpty()){
                setDisabledButtonsGameState();
            }
            disabledButtons.push(button);
        }
        if(currentGuess.length()==5){
            Button enter = (Button) findViewById(R.id.button_number_enter);
            enter.setEnabled(true);
            enter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
            if(!assess.isGameWon){
                decrementLives();
            }else{
                gameWinnerLogic();
            }
        }else{
            YoYo.with(Techniques.Shake).playOn(guessText);
        }
    }

    public void mainMenu(View view){
        intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

    public void restart(View view){
        this.recreate();
    }

    public void hideTutorial(View view){
        View tutorial = (View)view.getParent().getParent();
        tutorial.setVisibility(View.GONE);
    }
//
//    // UI Control methods
//    ////////////////////////////////////////////
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
        YoYo.with(Techniques.FadeOut).playOn(guessText);
    }

    private void showGuessFeedback(int[] guessFeedbackInts){

        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.guess_feedback_row, mContainerView, false);


        ((TextView) newView.findViewById(R.id.guess_textView_1)).setText(currentGuess);
        YoYo.with(Techniques.FadeIn).delay(200).playOn(newView.findViewById(R.id.guess_textView_1));
        LinearLayout resultImageContainer = (LinearLayout)newView.findViewById(R.id.guess_imageLayout_1);
        mContainerView.addView(newView, 0);
        for(int feedbackInt: guessFeedbackInts){
            if(!(difficultyLevel.equalsIgnoreCase("expert") & feedbackInt==0)){
                final ImageView ballImage = createImageView(feedbackInt);
                if(difficultyLevel.equalsIgnoreCase("intermediate") & feedbackInt==0){
                    YoYo.with(Techniques.FadeOut).delay(4000).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ballImage.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).playOn(ballImage);
                }
                resultImageContainer.addView(ballImage);
                YoYo.with(Techniques.BounceIn).delay(200).playOn(ballImage);
            }
        }
    }

    private void decrementLives(){
        if(stats.isGameOver()){
            new ExplodeAnimation(numpad)
                    .setExplodeMatrix(ExplodeAnimation.MATRIX_3X3)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(400)
                    .animate();
            ImageView lifeCounter = (ImageView) findViewById(R.id.lifeCounterImageView);
            lifeCounter.setVisibility(View.INVISIBLE);
            callGameOverSplash();
        }else{
            String lifeImage = "life_number_" + Integer.toString(stats.getLivesLeft());
            ImageView lifeCounter = (ImageView) findViewById(R.id.lifeCounterImageView);
            YoYo.with(Techniques.Flash).playOn(lifeCounter);
            mp.start();
            lifeCounter.setImageResource(getResources().getIdentifier(lifeImage, "drawable", "com.apps.jamesbuckley.flummoxed"));
        }
    }

    private void callGameOverSplash() {
        winStreak=0;
        switch (difficultyLevel.toLowerCase()){
            case "beginner":
                editor.putInt("winStreakBeginner",winStreak);
                break;
            case "intermediate":
                editor.putInt("winStreakIntermediate",winStreak);
                break;
            case "expert":
                editor.putInt("winStreakExpert",winStreak);
        }
        editor.commit();
        View transparentOverlay = findViewById(R.id.transparentOverlay);
        ViewGroup gameOverSplash = (ViewGroup)findViewById(R.id.game_over_splash);
        ((TextView)gameOverSplash.findViewById(R.id.answerText)).setText(stats.getAnswer());
        transparentOverlay.setVisibility(View.VISIBLE);
        gameOverSplash.setVisibility(View.VISIBLE);
        mp = MediaPlayer.create(this, R.raw.losing_failfare_audio);
        mp.start();
        YoYo.with(Techniques.BounceInDown).duration(1500).playOn(gameOverSplash);
    }

    private void callGameWonSplash(String winPercentage, int winStreak){
        View transparentOverlay = findViewById(R.id.transparentOverlay);
        ViewGroup gameWinnerSplash = (ViewGroup)findViewById(R.id.game_winner_splash);
        TextView guessNumber = (TextView)gameWinnerSplash.findViewById(R.id.number_of_guess_text);
        guessNumber.setText(Integer.toString(stats.getNumberOfGuesses()));
        TextView winStreakText = (TextView)gameWinnerSplash.findViewById(R.id.win_streak_text);
        winStreakText.setText(Integer.toString(winStreak));
        ((TextView)findViewById(R.id.win_percentage_text)).setText(winPercentage+"%");
        transparentOverlay.setVisibility(View.VISIBLE);
        gameWinnerSplash.setVisibility(View.VISIBLE);
        mp = MediaPlayer.create(this, R.raw.winning_tada_audio);
        mp.start();
        YoYo.with(Techniques.Landing).duration(3000).playOn(gameWinnerSplash);
    }

    private ImageView createImageView(int feedbackInt){
        ImageView resultImage = new ImageView(this);
        resultImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if(feedbackInt==1){
            resultImage.setImageResource(R.drawable.yellow_ball);
        }else if(feedbackInt == 2){
            resultImage.setImageResource(R.drawable.green_ball);
        }else if(feedbackInt == 0){
            resultImage.setImageResource(R.drawable.hollow_yellow);
        }

        return resultImage;
    }

    private void gameWinnerLogic(){
        winStreak++;
        totalWins++;
        switch (difficultyLevel.toLowerCase()){
            case "beginner":
                editor.putInt("winStreakBeginner",winStreak);
                editor.putInt("totalWinsBeginner",totalWins);
                editor.putBoolean("beginnerTutoralComplete", true);
                break;
            case "intermediate":
                editor.putInt("winStreakIntermediate",winStreak);
                editor.putInt("totalWinsIntermediate",totalWins);
                editor.putBoolean("intermediateTutoralComplete", true);
                break;
            case "expert":
                editor.putInt("winStreakExpert",winStreak);
                editor.putInt("totalWinsExpert",totalWins);
                editor.putBoolean("expertTutoralComplete", true);
        }
        if(winStreak==5 && difficultyLevel.equalsIgnoreCase("beginner")){
            editor.putInt("intermediateUnlocked", 1);
            ViewGroup tutorialView = (ViewGroup) findViewById(R.id.info_indicator_layout);
            tutorialView.setVisibility(View.VISIBLE);
            ((TextView)tutorialView.findViewById(R.id.info_text_view)).setText("Intermediate Unlocked");
            tutorialView.findViewById(R.id.tutorial_table_beginner).setVisibility(View.GONE);
            tutorialView.findViewById(R.id.tutorial_table_intermediate).setVisibility(View.VISIBLE);
            ((TextView)tutorialView.findViewById(R.id.tutorial_intermediate_textview)).setText(R.string.info_intermediate_unlocked);
        }else if(winStreak==5 && difficultyLevel.equalsIgnoreCase("intermediate")){
            editor.putInt("expertUnlocked", 1);
            ViewGroup tutorialView = (ViewGroup) findViewById(R.id.info_indicator_layout);
            tutorialView.setVisibility(View.VISIBLE);
            ((TextView)tutorialView.findViewById(R.id.info_text_view)).setText("Expert Unlocked");
            tutorialView.findViewById(R.id.tutorial_table_intermediate).setVisibility(View.VISIBLE);
            tutorialView.findViewById(R.id.tutorial_table_beginner).setVisibility(View.GONE);
            ((TextView)tutorialView.findViewById(R.id.tutorial_intermediate_textview)).setText(R.string.info_expert_unlocked);
        }
        editor.commit();
        int numberOfGames = sharedPreferences.getInt("number_of_games", 0);
        double winPercentage = ((double)totalWins/numberOfGames)*100;
        NumberFormat formatter = new DecimalFormat("#0.00");
        callGameWonSplash(formatter.format(winPercentage), winStreak);
    }

    private void initialiseViews(){
        mContainerView = (ViewGroup) findViewById(R.id.container);
        numpad = findViewById(R.id.numpad);
        numpad.setVisibility(View.INVISIBLE);
        guessText = (TextView)findViewById(R.id.currentGuessView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleNumpad();
            }
        });
        sharedPreferences = getSharedPreferences("flummoxedSharedPreferences", 0);
        editor = sharedPreferences.edit();
    }

    private void newGameCounter(){
        int totalNumberOfGames = sharedPreferences.getInt("number_of_games", 0);
        totalNumberOfGames++;
        editor.putInt("number_of_games",totalNumberOfGames);
        editor.commit();
    }

    private void showTutorial(){
        boolean tutorialComplete;
        switch (difficultyLevel.toLowerCase()){
            case "beginner":
                tutorialComplete = sharedPreferences.getBoolean("beginnerTutoralComplete", false);
                if(!tutorialComplete){
                    ViewGroup tutorialView = (ViewGroup) findViewById(R.id.info_indicator_layout);
                    tutorialView.setVisibility(View.VISIBLE);
                    tutorialView.findViewById(R.id.tutorial_table_beginner).setVisibility(View.VISIBLE);
                    tutorialView.findViewById(R.id.tutorial_table_intermediate).setVisibility(View.GONE);
                }else {
                    if(totalWins==1){
                        ViewGroup tutorialView = (ViewGroup) findViewById(R.id.info_indicator_layout);
                        tutorialView.setVisibility(View.VISIBLE);
                        ((TextView)tutorialView.findViewById(R.id.info_text_view)).setText("Unlock Next Level");
                        tutorialView.findViewById(R.id.tutorial_table_beginner).setVisibility(View.INVISIBLE);
                        tutorialView.findViewById(R.id.intermed_image_button).setVisibility(View.GONE);
                        tutorialView.findViewById(R.id.tutorial_table_intermediate).setVisibility(View.VISIBLE);
                        ((TextView)tutorialView.findViewById(R.id.tutorial_intermediate_textview)).setText(R.string.tutorial_unlock_next_level);
                    }
                }
                break;
            case "intermediate":
                tutorialComplete = sharedPreferences.getBoolean("intermediateTutoralComplete", false);
                if(!tutorialComplete){
                    ViewGroup tutorialView = (ViewGroup) findViewById(R.id.info_indicator_layout);
                    tutorialView.setVisibility(View.VISIBLE);
                    tutorialView.findViewById(R.id.tutorial_table_intermediate).setVisibility(View.VISIBLE);
                    tutorialView.findViewById(R.id.tutorial_table_beginner).setVisibility(View.GONE);
                }
                break;
            case "expert":
                tutorialComplete = sharedPreferences.getBoolean("expertTutoralComplete", false);
                if(!tutorialComplete){
                    ViewGroup tutorialView = (ViewGroup) findViewById(R.id.info_indicator_layout);
                    tutorialView.setVisibility(View.VISIBLE);
                    ((TextView)tutorialView.findViewById(R.id.tutorial_intermediate_textview)).setText(R.string.tutorial_expert_game);
                    tutorialView.findViewById(R.id.tutorial_table_intermediate).setVisibility(View.VISIBLE);
                    tutorialView.findViewById(R.id.tutorial_table_beginner).setVisibility(View.GONE);
                }
        }
    }

    private int getNumberOfLives(){
        switch (difficultyLevel.toLowerCase()){
            case "beginner":
                winStreak = sharedPreferences.getInt("winStreakBeginner", 0);
                totalWins = sharedPreferences.getInt("totalWinsBeginner", 0);
                return 8;
            case "intermediate":
                winStreak = sharedPreferences.getInt("winStreakIntermediate", 0);
                totalWins = sharedPreferences.getInt("totalWinsIntermediate", 0);
                return 9;
            case "expert":
                winStreak = sharedPreferences.getInt("winStreakExpert", 0);
                totalWins = sharedPreferences.getInt("totalWinsExpert", 0);
                return 10;
        }
        return 10;
    }

    private void setStartingLivesImage(int numberOfLives) {
        String lifeImage = "life_number_" + numberOfLives;
        ImageView lifeCounter = (ImageView) findViewById(R.id.lifeCounterImageView);
        lifeCounter.setImageResource(getResources().getIdentifier(lifeImage, "drawable", "com.apps.jamesbuckley.flummoxed"));
    }

}
