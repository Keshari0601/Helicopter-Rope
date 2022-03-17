package com.cell47.helicopterrope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public String mass;
    public String gravityX;
    public String gravityY;
    public String dragConstant;
    public String elasticConstant;
    public String helicopterSpeed;

    public void start(View view) {
        mass =((EditText)findViewById(R.id.mass)).getText().toString();
        gravityX =((EditText)findViewById(R.id.gx)).getText().toString();
        gravityY =((EditText)findViewById(R.id.gy)).getText().toString();
        dragConstant =((EditText)findViewById(R.id.dragConstant)).getText().toString();
        elasticConstant =((EditText)findViewById(R.id.elasticConstant)).getText().toString();
        helicopterSpeed =((EditText)findViewById(R.id.helicopterSpeed)).getText().toString();
        Constant.mass=Double.parseDouble(mass);
        Constant.gravityX=Double.parseDouble(gravityX);
        Constant.gravityY=Double.parseDouble(gravityY);
        Constant.dragConstant=Double.parseDouble(dragConstant);
        Constant.elasticConstant=Double.parseDouble(elasticConstant);
        Constant.helicopterSpeed=Double.parseDouble(helicopterSpeed);
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }
}