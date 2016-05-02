package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private boolean active = true;
    private int splashTime = 5000;    // time to display the splash screen in ms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sihan.restaurantrecommendation.R.layout.activity_main);

        findViewById(com.example.sihan.restaurantrecommendation.R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Restaurant.class));
            }
        });
        new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                } finally {
                    finish();

                    // Run next activity
                     Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    //stop();
                }
            }
        }.start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.sihan.restaurantrecommendation.R.menu.menu_main, menu);
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            active = false;
        }
        return true;
    }
}
