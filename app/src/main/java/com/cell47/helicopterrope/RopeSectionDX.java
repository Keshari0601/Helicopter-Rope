package com.cell47.helicopterrope;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RopeSectionDX {
    public double centerX, centerY,radius;
    public double velocityX=0, velocityY=0;
    public double mass;
    public double helicopterSpeed=1;
    public double aFactor=1;

    Paint redPaint;

    public RopeSectionDX(double centerX, double centerY, double radius, double mass){
        this.centerX =centerX;
        this.centerY =centerY;
        this.radius=radius;
        this.mass=mass;
        redPaint = new Paint();
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setColor(Color.RED);
    }
    public RopeSectionDX(double centerX, double centerY, double radius, double mass,double helicopterSpeed){
        this.centerX =centerX;
        this.centerY =centerY;
        this.radius=radius;
        this.mass=mass;
        redPaint = new Paint();
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setColor(Color.RED);
        this.helicopterSpeed=helicopterSpeed;
        aFactor=GameView.calculationPerFrame*GameView.calculationPerFrame;
    }

    public void draw(Canvas canvas){
        canvas.drawCircle((float) centerX,(float) centerY,(float) radius,redPaint);
    }

    public void update(Drag drag, Gravity gravity, Elastic elastic, RopeSectionDX preRope, RopeSectionDX nextRope) {
        if (preRope == null) {
            return;
        }
        double ax=0,ay=0;
        ax+= gravity.GX;
        ay+= gravity.GY;

        AccelerationVector previousAcceleration = elastic.getAcceleration(preRope,this);
        ax+=previousAcceleration.ax;
        ay+=previousAcceleration.ay;

        if(nextRope!=null) {
            AccelerationVector nextAcceleration = elastic.getAcceleration(nextRope,this);
            ax+=nextAcceleration.ax;
            ay+=nextAcceleration.ay;
        }
        AccelerationVector dragAcceleration= drag.getAcceleration(this);
        ax+= dragAcceleration.ax;
        ay+=dragAcceleration.ay;
        velocityY+=ay;
        velocityX+=ax;
        centerX+=velocityX/aFactor;
        centerY+=velocityY/aFactor;
    }
}
