package com.apps.jamesbuckley.flummoxed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.PathAnimation;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    ImageView firstGreen, secondGreen, firstYellow, secondYellow, thirdGreen, thirdYellow;
    private Intent intent;
    public static String DIFFICULTY_LEVEL;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firstGreen = (ImageView) findViewById(R.id.firstGreenBall);
        firstYellow = (ImageView) findViewById(R.id.firstYellowBall);
        secondGreen = (ImageView) findViewById(R.id.secondGreenBall);
        secondYellow = (ImageView) findViewById(R.id.secondYellowBall);
        thirdGreen = (ImageView) findViewById(R.id.thirdGreenBall);
        thirdYellow = (ImageView) findViewById(R.id.thirdYellowBall);

        intent = new Intent(this, MainActivity.class);
        sharedPreferences = getSharedPreferences("flummoxedSharedPreferences", 0);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            unlockGameModes();
            startPaths();
        }
    }

    private void unlockGameModes() {
        int intermediateUnlocked = sharedPreferences.getInt("intermediateUnlocked", 0);
        int expertUnlocked = sharedPreferences.getInt("expertUnlocked", 0);
        Button intermediateButton = (Button) findViewById(R.id.intermediateGameButton);
        Button expertButton = (Button) findViewById(R.id.expertGameButton);
        if(intermediateUnlocked==1){
            intermediateButton.setEnabled(true);
            intermediateButton.setBackground(getResources().getDrawable(R.drawable.green_rounded_rectangle));
        }else{
            intermediateButton.setEnabled(false);
            intermediateButton.setBackground(getResources().getDrawable(R.drawable.grey_rounded_rectangle));
        }
        if(expertUnlocked==1){
            expertButton.setEnabled(true);
            expertButton.setBackground(getResources().getDrawable(R.drawable.green_rounded_rectangle));
        }else{
            expertButton.setEnabled(false);
            expertButton.setBackground(getResources().getDrawable(R.drawable.grey_rounded_rectangle));
        }

    }

    public void easyMode(View view){
        intent.putExtra(DIFFICULTY_LEVEL, "beginner");
        startActivity(intent);
    }

    public void intermediateMode(View view){
        intent.putExtra(DIFFICULTY_LEVEL, "intermediate");
        startActivity(intent);
    }

    public void expertMode(View view){
        intent.putExtra(DIFFICULTY_LEVEL, "expert");
        startActivity(intent);
    }

    public void paths(View view){
        startPaths();
    }

    private void startPaths(){
        ArrayList<Point> firstYellowPoints = new ArrayList<Point>();
        ArrayList<Point> secondYellowPoints = new ArrayList<Point>();
        ArrayList<Point> firstGreenPoints = new ArrayList<Point>();
        ArrayList<Point> secondGreenPoints = new ArrayList<Point>();
        ArrayList<Point> thirdGreenPoints = new ArrayList<Point>();
        ArrayList<Point> thirdYellowPoints = new ArrayList<Point>();

        Point point1 = new Point(0,0);
        Point point2 = new Point(99,89);
        Point point3 = new Point(89,99);
        Point point4 = new Point(10,0);
        Point point5 = new Point(0,10);
        Point point6 = new Point(50,99);
        Point point7 = new Point(100,30);
        Point point8 = new Point(50,0);
        Point point9 = new Point(0,90);

        firstYellowPoints.add(point2);
        firstYellowPoints.add(point5);
        firstYellowPoints.add(point4);
        firstYellowPoints.add(point4);
        firstYellowPoints.add(point5);
        firstYellowPoints.add(point6);
        firstYellowPoints.add(point7);
        firstYellowPoints.add(point8);
        firstYellowPoints.add(point9);

        firstGreenPoints.add(point7);
        firstGreenPoints.add(point8);
        firstGreenPoints.add(point6);
        firstGreenPoints.add(point3);
        firstGreenPoints.add(point8);
        firstGreenPoints.add(point6);
        firstGreenPoints.add(point1);
        firstGreenPoints.add(point9);
        firstGreenPoints.add(point5);
        firstGreenPoints.add(point7);
        firstGreenPoints.add(point2);

        secondYellowPoints.add(point8);
        secondYellowPoints.add(point6);
        secondYellowPoints.add(point1);
        secondYellowPoints.add(point5);
        secondYellowPoints.add(point7);
        secondYellowPoints.add(point8);
        secondYellowPoints.add(point3);
        secondYellowPoints.add(point8);
        secondYellowPoints.add(point6);
        secondYellowPoints.add(point8);

        secondGreenPoints.add(point5);
        secondGreenPoints.add(point4);
        secondGreenPoints.add(point3);
        secondGreenPoints.add(point2);
        secondGreenPoints.add(point1);
        secondGreenPoints.add(point8);
        secondGreenPoints.add(point7);
        secondGreenPoints.add(point6);
        secondGreenPoints.add(point7);
        secondGreenPoints.add(point5);

        thirdGreenPoints.add(point8);
        thirdGreenPoints.add(point9);
        thirdGreenPoints.add(point7);
        thirdGreenPoints.add(point6);
        thirdGreenPoints.add(point5);
        thirdGreenPoints.add(point4);
        thirdGreenPoints.add(point3);
        thirdGreenPoints.add(point2);
        thirdGreenPoints.add(point1);
        thirdGreenPoints.add(point2);
        thirdGreenPoints.add(point3);
        thirdGreenPoints.add(point9);

        thirdYellowPoints.add(point6);
        thirdYellowPoints.add(point4);
        thirdYellowPoints.add(point2);
        thirdYellowPoints.add(point1);
        thirdYellowPoints.add(point3);
        thirdYellowPoints.add(point8);
        thirdYellowPoints.add(point5);
        thirdYellowPoints.add(point2);
        thirdYellowPoints.add(point6);
        thirdYellowPoints.add(point3);
        thirdYellowPoints.add(point9);

        new PathAnimation(firstYellow)
                .setAnchorPoint(PathAnimation.ANCHOR_TOP_LEFT)
                .setPoints(firstYellowPoints)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(12000)
                .animate();
        new PathAnimation(firstGreen)
                .setAnchorPoint(PathAnimation.ANCHOR_BOTTOM_LEFT)
                .setPoints(firstGreenPoints)
                .setDuration(12000)
                .animate();
        new PathAnimation(secondYellow)
                .setAnchorPoint(PathAnimation.ANCHOR_BOTTOM_RIGHT)
                .setPoints(secondYellowPoints)
                .setDuration(12000)
                .animate();
        new PathAnimation(secondGreen)
                .setAnchorPoint(PathAnimation.ANCHOR_TOP_RIGHT)
                .setPoints(secondGreenPoints)
                .setDuration(12000)
                .animate();
        new PathAnimation(thirdYellow)
                .setAnchorPoint(PathAnimation.ANCHOR_CENTER)
                .setPoints(thirdYellowPoints)
                .setDuration(12000)
                .animate();
        new PathAnimation(thirdGreen)
                .setAnchorPoint(PathAnimation.ANCHOR_TOP_LEFT)
                .setPoints(thirdGreenPoints)
                .setDuration(12000)
                .setListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startPaths();
                    }
                })
                .animate();
    }
}
