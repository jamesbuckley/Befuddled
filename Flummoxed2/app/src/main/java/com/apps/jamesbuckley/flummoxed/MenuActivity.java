package com.apps.jamesbuckley.flummoxed;

import android.content.Intent;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.easyandroidanimations.library.PathAnimation;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    ImageView firstGreen, secondGreen, firstYellow, secondYellow;
    private Intent intent;
    public static String DIFFICULTY_LEVEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firstGreen = (ImageView) findViewById(R.id.firstGreenBall);
        firstYellow = (ImageView) findViewById(R.id.firstYellowBall);
        secondGreen = (ImageView) findViewById(R.id.secondGreenBall);
        secondYellow = (ImageView) findViewById(R.id.secondYellowBall);

        intent = new Intent(this, MainActivity.class);

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

    private void startPaths(){
        ArrayList<Point> firstYellowPoints = new ArrayList<Point>();
        ArrayList<Point> secondYellowPoints = new ArrayList<Point>();
        ArrayList<Point> firstGreenPoints = new ArrayList<Point>();
        ArrayList<Point> secondGreenPoints = new ArrayList<Point>();

        Point point1 = new Point(0,0);
        Point point2 = new Point(10,10);
        Point point3 = new Point(40,40);
        Point point4 = new Point(50,50);
        Point point5 = new Point(60,60);
        Point point6 = new Point(70,70);
        Point point7 = new Point(80,80);
        Point point8 = new Point(90,90);
        Point point9 = new Point(0,0);
        Point point10 = new Point(0,0);
        Point point11 = new Point(0,0);
        Point point12 = new Point(100,100);

        Point point13 = new Point(0,0);
        Point point14 = new Point(0,0);
        Point point15 = new Point(0,0);
        Point point16 = new Point(0,0);
        Point point17 = new Point(0,0);
        Point point18 = new Point(0,0);
        Point point19 = new Point(0,0);
        Point point20 = new Point(0,0);
        Point point21 = new Point(0,0);
        Point point22 = new Point(0,0);
        Point point23 = new Point(0,0);
        Point point24 = new Point(0,0);

        firstYellowPoints.add(point1);
        firstYellowPoints.add(point2);
        firstYellowPoints.add(point3);
        firstYellowPoints.add(point4);
        firstYellowPoints.add(point5);
        firstYellowPoints.add(point6);
        firstYellowPoints.add(point7);
        firstYellowPoints.add(point8);
        new PathAnimation(firstYellow)
                .setInterpolator(new AccelerateInterpolator())
                .setPoints(firstYellowPoints)
                .setDuration(50000)
                .animate();
    }
}
