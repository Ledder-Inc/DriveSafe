package com.example.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private Button btSpeedometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ui elements init
        this.etUsername = (EditText) findViewById(R.id.etUsername);
        this.btSpeedometer = (Button) findViewById(R.id.btSpeedometer);
        this.btSpeedometer.setOnClickListener(this);

        //get username from sharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DriveSafePref", 0);
        this.etUsername.setText(sharedPreferences.getString("username", ""));
        //if (this.tvUsername.getText().length() >= 5)
        //    getWindow().setSoftInputMode(3);

        //set default username
        if (this.etUsername.getText().length() == 0){
            this.etUsername.setText("srv_admin");
            saveUsername();
        }
    }

    private String safeStr(String paramString) {
        return paramString.replaceAll("^[\\p{L}0-9]*$", "");
    }

    private void saveUsername() {
        if (this.etUsername.getText().toString().length() >= 5)
            getSharedPreferences("DriveSafePref", 0).edit().putString("username", this.etUsername.getText().toString()).commit();
    }

    @Override
    public void onClick(View v) {
        if(v == this.btSpeedometer){
            if (this.etUsername.getText().toString().length() < 5) {
                this.etUsername.setError("Benutzernamen von mindestens 5 Zeichen festlegen!");
            } else {
                saveUsername();
                Log.d("Debug", "open Speedometer.class");
                Intent intent = new Intent((Context) this, Speedometer.class);
                intent.putExtra("username", this.etUsername.getText().toString());
                startActivity(intent);
            }
        }
    }
}