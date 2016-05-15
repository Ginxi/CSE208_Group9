package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.example.sihan.restaurantrecommendation.R;

import java.util.List;

public class User_MainFragment extends Fragment {
    private TextView displayPersonalBasicInfo;
    private TextView displayNickName;
    private TextView displayGen;
    private TextView displayPhone;
    private TextView displayTasteHobby;
    private TextView displaySuggestion;
    private static String basicInfo ;
    private static String nickName;
    private static String gen;
    private static String phone;
    private static String taste;
    private static String suggestion;
    private static Context mContext;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_fragment_main, container, false);
        mContext = rootView.getContext();
        displayGen=(TextView)rootView.findViewById(R.id.displayGen);
        displayNickName=(TextView)rootView.findViewById(R.id.displayNickName);
        displayPersonalBasicInfo=(TextView)rootView.findViewById(R.id.displayPersonalBasicInfo);
        displayPhone=(TextView)rootView.findViewById(R.id.displayPhone);
        displayTasteHobby=(TextView)rootView.findViewById(R.id.displayTasteHobby);
        displaySuggestion=(TextView)rootView.findViewById(R.id.displaySuggestion);
        logout = (Button) rootView.findViewById(R.id.logOut);
                logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Login.class));
                Login.loginAccount = null;
            }
        });


        final AVQuery<AVObject> query = new AVQuery<>("Userprofile");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                boolean validate = false;
                for (AVObject avObject : list) {
                    String acc = avObject.getString("SignAccount");
                    final String id = avObject.getObjectId();
                    if (Login.loginAccount.equals(acc)) {
                        validate = true;
//                        AVObject access = AVObject.createWithoutData("Userprofile", id);
//                        // String keys = "priority,content";// 指定刷新的 key 字符串
//                        access.fetchInBackground(new GetCallback<AVObject>() {
//                            @Override
//                            public void done(AVObject avObject, AVException e) {
//                                basicInfo = avObject.getString("UserBasicInfo");
//                                nickName = avObject.getString("UserNickName");
//                                phone = avObject.getString("UserPhoneNum");
//                                taste = avObject.getString("UserTasteHobby");
//                                suggestion = avObject.getString("UserSuggestion");
//                                gen = avObject.getString("UserGender");
//                            }
//                        });
                        final AVQuery<AVObject> aquery = new AVQuery<>("Userprofile");
                        aquery.getInBackground(id, new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                basicInfo = avObject.getString("UserBasicInfo");
                                nickName = avObject.getString("UserNickName");
                                phone = avObject.getString("UserPhoneNum");
                                taste = avObject.getString("UserTasteHobby");
                                suggestion = avObject.getString("UserSuggestion");
                                gen = avObject.getString("UserGender");

                                //  Toast.makeText(mContext, id + " " + basicInfo + " " + " " + nickName + " " + phone + " " + taste + " " + suggestion + " " + gen + " ", Toast.LENGTH_SHORT).show();
                                displayGen.setText(gen);
                                displayPhone.setText(phone);
                                displayNickName.setText(nickName);
                                displayTasteHobby.setText(taste);
                                displaySuggestion.setText(suggestion);
                                displayPersonalBasicInfo.setText(basicInfo);
                            }
                        });


                    }
                }

                if (!validate) {
                    Toast.makeText(mContext, "something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


}
