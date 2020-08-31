package com.nayan.alaram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker timepicker;
    Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timepicker = (TimePicker) findViewById(R.id.timePicker);
        findViewById(R.id.buttonSetAlaram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance();
                if (Build.VERSION.SDK_INT >= 23) {

                    calender.set(
                            calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH),
                            calender.get(Calendar.DAY_OF_MONTH),
                            timepicker.getHour(),
                            timepicker.getMinute(),
                             0
                    );
                }else{
                  
                    calender.set(
                            calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH),
                            calender.get(Calendar.DAY_OF_MONTH),
                            timepicker.getCurrentHour(),
                            timepicker.getCurrentMinute(), 0
                    );

                }

                setAlarm(calender.getTimeInMillis());
            }
        });

//pop up button for timer
        btn = (Button) findViewById(R.id.stopPop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopWindow();
            }
        });
    }

    private void openPopWindow() {
        Intent popUpWindow = new Intent(MainActivity.this, StopWatch.class);
        startActivity(popUpWindow);
    }

    private void setAlarm(long timeInMillis){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, NewAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alaram is set", Toast.LENGTH_SHORT).show();

    }
}