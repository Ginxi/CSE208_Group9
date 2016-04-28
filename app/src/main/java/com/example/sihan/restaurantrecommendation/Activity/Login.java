package com.example.sihan.restaurantrecommendation.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sihan.restaurantrecommendation.R;

public class Login extends AppCompatActivity {
    SQLiteOpenHelper helper;
    private EditText et_id, et_name;
    private Button btn_test, btn_local;
    private String _id;
    private String _name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sihan.restaurantrecommendation.R.layout.activity_login);
        helper = new Sqliteopenhelper(this);
        helper.getWritableDatabase();
        et_id = (EditText) findViewById(R.id.Account);
        et_name = (EditText) findViewById(R.id.passwordin);
        btn_test = (Button) findViewById(R.id.signUp);
        btn_local = (Button) findViewById(R.id.login);

        btn_test.setOnClickListener(new testListener());
        btn_local.setOnClickListener(new localListener());

        findViewById(R.id.useAsVisitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, new_man.class));

            }
        });

    }

    private void sureuser(String userid, String username) {
        //3,数据库的操作，查询
        SQLiteDatabase sdb = helper.getReadableDatabase();
        try {

            String sql = "select * from student where id=? and name=?";
            // 实现遍历id和name
            Cursor cursor = sdb.rawQuery(sql, new String[]{_id, _name});
            if (cursor.getCount() > 0) {
                Intent intent = new Intent(Login.this, MainMenu.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", _name);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Login failed",
                        Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            sdb.close();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Please sign up!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //登陆按钮

    class testListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }

    }

    class localListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            _id = et_id.getText().toString();
            _name = et_name.getText().toString();
            if (_name.equals("") || _id.equals("")) {
                Toast.makeText(getApplicationContext(), "Please input password:",
                        Toast.LENGTH_SHORT).show();
            } else {
                sureuser(_id, _name);
            }
        }

    }

}
