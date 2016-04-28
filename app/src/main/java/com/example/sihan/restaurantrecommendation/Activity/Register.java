package com.example.sihan.restaurantrecommendation.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sihan.restaurantrecommendation.Function.User;
import com.example.sihan.restaurantrecommendation.R;

public class Register extends AppCompatActivity {
    SQLiteOpenHelper helper;
    private EditText etid, etname;
    private Button btn_qu, btn_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.sihan.restaurantrecommendation.R.layout.activity_register);
        helper = new Sqliteopenhelper(this);
        helper.getWritableDatabase();
        etid = (EditText) findViewById(R.id.createAccount);
        etname = (EditText) findViewById(R.id.createPassword);
        btn_qu = (Button) findViewById(R.id.btn_qu);
        btn_sure = (Button) findViewById(R.id.signNewAccount);
        btn_sure.setOnClickListener(new sureListener());
        btn_qu.setOnClickListener(new quListener());

    }

    class sureListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            try {
                SQLiteDatabase sdb = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", etid.getText().toString());
                values.put("name", etname.getText().toString());
                sdb.insert("student", null, values);
                Toast.makeText(getApplicationContext(), "Sign successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, User.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", etname.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            } catch (SQLiteException e) {
                Toast.makeText(getApplicationContext(), "Sign Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class quListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        }

    }
}
