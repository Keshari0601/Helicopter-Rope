package com.cell47.helicopterrope;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    //public Gravity gravity;
    public Drag drag;
    public Elastic elastic;
    public int sizeOfRopeList;
    public static int calculationPerFrame=2;
    GameView(MainActivity context) {
        super(context);
        activity=context;

        //gravity, speed of helicopter, drag constant, elasticity constant
        //gravity=new Gravity(Constant.gravityX,Constant.gravityY);
        drag=new Drag();
        elastic =new Elastic(Constant.elasticConstant);

        RopeSectionDX.redPaint = new Paint();
        RopeSectionDX.redPaint.setStyle(Paint.Style.FILL);
        RopeSectionDX.redPaint.setColor(Color.WHITE);
        for(int i=0;i<30;i++){
            RopeSectionList.add(new RopeSectionDX(800+20*i,250,10));
        }
        sizeOfRopeList=RopeSectionList.size();
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
            helicopter.draw(canvas);
            for (int i=0;i<calculationPerFrame;i++) {
                int previousIndex = -1, nextIndex = 1;
                for (RopeSectionDX ropeSection : RopeSectionList) {
                    RopeSectionDX preRope = previousIndex < 0 ? null : RopeSectionList.get(previousIndex);
                    RopeSectionDX nextRope = nextIndex >= sizeOfRopeList ? null : RopeSectionList.get(nextIndex);
                    ropeSection.update(drag, elastic, preRope, nextRope);
                    previousIndex++;
                    nextIndex++;
                }
            }
            for (RopeSectionDX ropeSection:RopeSectionList) {
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
