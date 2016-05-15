package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.sihan.restaurantrecommendation.Function.EfficientUser;
import com.example.sihan.restaurantrecommendation.Function.FirstRatings;
import com.example.sihan.restaurantrecommendation.Function.Restaurant;
import com.example.sihan.restaurantrecommendation.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import sunger.net.org.dropmenu.new_drop;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class new_man extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {
    public InputStream isFile;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_chuan;
    private LinearLayout linearLayout;
    private String word = "";
    private int averageSpent;
    private String categories = "";
    private String place;
    private int distances;
    private String scoreFlag;
    private ViewPager viewPager;
    private Spinner spinnerScore;
    private Spinner spinnerAverage;
    private Spinner spinnerDis;
    private Spinner spinnerPlace;
    private EditText search;
    private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private int[] imgArray = new int[] {R.drawable.content_chuan,R.drawable.content_xiang,R.drawable.content_hotpot,R.drawable.content_yue,
            R.drawable.content_su,R.drawable.content_japan,R.drawable.content_west,R.drawable.content_korea};
    private int imgIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_man);

        ArrayList<EfficientUser> users = (ArrayList<EfficientUser>) getIntent().getSerializableExtra("user");

      //  Toast.makeText(new_man.this, users.get(0).getName(), Toast.LENGTH_LONG).show();
        findViewById(R.id.searchField).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankFactor();
//                Bundle mBundle = new Bundle();
//                for(int i = 0; i < restaurantList.size(); i++) {
//                    mBundle.putSerializable(String.valueOf(i), (Serializable) restaurantList.get(i));
//                }
                String str = "";
                for (int i = 0; i < restaurantList.size(); i++)
                {
                    str += restaurantList.get(i).toString() + ";";
                }
                Intent intent = new Intent(new_man.this, new_drop.class);
                intent.putExtra("res", str);
               startActivity(intent);
            }
        });
        search = (EditText) findViewById(R.id.et_search1);
        TextWatcher watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                word = s.toString();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        };
        search.addTextChangedListener(watcher);

        //add finished

        contentFragment = ContentFragment.newInstance(R.drawable.content_chuan);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);

        //add

        spinnerAverage = (Spinner) findViewById(R.id.spinnerAverage);
        spinnerDis = (Spinner) findViewById(R.id.spinnerDistance);
        spinnerPlace = (Spinner) findViewById(R.id.spinnerDestination);

        spinnerAverage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                try {
                    averageSpent = Integer.parseInt(getResources().getStringArray(R.array.Average)[pos].substring(3, getResources().getStringArray(R.array.Average)[pos].length()));
                } catch(NumberFormatException ex) {
                    averageSpent = Integer.MAX_VALUE;
                }
                //  Toast.makeText(MannualSearch.this, sxs, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinnerDis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String disFlag = getResources().getStringArray(R.array.Distance)[pos];
                if (disFlag.length() > 5) {
                    distances = Integer.parseInt(disFlag.substring(2, disFlag.length() - 1));
                } else {
                    distances = Integer.MAX_VALUE;
                }
                //  Toast.makeText(MannualSearch.this, Integer.toString(distances), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                place = getResources().getStringArray(R.array.Destination)[pos];
                if (place.equals("All"))
                    place = "";
                //    Toast.makeText(MannualSearch.this, placeFlag, 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        // unchanged
    }
    //add finish

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.SICHUAN, R.drawable.sichuan);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.HUNAN, R.drawable.hunan);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.HOTPOT, R.drawable.hotpot);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CANTONESE, R.drawable.cantonese);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SOOCHAW, R.drawable.soochaw);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.JAPAN, R.drawable.japan);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.WESTERN, R.drawable.western);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem(ContentFragment.KOREAN, R.drawable.korean);
        list.add(menuItem8);
    }

    // changed
    private void rankFactor() {
        FirstRatings fr = new FirstRatings();
        //  ArrayList<Restaurant> rl = new ArrayList<Restaurant>();

        isFile = getResources().openRawResource(R.raw.cse208data1short);
        restaurantList = fr.wantedRestaurants(word, averageSpent, categories, place, distances, isFile);

        //SortRestaurant sr = new SortRestaurant(rl);
        // sr.sortRest(scoreFlag);
        //  pf = "";
//        for(Restaurant r : rl)
//            pf += r.toString() + ";";
        //  Toast.makeText(new_man.this, pf, Toast.LENGTH_LONG).show();
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.man_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        if (drawerToggle.onOptionsItemSelected(item)) {
////
////        }
////        switch (item.getItemId()) {
////            case R.id.action_settings:
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
//        return true;
//    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
       // this.res = this.res == R.drawable.content_chuan ? R.drawable.content_western : R.drawable.content_chuan;
        this.res = imgArray[imgIndex];
        //Toast.makeText(new_man.this, String.valueOf(topPosition), Toast.LENGTH_LONG).show();
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
       // ImageView img = (ImageView) findViewById(R.id.content_overlay);
       // Bitmap bitmap = ShopAdapter.readBitMap(new_man.this, imgArray[imgIndex]);
      //  img.setImageBitmap(bitmap);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                categories = "";
                return screenShotable;
            case ContentFragment.SICHUAN:
                categories = "Sichuan";
                imgIndex = 0;
                return replaceFragment(screenShotable, position);
            case ContentFragment.HUNAN:
                categories = "Hunan";
                imgIndex = 1;
                return replaceFragment(screenShotable, position);
            case ContentFragment.HOTPOT:
                categories = "Hotpot";
                imgIndex = 2;
                return replaceFragment(screenShotable, position);
            case ContentFragment.CANTONESE:
                categories = "Cantonese";
                imgIndex = 3;
                return replaceFragment(screenShotable, position);
            case ContentFragment.SOOCHAW:
                categories = "Suzhou";
                imgIndex = 4;
                return replaceFragment(screenShotable, position);
            case ContentFragment.JAPAN:
                categories = "Japanese";
                imgIndex = 5;
                return replaceFragment(screenShotable, position);
            case ContentFragment.WESTERN:
                categories = "Western";
                imgIndex = 6;
                return replaceFragment(screenShotable, position);
            case ContentFragment.KOREAN:
                categories = "Korean";
                imgIndex = 7;
                return replaceFragment(screenShotable, position);
            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
