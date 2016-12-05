package com.example.weltschmerz.canvasgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    protected void sensor(View v) {
        intent = new Intent(this,SensorActivity.class);
        startActivity(intent);
    }

    protected void gameboy(View v) {
        intent = new Intent(this,KeyboardActivity.class);
        startActivity(intent);
    }
}
