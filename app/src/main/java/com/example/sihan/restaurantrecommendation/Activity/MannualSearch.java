package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sihan.restaurantrecommendation.Function.FirstRatings;
import com.example.sihan.restaurantrecommendation.Function.Restaurant;
import com.example.sihan.restaurantrecommendation.Function.SortRestaurant;
import com.example.sihan.restaurantrecommendation.R;

import java.io.InputStream;
import java.util.ArrayList;

public class MannualSearch extends AppCompatActivity implements OnPageChangeListener {
    public InputStream isFile;
    /**
     * ViewPager
     */
    private String word = "";
    private int averageSpent;
    private String categories;
    private String place;
    private int distances;
    private String scoreFlag;
    private ViewPager viewPager;
    private Spinner spinnerScore;
    private Spinner spinnerAverage;
    private Spinner spinnerDis;
    private Spinner spinnerPlace;
    private EditText search;
    private String pf;
    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] imgIdArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mannual_search);
        //  findViewById(R.id.searchField).setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        //  rankFactor();
        //   Intent intent = new Intent(MannualSearch.this, new_man.class);
        //  Intent data = new Intent();
        //   data.putExtra("data", pf);
        //  startActivity(new Intent(MannualSearch.this, new_drop.class));
        //  }
        // });
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


        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
                R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h};


        //将点点加入到ViewGroup中
        tips = new ImageView[imgIdArray.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {     // changed
                Bitmap bitmap = readBitMap(MannualSearch.this, R.drawable.page_indicator_focused);
                tips[i].setImageBitmap(bitmap);
                //tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                Bitmap bitmap = readBitMap(MannualSearch.this, R.drawable.page_indicator_unfocused);
                tips[i].setImageBitmap(bitmap);
                //tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            } // unchanged

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);
        }


        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {    // changed
            Bitmap bitmap = readBitMap(MannualSearch.this, imgIdArray[i]);
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            //imageView.setBackgroundResource(imgIdArray[i]);
            imageView.setImageBitmap(bitmap);
        }  // unchanged

        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);
        // changed
        spinnerScore = (Spinner) findViewById(R.id.spinnerScore);
        spinnerAverage = (Spinner) findViewById(R.id.spinnerAverage);
        spinnerDis = (Spinner) findViewById(R.id.spinnerDistance);
        spinnerPlace = (Spinner) findViewById(R.id.spinnerDestination);
        spinnerScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                scoreFlag = getResources().getStringArray(R.array.Score)[pos];
                //  Toast.makeText(MannualSearch.this, scoreFlag, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinnerAverage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                averageSpent = Integer.parseInt(getResources().getStringArray(R.array.Average)[pos].substring(2));
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
                    distances = Integer.parseInt(disFlag.substring(7, disFlag.length() - 1));
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

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {   // changed
                Bitmap bitmap = readBitMap(MannualSearch.this, R.drawable.page_indicator_focused);
                tips[i].setImageBitmap(bitmap);
                //   tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                Bitmap bitmap = readBitMap(MannualSearch.this, R.drawable.page_indicator_unfocused);
                //  tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                tips[i].setImageBitmap(bitmap);
            } // unchanged
        }
    }

    public Bitmap readBitMap(Context context, int resId) {   // changed
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }   // unchanged

    //    private void manSearch() {
//        ArrayList<Restaurant> rs = new ArrayList<Restaurant>();
//     //   Spinner spinnerScore = (Spinner) findViewById(com.example.sihan.restaurantrecommendation.R.id.spinnerScore);
//     //   System.out.println(spinnerScore.getSelectedItem());
//        FirstRatings s = new FirstRatings();
//        rs = s.wantedRestaurants(word, averageSpent, categories,
//                place, distances);
//    }
    // changed
    private void rankFactor() {
        FirstRatings fr = new FirstRatings();
        ArrayList<Restaurant> rl = new ArrayList<Restaurant>();
        categories = "";

        isFile = getResources().openRawResource(R.raw.cse208data1short);
        rl = fr.wantedRestaurants(word, averageSpent, categories, place, distances, isFile);
        SortRestaurant sr = new SortRestaurant(rl);
        sr.sortRest(scoreFlag);
        pf = "";
        for (Restaurant r : rl)
            pf += r.toString();
        Toast.makeText(MannualSearch.this, pf, Toast.LENGTH_LONG).show();
    }

    public class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    // unchanged
}
