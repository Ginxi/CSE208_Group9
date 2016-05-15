package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.sihan.restaurantrecommendation.R;

import java.util.List;

public class Login extends AppCompatActivity {
    private EditText et_account, et_password;
    private String account;
    private String password;
    private Button login;
    private Button register;
    public static String loginAccount;
    public static String loginPassword;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String objectID;
        try{
            Intent intent=getIntent();
            Bundle bundle = intent.getExtras();
            objectID= bundle.getString("id","null");
        }
        catch (Exception e){
            objectID="null";
        }
        et_account = (EditText) findViewById(R.id.Account);
        et_password = (EditText) findViewById(R.id.passwordin);

        account = et_account.getText().toString();
        password = et_password.getText().toString();
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.signUp);
        register.setOnClickListener(new testListener());
        login.setOnClickListener(new localListener());

        findViewById(R.id.useAsVisitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, new_man.class));
            }
        });
    }

    class localListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            account = et_account.getText().toString();
            password = et_password.getText().toString();
            if (password.equals("") || account.equals("")) {
                Toast.makeText(getApplicationContext(), "Please input account & password:",
                        Toast.LENGTH_SHORT).show();
            } else {
                sureuser(account, password);
                Intent intent = new Intent(Login.this, MainMenu.class);
                startActivity(intent);
            }
        }

    }

    private void sureuser(String userid, String username) {
        final AVQuery<AVObject> query = new AVQuery<>("Useraccount");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                boolean validate = false;
                for(AVObject avObject:list){
                    String acc = avObject.getString("SignAccount");
                    String pass= avObject.getString("SignPassword");
                    if (et_password.getText().toString().equals(pass) && et_account.getText().toString().equals(acc)) {
                        validate=true;
                        loginAccount = acc;
                        loginPassword = pass;

                    }
                }
                if(!validate){
                    Toast.makeText(getApplicationContext(), "Login failed !",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class testListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }
    }

}


