package sunger.net.org.dropmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sihan.restaurantrecommendation.Function.Restaurant;
import com.example.sihan.restaurantrecommendation.R;

import org.net.sunger.widget.DropDownLayout;
import org.net.sunger.widget.MenuLayout;

import java.util.ArrayList;
import java.util.List;

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
    private String[] restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle mBundle = new Bundle();
        //mBundle.putSerializable(student_key, inputString);
        inputString = intent.getStringExtra("new_man");
        if (inputString != null) {
            restaurantList = inputString.split(";");
            //  Toast.makeText(new_drop.this, restaurantList[0], Toast.LENGTH_LONG).show();
        } else {
            restaurantList = new String[]{"Restaurant 1"};
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_drop_ac);
        menuLayout = (MenuLayout) findViewById(R.id.menuLayout);
        dropDownLayout = (DropDownLayout) findViewById(R.id.dropdown);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentScore());
        fragments.add(new FragmentPrice());
        fragments.add(new FragmentRange());
//         menuLayout.setAnimationIn(R.anim.an);
//         menuLayout.setAnimationOut(R.anim.out);
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopAdapter);
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
        updateTabData();
    }

    public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHoler> {
        //  private LayoutInflater mInflater;
        private String[] text;
        private int[] img = {R.drawable.a};

        public ShopAdapter(Restaurant[] restaurant) {
            text = restaurant;
        }

        @Override
        public ShopViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
            ShopViewHoler svh = new ShopViewHoler(LayoutInflater.from(new_drop.this).inflate(R.layout.list_item, parent, false));
            //  ImageView imv = ImageView.p arent.getResources()
            //   View view = mInflater.inflate(R.layout.new_drop_con,
            //          parent, false);
            //   ShopViewHoler viewHolder = new ShopViewHoler(view);
            //   viewHolder.mImage = (ImageView) view.findViewById(R.);
            //   ImageView tv = (ImageView) parent.getChildAt(0);
            //  tv.setHeight(300);
            return svh;
        }

        @Override
        public void onBindViewHolder(ShopViewHoler holder, int position) {
            //  holder.mTextView.setText(text[position]);
            holder.mTextView.setText(text[position]);
            holder.mImageView.setImageResource(img[0]);
            // holder.mTextView.setImageResource(R.drawable.a);
            //  holder.mImageView.setImageResource(img[position]);
        }


        @Override
        public int getItemCount() {
            return text.length;
        }

        public class ShopViewHoler extends RecyclerView.ViewHolder {

            //     public final TextView mTextView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ShopViewHoler(View itemView) {
                super(itemView);
//                mTextView=(TextView)itemView;
                mTextView = (TextView) itemView.findViewById(R.id.id_item_text);
                mImageView = (ImageView) itemView.findViewById(R.id.id_item_image);
            }

        }
    }
}
