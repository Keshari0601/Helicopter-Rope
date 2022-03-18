package com.cell47.helicopterrope;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View decorView;
    public GameView gameView;
    public float scaleX=1,scaleY=1,translateX=0,translateY=0;

    private SeekBar mass,gx,gy,drag,helicopterSpeed;
    private TextView massText,gxText,gyText,dragText,helicopterSpeedText;
    private float minMass=50,maxMass=1000;
    private float minGX=-20,maxGX=20;
    private float minGY=0,maxGY=50;
    private float minDRAG=0,maxDRAG=50;
    private float minSPEED=0,maxSPEED=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decorView = getWindow().getDecorView();
        setScales();
        mass=findViewById(R.id.mass);
        massText=findViewById(R.id.massText);
        gx=findViewById(R.id.gravityX);
        gxText=findViewById(R.id.gravityXText);
        gy=findViewById(R.id.gravityY);
        gyText=findViewById(R.id.gravityYText);
        drag=findViewById(R.id.dragConstant);
        dragText=findViewById(R.id.dragConstantText);
        helicopterSpeed=findViewById(R.id.helicopterSpeed);
        helicopterSpeedText=findViewById(R.id.helicopterSpeedText);

        Constant.mass=minMass+((double)0.5)*(maxMass-minMass);
        massText.setText(String.format("%.2f", Constant.mass));

        Constant.gravityX=minGX+((double)0.5)*(maxGX-minGX);
        gxText.setText(String.format("%.2f", Constant.gravityX));

        Constant.gravityY=minGY+((double)0.5)*(maxGY-minGY);
        gyText.setText(String.format("%.2f", Constant.gravityY));

        Constant.dragConstant=minDRAG+((double)0.5)*(maxDRAG-minDRAG);
        dragText.setText(String.format("%.2f", Constant.dragConstant));

        Constant.helicopterSpeed=minSPEED+((double)0.5)*(maxSPEED-minSPEED);
        helicopterSpeedText.setText(String.format("%.2f", Constant.helicopterSpeed));


        helicopterSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Constant.helicopterSpeed=minSPEED+((double)progress/100)*(maxSPEED-minSPEED);
                helicopterSpeedText.setText(String.format("%.2f", Constant.helicopterSpeed));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Constant.helicopterSpeed=minSPEED+((double)seekBar.getProgress()/100)*(maxSPEED-minSPEED);
                helicopterSpeedText.setText(String.format("%.2f", Constant.helicopterSpeed));    }
        });


        drag.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Constant.dragConstant=minDRAG+((double)progress/100)*(maxDRAG-minDRAG);
                dragText.setText(String.format("%.2f", Constant.dragConstant));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Constant.dragConstant=minDRAG+((double)seekBar.getProgress()/100)*(maxDRAG-minDRAG);
                dragText.setText(String.format("%.2f", Constant.dragConstant));
            }
        });


        gy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Constant.gravityY=minGY+((double)progress/100)*(maxGY-minGY);
                gyText.setText(String.format("%.2f", Constant.gravityY));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Constant.gravityY=minGY+((double)seekBar.getProgress()/100)*(maxGY-minGY);
                gyText.setText(String.format("%.2f", Constant.gravityY));
            }
        });

        gx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Constant.gravityX=minGX+((double)progress/100)*(maxGX-minGX);
                gxText.setText(String.format("%.2f", Constant.gravityX));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Constant.gravityX=minGX+((double)seekBar.getProgress()/100)*(maxGX-minGX);
                gxText.setText(String.format("%.2f", Constant.gravityX));
            }
        });

        mass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Constant.mass=minMass+((double)progress/100)*(maxMass-minMass);
                massText.setText(String.format("%.2f", Constant.mass));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Constant.mass=minMass+((double)seekBar.getProgress()/100)*(maxMass-minMass);
                massText.setText(String.format("%.2f", Constant.mass));
            }
        });


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
        findViewById(R.id.helicopter).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height*3/9)));
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

    public void reset(View view) {
        LinearLayout surface = findViewById(R.id.surface_container);
        gameView = new GameView(MainActivity.this);
        surface.removeAllViews();
        surface.addView(gameView);
    }
}