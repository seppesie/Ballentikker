package com.prog5.miras.ballentikker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
private ImageView assetBall;
private TextView txtInfo,txtCount;
private ConstraintLayout layout;
private float[] lastTouchDownXY = new float[2];
private float[] lastTouchUpXY = new float[2];
private Float BallX,BallY,distance;
private double VelocityX,VelocityY,AccelY=0;
private boolean isEnd,isStart=false;
private Timer timer = new Timer();
private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assetBall = findViewById(R.id.assetBall);
        View myView = findViewById(R.id.my_view);
        layout = findViewById(R.id.my_view);
        assetBall.getLayoutParams().height= assetBall.getLayoutParams().width;
        txtInfo = findViewById(R.id.txtInfo);
        txtCount = findViewById(R.id.txtCount);
        myView.setOnTouchListener(touchListener);
        myView.setOnClickListener(clickListener);
        isEnd=false;
        txtCount.setText(Integer.toString(count));
reset();
        if(isEnd==true){
            stopTimer();
        }
    }


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
                BallX = assetBall.getX()+(assetBall.getWidth()/2);
                BallY = assetBall.getY()+(assetBall.getHeight()/2)-(float)(VelocityY);
                distance = (float) (Math.sqrt(((BallX-x)*(BallX-x))+((BallY-y)*(BallY-y))));
                 //V(X dist * X dist) + (Y dist * Y dist)
                Log.i("TAG", "Ball x= "+BallX+" y="+BallY);
                Log.i("TAG", "distance = "+distance);
                if (distance-50<(assetBall.getWidth()/2)){
                    Log.i("TAG","within range");
                    Double distX = (BallX-x)/(assetBall.getWidth()*0.95);
                    Double distY = (BallY-y)/(assetBall.getHeight()*0.95);
                    if(distY<0) {
                      VelocityY = ((distY*100)-8) ;
                      VelocityX = (distX*40);
                      count +=1;
                      txtCount.setText(Integer.toString(count));
                    }
                }else {
                    Log.i("TAG", "out of range");}
                startTimer();

            }
        };
    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();

    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    private void startTimer(){
        if(isStart==false){
            isStart=true;
        mTimer1 = new Timer();
        Log.i("TAG","timer started");

        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
//todo
                        txtInfo.setText("X: "+assetBall.getX()+"/Y: "+assetBall.getY()+"/Vel: "+VelocityY);

                            assetBall.setY(assetBall.getY() + (float) VelocityY);
                            assetBall.setX(assetBall.getX() + (float) VelocityX);

                         VelocityY -= AccelY;
                         AccelY = -0.24;
VelocityX = VelocityX*0.995;
checkbounce();

                        if(assetBall.getY()>2000) {
                            stopTimer();
                            reset();
                        }





                    }
                });
            }
        };

        mTimer1.schedule(mTt1, 1, 12);
    }}


    public void checkbounce(){
        if(assetBall.getX()<=0){
            VelocityX = -VelocityX;
        }
        if((assetBall.getX()+assetBall.getWidth())>layout.getWidth()){
            VelocityX = -VelocityX;
        }
    }


public void reset(){
    isStart=false;
    assetBall.setY(200);
    count =0;
    assetBall.setX((layout.getWidth()/2)-(assetBall.getWidth()/2));
}
}