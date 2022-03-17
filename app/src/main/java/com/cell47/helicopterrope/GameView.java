package com.cell47.helicopterrope;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView {
    //main objects
    public MainActivity activity;
    GameLoopThread gameLoopThread;
    SurfaceHolder holder;

    //local objects
    public boolean isCompleted=false;


    public List<RopeSectionDX> RopeSectionList=new ArrayList<>();
    public Helicopter helicopter=new Helicopter();

    GameView(MainActivity context) {
        super(context);
        activity=context;



        RopeSectionList.add(new RopeSectionDX(450,800,100,100));

        //Holder Set
        holder = getHolder();
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                try {
                    pause();
                }
                catch (Exception ignored){
                }
            }
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                resume();
            }
            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.scale(activity.scaleX,activity.scaleY);
            canvas.translate(activity.translateX, activity.translateY);
            for (RopeSectionDX ropeSection:RopeSectionList){
                ropeSection.update();
                ropeSection.draw(canvas);
            }
        }
        catch (Exception ignored){

        }

    }


    public void pause() {
        if(gameLoopThread!=null) {
            gameLoopThread.setRunning(false);
            try {
                gameLoopThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameLoopThread = null;
        }
    }

    public void resume() {
        if(!isCompleted) {
            gameLoopThread = new GameLoopThread(this);
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }
}
