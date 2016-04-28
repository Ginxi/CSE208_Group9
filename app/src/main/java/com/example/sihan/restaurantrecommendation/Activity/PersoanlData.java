package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PersoanlData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sihan.restaurantrecommendation.R.layout.activity_persoanl_data);
        findViewById(com.example.sihan.restaurantrecommendation.R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersoanlData.this, SpecialActivitySearch.class));
            }
        });
    }
}
