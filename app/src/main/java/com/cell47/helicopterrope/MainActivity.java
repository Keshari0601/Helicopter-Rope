package com.cell47.helicopterrope;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private View decorView;
    public GameView gameView;
    public float scaleX=1,scaleY=1,translateX=0,translateY=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decorView = getWindow().getDecorView();
        setScales();


        gameView = new GameView(MainActivity.this);
        LinearLayout surface = findViewById(R.id.surface_container);
        surface.addView(gameView);


        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0) {
                fullScreen();
            }
        });

    }

    private void setScales() {
        Display display;
        Point point = new Point();
        display = this.getWindowManager().getDefaultDisplay();
        display.getSize(point);
        float navigationBarHeight = getNavigationBarHeight();
        float width = point.x+navigationBarHeight ;
        float height = point.y;
        float ratio= width/height;
        if(ratio<16f/9f){
            scaleY=width/1600;
            translateY=(1600/ratio-900)/2;
            translateX=0;
        }
        else{
            scaleY=height/900;
            translateY=0;
            translateX=(900*ratio-1600)/2;
        }
        scaleX=scaleY;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if(gameView.gameLoopThread==null){
                gameView.resume();
            }
            fullScreen();
        }
        else {
            gameView.pause();
        }
    }
    private void fullScreen()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private int getNavigationBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableWidth = metrics.widthPixels;
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realWidth = metrics.widthPixels;
        if (realWidth > usableWidth)
            return realWidth - usableWidth;
        else
            return 0;
    }

}