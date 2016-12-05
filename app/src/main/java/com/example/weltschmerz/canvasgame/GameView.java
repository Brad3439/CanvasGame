package com.example.weltschmerz.canvasgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;
import static android.graphics.Color.GREEN;

/**
 * Created by Weltschmerz on 11/3/16.
 */
class GameView extends SurfaceView implements SurfaceHolder.Callback {
    DrawingThread thread = null;
    SurfaceHolder ourHolder;
    Paint paint;
    Bitmap bitmap;
    Bitmap bamboo;
    boolean playing;
    static int score = 0;
    int width,height;
    float playerPosX,playerPosY;

    //direction control variables
    static boolean s = false;
    static boolean w = false;
    static boolean a = false;
    static boolean d = false;

    float playerSpeed = 10;

    Random x = new Random();
    Random y = new Random();
    int targetPosX, targetPosY;

    public GameView(Context context,AttributeSet attrs) {
        super(context,attrs);
        ourHolder = getHolder();
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pandafront);
        bamboo = BitmapFactory.decodeResource(this.getResources(), R.drawable.pandaback);
        playing = true;

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        targetPosX = x.nextInt(500 - 5) + 5;
        targetPosY = y.nextInt(500 - 5) + 5;

    }

    public void update() {
        //allows player to move in four directions and draws the appropriate sprite
        if(down() == true) {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pandafront);
            playerPosY += playerSpeed;
        }
        else if(up() == true) {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pandaback);
            playerPosY -= playerSpeed;
        }
        else if(left() == true) {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pandaleft);
            playerPosX -= playerSpeed;
        }
        else if(right() == true) {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pandaright);
            playerPosX += playerSpeed;
        }
        //player collision detection
        if(playerPosX >= width-50 && right() == true) {
            playerPosX = width-50;
        }
        else if(playerPosY >= height-50 && down() == true) {
            playerPosY = height-50;
        }
        else if(playerPosY <= 0 && up() == true) {
            playerPosY = 0;
        }
        else if(playerPosX <= 0 && left() == true) {
            playerPosX = 0;
        }
        //puts a hit field around target and increments the score variable when entered
        if(playerPosX >= targetPosX - 50 && playerPosX <= targetPosX + 50 && playerPosY >= targetPosY - 25 && playerPosY <= targetPosY + 25) {
            score++;
            targetPosX = x.nextInt(width);
            targetPosY = y.nextInt(height);
        }
    }

    public boolean down() {
        if(s) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean up() {
        if(w) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean left() {
        if(a) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean right() {
        if(d) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
        boolean waitingForDeath = true;
        while(waitingForDeath) {
            try {
                thread.join();
                waitingForDeath = false;
            }
            catch (Exception e) {
                Log.v("Thread Exception", "Waiting on drawing thread to die: " + e.getMessage());
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new DrawingThread(holder, getContext(), this);
        thread.setRunning(true);
        thread.start();
        Canvas t = holder.lockCanvas();
        width = t.getWidth();
        height = t.getHeight();
        holder.unlockCanvasAndPost(t);
    }

    public void customDraw(Canvas canvas) {
        update();
        canvas.drawColor(GREEN);
        paint.setColor(GREEN);
        canvas.drawBitmap(bitmap, playerPosX, playerPosY, paint);
        canvas.drawBitmap(bamboo, targetPosX, targetPosY, paint);

    }
}

class DrawingThread extends Thread {
    private boolean running;
    private Canvas canvas;
    private SurfaceHolder holder;
    protected Context context;
    private GameView view;

    private int FRAME_RATE = 30;
    private double delay = 1.0 / FRAME_RATE * 1000;
    private long time;

    public DrawingThread(SurfaceHolder holder, Context c, GameView v) {
        this.holder = holder;
        context = c;
        view = v;
        time = System.currentTimeMillis();
    }

    void setRunning(boolean r) {
        running = r;
    }

    @Override
    public void run() {
        super.run();
        while(running) {
            if(System.currentTimeMillis() - time > delay) {
                time = System.currentTimeMillis();
                canvas = holder.lockCanvas();
                if(canvas != null) {
                    view.customDraw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}




