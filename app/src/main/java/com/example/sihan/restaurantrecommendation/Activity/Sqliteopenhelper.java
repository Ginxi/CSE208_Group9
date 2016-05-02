package com.example.sihan.restaurantrecommendation.Activity;

/**
 * Created by Sihan on 2016/4/23.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//新建类Sqliteopenhelper 继承于SQLiteOpenHelper

public class Sqliteopenhelper extends SQLiteOpenHelper {

    //数据库名：
    private static final String DBNAME="test.db";
    //表名
    private static final String TABLENAME="student";
    private static final String GOODSNAME="goods";
    //版本号：具体我也不知道是什么，照着写就行了
    private static final int TESTVERSION=1;

    public Sqliteopenhelper(Context context) {
        super(context, DBNAME, null, TESTVERSION);
        // TODO Auto-generated constructor stub
    }

    //初始化，创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql1="create table"+" "+TABLENAME+"(id varchar,name varchar)";
        String sql2="create table"+" "+GOODSNAME+"(LF varchar,name varchar,miaoshu varchar)";
        db.execSQL(sql1);
        db.execSQL(sql2);

    }
    //失败后删除，重新创建
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if(newVersion>oldVersion)
        {
            String sql1="drop table if exists"+TABLENAME;
            String sql2="drop table if exists"+GOODSNAME;
            db.execSQL(sql1);
            db.execSQL(sql2);
            this.onCreate(db);
        }
    }

}