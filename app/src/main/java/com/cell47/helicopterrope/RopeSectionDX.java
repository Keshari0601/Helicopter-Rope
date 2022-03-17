package com.cell47.helicopterrope;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RopeSectionDX {
    public float centerX, centerY,radius;
    public float velocityX=0, velocityY=0;
    public float mass;

    Paint redPaint;

    public RopeSectionDX(float centerX, float centerY, float radius, float mass){
        this.centerX =centerX;
        this.centerY =centerY;
        this.radius=radius;
        this.mass=mass;
        redPaint = new Paint();
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setColor(Color.RED);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(centerX,centerY,radius,redPaint);
        canvas.drawCircle(1000,500,50,redPaint);
        canvas.drawRect(0,0,1000,500,redPaint);
    }

    public void updateVelocity(Drag drag, Gravity gravity, Elastic elastic) {
       /* float disX=centerX-spaceShip.centerX;
        float disY=centerY-spaceShip.centerY;
        float distance=(float) Math.sqrt(disX*disX+disY*disY);
        if(checkCollision(distance)){
            return;
        }
        float distance3=distance*distance*distance;
        spaceShip.speedX+=Gm*disX/distance3;
        spaceShip.speedY+=Gm*disY/distance3;
    */
    }

    public void update() {
    }
}
