package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.sihan.restaurantrecommendation.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class User_MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener{

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private ArrayList<String> resid = new ArrayList<String>();
    public static ArrayList<com.example.sihan.restaurantrecommendation.Function.Restaurant> resList = new ArrayList<com.example.sihan.restaurantrecommendation.Function.Restaurant>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AVQuery<AVObject> query = new AVQuery<>("User"+Login.loginAccount);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    String ressssid = avObject.getString("RestaurantID");
                    if (ressssid != null) {
                        resid.add(ressssid);
                        //  Toast.makeText(favouriteList.this, ressssid, Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

        final AVQuery<AVObject> queryres = new AVQuery<>("cse208data1short");
        queryres.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                resList.clear();
                for(String str: resid) {
                    for (AVObject avObject : list) {
                        String ressssid = avObject.getString("restaurantID");
                        if (ressssid != null && ressssid.equals(str)) {
                            resList.add(new com.example.sihan.restaurantrecommendation.Function.Restaurant(ressssid, avObject.getString("title"),
                                    Integer.parseInt(avObject.getString("averageSpent")), avObject.getString("categories"), Double.parseDouble(avObject.getString("environmentScore")),
                                    Double.parseDouble(avObject.getString("serviceScore")), Double.parseDouble(avObject.getString("flavorScore")), avObject.getString("phoneNumber"),
                                    avObject.getString("address"), Integer.parseInt(avObject.getString("distance"))));
                        }
                    }
                }
            }

        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
         addFragment(new User_MainFragment(), true, R.id.containerUser);

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.user_icn_close);

       // MenuObject send = new MenuObject("Comment Restaurant");
      //  send.setResource(R.drawable.user_icn_1);


      //  MenuObject like = new MenuObject("Like profile");
        //Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.user_icn_2);
        //like.setBitmap(b);


        MenuObject addFr = new MenuObject("Edit Personal Profile");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.user_icn_3));
        addFr.setDrawable(bd);


        MenuObject addFav = new MenuObject("Favourite Restaurant");
        addFav.setResource(R.drawable.user_icn_4);


        MenuObject block = new MenuObject("Create new account");
        block.setResource(R.drawable.user_icn_5);

        menuObjects.add(close);
       // menuObjects.add(send);
        //menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }


    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.user_toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("My Page");
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else{
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
      //  Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        switch(position) {
            case 1:
                startActivity(new Intent(User_MainActivity.this, editPersonalProfile.class));
                break;
            case 2:
                startActivity(new Intent(User_MainActivity.this, favouriteList.class));
                break;
            case 3:
                startActivity(new Intent(User_MainActivity.this, Register.class));
                break;
        }

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

       // Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
        switch(position) {
            case 1:
                startActivity(new Intent(User_MainActivity.this, editPersonalProfile.class));
                break;
            case 2:
                startActivity(new Intent(User_MainActivity.this, favouriteList.class));
                break;
            case 3:
                startActivity(new Intent(User_MainActivity.this, Register.class));
                break;
        }


    }
}
