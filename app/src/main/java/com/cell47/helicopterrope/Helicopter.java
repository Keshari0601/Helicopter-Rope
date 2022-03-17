package com.cell47.helicopterrope;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Helicopter {
    public float sx=600,sy=0,ex=1000,ey=100;
    public Paint redPaint=new Paint();
    public Helicopter(){
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas){
        canvas.drawRect(sx,sy,ex,ey,redPaint);
    }
}
