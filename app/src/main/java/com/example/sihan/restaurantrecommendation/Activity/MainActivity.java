package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;
import com.example.sihan.restaurantrecommendation.R;



public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sihan.restaurantrecommendation.R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
                overridePendingTransition(R.anim.alpha_in,
                        R.anim.alpha_out);
            }
        }, 800);

        AVOSCloud.initialize(this, "MQAdXbdNgXPI1YzlpHuAYI9O-gzGzoHsz", "Sz0KIPvyscUKScbCEGgHRvj7");


    }

}
