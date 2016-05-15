package com.example.sihan.restaurantrecommendation.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.sihan.restaurantrecommendation.Function.FirstRatings;
import com.example.sihan.restaurantrecommendation.R;
import com.example.sihan.restaurantrecommendation.com.example.sihan.restaurantrecommendation.view.SlidingMenu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sunger.net.org.dropmenu.adapter.ShopAdapter;
import sunger.net.org.dropmenu.new_drop;

public class MainMenu extends AppCompatActivity {
    private SlidingMenu LeftSlideMenu;
    private ArrayList<com.example.sihan.restaurantrecommendation.Function.Restaurant> restaurantList;
 //   private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
//    private String[] mTitles = {"Score Ranking", "Price Ranking", "Distance Ranking"};
//    private int[] mIconUnselectIds = {
//            R.mipmap.tab_floor_unselected, R.mipmap.tab_category_unseleted,
//            R.mipmap.tab_sort_unseleted};
//    private int[] mIconSelectIds = {
//            R.mipmap.tab_floor_selected, R.mipmap.tab_category_seleted,
//            R.mipmap.tab_floor_selected};
//    private CommonTabLayout tabs;
//    private DropDownLayout dropDownLayout;
//    private MenuLayout menuLayout;
    @Override
    protected void   onCreate(Bundle savedInstanceState) {
        FirstRatings fr = new FirstRatings();
        InputStream isFile = getResources().openRawResource(R.raw.cse208data1short);
        restaurantList = fr.wantedRestaurants("", 70, "", "", 1000, isFile);
        System.out.println(restaurantList.get(0).toString());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        LeftSlideMenu=(SlidingMenu)findViewById(R.id.slideMenu);
        findViewById(R.id.mannual_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, new_man.class));

            }
        }
        );


        findViewById(R.id.personal_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, User_MainActivity.class));

            }
        });
        SysApplication.getInstance().addActivity(this);

        findViewById(R.id.additional_function).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().exit();
            }
        });
//        String[] res = new String[restaurantList.size()];
//        for(int i = 0; i < restaurantList.size(); i++) {
//            res[i] = restaurantList.get(i).toString();
//        }
        ShopAdapter shopAdapter = new ShopAdapter(restaurantList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        new_drop.DividerLine dividerLine = new new_drop.DividerLine(new_drop.DividerLine.VERTICAL);
        dividerLine.setSize(10);
        dividerLine.setColor(0xFFDDDDDD);
        recyclerView.addItemDecoration(dividerLine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.setOnItemClickListener(new ShopAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent(MainMenu.this, com.example.sihan.restaurantrecommendation.Activity.Restaurant.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, String data) {

            }
        });
//        menuLayout = (MenuLayout) findViewById(R.id.menuLayout);
//        dropDownLayout = (DropDownLayout) findViewById(R.id.dropdown);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new FragmentScore());
//        fragments.add(new FragmentPrice());
//        fragments.add(new FragmentRange());
//        menuLayout.setFragmentManager(getSupportFragmentManager());
//        menuLayout.bindFragments(fragments);
//        updateTabData();
//        tabs = (CommonTabLayout) findViewById(R.id.tabs);
//        tabs.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                dropDownLayout.showMenuAt(position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//                if (menuLayout.isShow()) {
//                    dropDownLayout.closeMenu();
//                } else {
//                    dropDownLayout.showMenuAt(position);
//                }
//
//            }
//        });
//    }
//    private void updateTabData() {
//        mTabEntities.clear();
//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
//        }
//        tabs.setTabData(mTabEntities);
        createUserScoreList();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
            showTips();
            return true;
        }
        return true;
    }

    private void showTips() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Reminder")
                .setMessage("Are you sure you want leave?")
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                }).setNegativeButton("Cancel",

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }
    public void toggleMenu(View view){
        LeftSlideMenu.toggle();
    }
    private void createUserScoreList() {
    Toast.makeText(getApplicationContext(), "User"+Login.loginAccount, Toast.LENGTH_LONG).show();
        final AVQuery<AVObject> queryUser = new AVQuery<>("User"+Login.loginAccount);
        queryUser.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e != null) {
                    final AVObject createUserScore = new AVObject("User" + Login.loginAccount);
                    createUserScore.setFetchWhenSave(true);
                    createUserScore.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Successful creating score list!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed creating score list!", Toast.LENGTH_SHORT).show();
                                // 失败的话，请检查网络环境以及 SDK 配置是否正确
                            }
                        }
                    });
                }
            }

        });
    }
}
