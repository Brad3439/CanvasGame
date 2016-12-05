package com.example.weltschmerz.canvasgame;

import android.app.Activity;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

public class KeyboardActivity extends Activity {
    public GameView g;
    MediaPlayer player;
    TextView t;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        g = new GameView(this,null);
        setContentView(R.layout.activity_keyboard);

        player = MediaPlayer.create(getApplicationContext(), R.raw.eightbit);
        t = (TextView) findViewById(R.id.textView);
        s = "Score: " + g.score;
        t.setText(s);
    }

    public void music(View view) {
        player.setLooping(true);
        player.start();
    }
    public void musicStop(View view) {
        player.stop();
        player = MediaPlayer.create(getApplicationContext(), R.raw.eightbit);
    }

    public void moveDown(View view) {
        s = "Score: " + g.score;
        t.setText(s);
        g.s = true;
        g.w = false;
        g.a = false;
        g.d = false;
    }
    public void moveUp(View view) {
        s = "Score: " + g.score;
        t.setText(s);
        g.w = true;
        g.s = false;
        g.a = false;
        g.d = false;
    }
    public void moveRight(View view) {
        s = "Score: " + g.score;
        t.setText(s);
        g.d = true;
        g.s = false;
        g.w = false;
        g.a = false;
    }
    public void moveLeft(View view) {
        s = "Score: " + g.score;
        t.setText(s);
        g.a = true;
        g.s = false;
        g.w = false;
        g.d = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
