package com.example.weltschmerz.canvasgame;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {
    public GameView g;
    MediaPlayer player;
    TextView t;
    String s;
    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private WindowManager mWindowManager;
    private Display mDisplay;
    public float mSensorX;
    public float mSensorY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        g = new GameView(this,null);
        setContentView(R.layout.activity_sensor);

        player = MediaPlayer.create(getApplicationContext(), R.raw.eightbit);
        t = (TextView) findViewById(R.id.textView);
        s = "Score: " + g.score;
        t.setText(s);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void music(View view) {
        player.setLooping(true);
        player.start();
    }

    public void musicStop(View view) {
        player.stop();
        player = MediaPlayer.create(getApplicationContext(), R.raw.eightbit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_0:
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                break;
            case Surface.ROTATION_90:
                mSensorX = -event.values[1];
                mSensorY = event.values[0];
                break;
            case Surface.ROTATION_180:
                mSensorX = -event.values[0];
                mSensorY = -event.values[1];
                break;
            case Surface.ROTATION_270:
                mSensorX = event.values[1];
                mSensorY = -event.values[0];
                break;
        }
        if(mSensorX > 2) {
            s = "Score: " + g.score;
            g.w = false;
            g.a = true;
            g.s = false;
            g.d = false;
        }
        else if(mSensorX < -2) {
            s = "Score: " + g.score;
            g.w = false;
            g.a = false;
            g.s = false;
            g.d = true;
        }
        else if(mSensorY < 5.5) {
            s = "Score: " + g.score;
            g.w = true;
            g.a = false;
            g.s = false;
            g.d = false;
        }
        else if(mSensorY < 10) {
            s = "Score: " + g.score;
            g.w = false;
            g.a = false;
            g.s = true;
            g.d = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
