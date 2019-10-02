package com.prog5.miras.ballentikker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
private ImageView assetBall;
private float[] lastTouchDownXY = new float[2];
    private float[] lastTouchUpXY = new float[2];
private Float BallX,BallY,distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assetBall = findViewById(R.id.assetBall);
        View myView = findViewById(R.id.my_view);
        myView.setOnTouchListener(touchListener);
        myView.setOnClickListener(clickListener);}

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // save the X,Y coordinates
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    lastTouchDownXY[0] = event.getX();
                    lastTouchDownXY[1] = event.getY();
                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    lastTouchUpXY[0] = event.getX();
                    lastTouchUpXY[1] = event.getY();
                }

                // let the touch event pass on to whoever needs it
                return false;
            }
        };

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // retrieve the stored coordinates
                float x = lastTouchDownXY[0];
                float y = lastTouchDownXY[1];

                // use the coordinates for whatever
                Log.i("TAG", "onLongClick: x = " + x + ", y = " + y);
                BallX = assetBall.getX()+300;
                BallY = assetBall.getY()+300;
                distance = (float) (Math.sqrt(((BallX-x)*(BallX-x))+((BallY-y)*(BallY-y))));
                 //V(X dist * X dist) + (Y dist * Y dist)
                Log.i("TAG", "Ball x= "+BallX+" y="+BallY);
                Log.i("TAG", "distance = "+distance);
                if (distance<200){
                    Log.i("TAG","within range");
                    Float distX = BallX-x;
                    Float distY = BallY-y;

                }else {
                    Log.i("TAG", "out of range");


                }
            }
        };
}
