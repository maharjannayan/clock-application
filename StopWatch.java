package com.nayan.alaram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class StopWatch extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView mtextViewCountDown;
    private Button mButtonStartPause;
    private Button mbuttonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width *.7), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


        mtextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mbuttonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        mbuttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();

            }
        });
        updateCountDownText();
    }

    private void startTimer(){
        //1000 is the 10 minutes so, you can Change the Value!!
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility((View.INVISIBLE));
                mbuttonReset.setVisibility(View.VISIBLE);

            }
        }.start();
        mTimerRunning=true;
        mButtonStartPause.setText("pause");
        mbuttonReset.setVisibility(View.INVISIBLE);

    }
    private void pauseTimer(){

        mCountDownTimer.cancel();
        mTimerRunning=false;
        mButtonStartPause.setText("Start");
        mbuttonReset.setVisibility(View.VISIBLE);

    }
    private void  resetTimer(){

        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mbuttonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mtextViewCountDown.setText(timeLeftFormatted);
    }

}