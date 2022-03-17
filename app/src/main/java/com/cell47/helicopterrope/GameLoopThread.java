package com.cell47.helicopterrope;

import android.graphics.Canvas;
import android.view.SurfaceView;

public class GameLoopThread extends Thread {
    private static final long FPS = 60;
    private final SurfaceView view;
    boolean running = false;

    public GameLoopThread(SurfaceView view) {
        this.view = view;
    }
    public void setRunning(boolean run) {
        running = run;
    }
    @Override
    public void run() {


        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    try {
                        view.draw(c);
                    }
                    catch (Exception ignored){

                    }
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);

            } catch (Exception ignored) {}
        }

    }

}

