package sunger.net.org.dropmenu;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sihan.restaurantrecommendation.Function.Restaurant;
import com.example.sihan.restaurantrecommendation.Function.SortRestaurant;
import com.example.sihan.restaurantrecommendation.R;

import org.net.sunger.widget.DropDownLayout;
import org.net.sunger.widget.MenuLayout;

import java.util.ArrayList;
import java.util.List;

import sunger.net.org.dropmenu.adapter.ShopAdapter;
import sunger.net.org.dropmenu.fragment.FragmentPrice;
import sunger.net.org.dropmenu.fragment.FragmentRange;
import sunger.net.org.dropmenu.fragment.FragmentScore;
import sunger.net.org.dropmenu.tab.CommonTabLayout;
import sunger.net.org.dropmenu.tab.listener.CustomTabEntity;
import sunger.net.org.dropmenu.tab.listener.OnTabSelectListener;

public class new_drop extends AppCompatActivity {
    private CommonTabLayout tabs;
    private String[] mTitles = {"Score Ranking", "Price Ranking", "Distance Ranking"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_floor_unselected, R.mipmap.tab_category_unseleted,
            R.mipmap.tab_sort_unseleted};
    private int[] mIconSelectIds = {
            R.mipmap.tab_floor_selected, R.mipmap.tab_category_seleted,
            R.mipmap.tab_floor_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private DropDownLayout dropDownLayout;
    private MenuLayout menuLayout;
    private ShopAdapter shopAdapter;
    private String inputString;
    private String[] restaurantInfo;
    private ArrayList<Restaurant> restaurantList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
  //      restaurantList = (ArrayList<Restaurant>) getIntent().getSerializableExtra("res");
        inputString = getIntent().getStringExtra("res");
        restaurantList = new ArrayList<Restaurant>();
        if (inputString.endsWith(";")) {
            restaurantInfo = inputString.split(";");
            int count = 0;
            for (String temp: restaurantInfo)
            {
                String anID = temp.split(",")[0].split("=")[1];
                String aTitle = temp.split(",")[1].split("=")[1];
                int anAverageSpent = Integer.parseInt(temp.split(",")[2].split("=")[1]);
                String aCategories = temp.split(",")[3].split("=")[1];
                double anEnvironmentScore = Double.parseDouble(temp.split(",")[4].split("=")[1]);
                double aServiceScore = Double.parseDouble(temp.split(",")[5].split("=")[1]);
                double aFlavorScore = Double.parseDouble(temp.split(",")[6].split("=")[1]);
                String aPhoneNumber = temp.split(",")[7].split("=")[1];
                String anAddress = temp.split(",")[8].split("=")[1];
                int aDistance = Integer.parseInt(temp.split(",")[9].split("=")[1].split("]")[0]);
                restaurantList.add(new Restaurant(anID,aTitle,anAverageSpent,aCategories,anEnvironmentScore,aServiceScore,aFlavorScore,aPhoneNumber,anAddress,aDistance));
            }
        } else {
            restaurantInfo = new String[]{"Restaurant"};
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_drop_ac);
   //     Toast.makeText(new_drop.this,String.valueOf(), Toast.LENGTH_LONG).show();
        menuLayout = (MenuLayout) findViewById(R.id.menuLayout);
        dropDownLayout = (DropDownLayout) findViewById(R.id.dropdown);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentScore());
        fragments.add(new FragmentPrice());
        fragments.add(new FragmentRange());
        menuLayout.setFragmentManager(getSupportFragmentManager());
        menuLayout.bindFragments(fragments);
        tabs = (CommonTabLayout) findViewById(R.id.tabs);
        updateTabData();

        tabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                dropDownLayout.showMenuAt(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (menuLayout.isShow()) {
                    dropDownLayout.closeMenu();
                } else {
                    dropDownLayout.showMenuAt(position);
                }

            }
        });

        shopAdapter = new ShopAdapter(restaurantList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(10);
        dividerLine.setColor(0xFFDDDDDD);
        recyclerView.addItemDecoration(dividerLine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.setOnItemClickListener(new ShopAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent(new_drop.this, com.example.sihan.restaurantrecommendation.Activity.Restaurant.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, String data) {

            }
        });
    }

    private void updateTabData() {
        mTabEntities.clear();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabs.setTabData(mTabEntities);
    }

    public void onFilter(int type, String tag) {
        dropDownLayout.closeMenu();
        switch (type) {
            case 0:
                mTitles[0] = tag;
                break;
            case 1:
                mTitles[1] = tag;

                break;
            case 2:
                mTitles[2] = tag;
                break;
        }
//        ArrayList<Restaurant> temp = sortRestaurant(tag);
        sortRestaurant(tag);
//        for (int i = 0; i < restaurantList.size(); i++){
//            restaurantInfo[i] = restaurantList.get(i).toString();
//        }
        shopAdapter = new ShopAdapter(restaurantList);
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.setOnItemClickListener(new ShopAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent(new_drop.this, com.example.sihan.restaurantrecommendation.Activity.Restaurant.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, String data) {

            }
        });
//        String str = "";
//        for (int i = 0; i <restaurantList.size(); i++)
//            str += restaurantList.get(i).toString() + ";";
//        Toast.makeText(new_drop.this, str, Toast.LENGTH_LONG).show();
        updateTabData();
    }

    public static class DividerLine extends RecyclerView.ItemDecoration {
        public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
        public static final int VERTICAL = LinearLayoutManager.VERTICAL;
        private Paint paint;
        private int orientation;
        private int color;
        private int size;
        public DividerLine() {
            this(VERTICAL);
        }
        public DividerLine(int orientation) {
            this.orientation = orientation;

            paint = new Paint();
        }
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            if (orientation == VERTICAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
        public void setColor(int color) {
            this.color = color;
            paint.setColor(color);
        }
        public void setSize(int size) {
            this.size = size;
        }
        protected void drawVertical(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + size;

                c.drawRect(left, top, right, bottom, paint);
            }
        }
        protected void drawHorizontal(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + size;
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }
    private void sortRestaurant(String tag) {
        SortRestaurant sr = new SortRestaurant();
//        ArrayList<Restaurant> res = sr.sortRest(tag, restaurantList);
        sr.sortRest(tag, restaurantList);
        //return res;
    }
}
