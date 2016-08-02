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
        startPaths();
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

        Point point1 = new Point(0,0);
        Point point2 = new Point(100,100);
        Point point3 = new Point(50,0);
        Point point4 = new Point(0,50);
        Point point5 = new Point(50,100);
        Point point6 = new Point(100,50);
        Point point7 = new Point(100,0);
        Point point8 = new Point(0,10);

        firstYellowPoints.add(point1);
        firstYellowPoints.add(point2);
        firstYellowPoints.add(point3);
        firstYellowPoints.add(point4);
        firstYellowPoints.add(point5);
        firstYellowPoints.add(point6);
        firstYellowPoints.add(point3);
        firstYellowPoints.add(point4);
        firstYellowPoints.add(point2);
        firstYellowPoints.add(point1);

        firstGreenPoints.add(point7);
        firstGreenPoints.add(point8);
        firstGreenPoints.add(point6);
        firstGreenPoints.add(point3);
        firstGreenPoints.add(point8);
        firstGreenPoints.add(point6);
        firstGreenPoints.add(point1);
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

        secondGreenPoints.add(point2);
        secondGreenPoints.add(point1);
        secondGreenPoints.add(point5);
        secondGreenPoints.add(point6);
        secondGreenPoints.add(point3);
        secondGreenPoints.add(point8);
        secondGreenPoints.add(point7);
        secondGreenPoints.add(point3);
        secondGreenPoints.add(point1);
        secondGreenPoints.add(point2);


        new PathAnimation(firstYellow)
                .setAnchorPoint(PathAnimation.ANCHOR_TOP_LEFT)
                .setPoints(firstYellowPoints)
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
    }
}
