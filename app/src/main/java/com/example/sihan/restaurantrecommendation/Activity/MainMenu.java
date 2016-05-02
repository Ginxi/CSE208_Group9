package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.sihan.restaurantrecommendation.R;
import com.example.sihan.restaurantrecommendation.com.example.sihan.restaurantrecommendation.view.SlidingMenu;

public class MainMenu extends AppCompatActivity {
    private SlidingMenu LeftSlideMenu;
    @Override
    protected void   onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        LeftSlideMenu=(SlidingMenu)findViewById(R.id.slideMenu);
        findViewById(R.id.mannual_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, new_man.class));

            }
        });


        findViewById(R.id.personal_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, User_MainActivity.class));

            }
        });

    }
    public void toggleMenu(View view){
        LeftSlideMenu.toggle();
    }
}
