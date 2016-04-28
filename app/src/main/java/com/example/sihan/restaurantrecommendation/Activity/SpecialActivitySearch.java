package com.example.sihan.restaurantrecommendation.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

public class SpecialActivitySearch extends TabActivity {
    private TabHost tabhost;
    private RadioGroup special_radiogroup;
    private RadioButton Mannual;
    private RadioButton Personal;
    private RadioButton More;


    public SpecialActivitySearch() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sihan.restaurantrecommendation.R.layout.activity_special_activity_search);
        special_radiogroup = (RadioGroup) findViewById(com.example.sihan.restaurantrecommendation.R.id.special_radiogroup);

        Mannual = (RadioButton) findViewById(com.example.sihan.restaurantrecommendation.R.id.MannualSearch);
        Personal = (RadioButton) findViewById(com.example.sihan.restaurantrecommendation.R.id.PersonalSearch);
        More = (RadioButton) findViewById(com.example.sihan.restaurantrecommendation.R.id.MoreFunction);
        this.tabhost = this.getTabHost();
        tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0").setContent(new Intent(this, personalSearch.class)));
        tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1").setContent(new Intent(this, new_man.class)));
        tabhost.addTab(tabhost.newTabSpec("tag3").setIndicator("2").setContent(new Intent(this, AdditionalFunction.class)));
        checkListener checkradio = new checkListener();
        special_radiogroup.setOnCheckedChangeListener(checkradio);
    }


    //监听类
    public class checkListener implements RadioGroup.OnCheckedChangeListener {
        public checkListener() {
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub
            //setCurrentTab 通过标签索引设置当前显示的内容
            //setCurrentTabByTag 通过标签名设置当前显示的内容
            switch (checkedId) {
                case com.example.sihan.restaurantrecommendation.R.id.PersonalSearch:
                    SpecialActivitySearch.this.tabhost.setCurrentTab(0);
                    startActivity(new Intent(SpecialActivitySearch.this, User_MainActivity.class));
                    //或
                    //tabhost.setCurrentTabByTag("tag1");
                    break;
                case com.example.sihan.restaurantrecommendation.R.id.MannualSearch:
                    SpecialActivitySearch.this.tabhost.setCurrentTab(1);
                    break;
                case com.example.sihan.restaurantrecommendation.R.id.MoreFunction:
                    SpecialActivitySearch.this.tabhost.setCurrentTab(2);
                    break;
            }


        }
    }


}


