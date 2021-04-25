package com.example.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Speedometer extends GPSActivity {
    private ProgressBar pbSpeed;
    private TextView tvSpeed;
    private TextView tvLog;
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            float f = Speedometer.this.getSpeed();
            //TextView textView = Speedometer.this.tvSpeed;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Math.round(f));
            stringBuilder.append(" km/h");
            Speedometer.this.tvSpeed.setText(stringBuilder.toString());
            Speedometer.this.tvLog.setText( Speedometer.this.tvLog.getText() + "; " + stringBuilder.toString());
            Speedometer.this.pbSpeed.setProgress(Math.round(f));
            //Speedometer.this.handler.postDelayed(this, 50L);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);
        //ui elements init
        this.tvSpeed = (TextView)findViewById(R.id.tvSpeed);
        this.tvLog = (TextView)findViewById(R.id.tvLog);
        this.pbSpeed = (ProgressBar)findViewById(R.id.pbSpeed);
        this.handler.postDelayed(this.runnable, 50L);
    }
}