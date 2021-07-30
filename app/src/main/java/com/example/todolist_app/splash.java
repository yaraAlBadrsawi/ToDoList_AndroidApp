package com.example.todolist_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class splash extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.imageView=findViewById(R.id.imageView2);

        AnimationDrawable animationDrawable= (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        };
        thread.start();

    }
}