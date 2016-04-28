package com.example.lee.ndkdemonativesqlite.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lee.ndkdemonativesqlite.db.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by Lee on 2016/4/29.
 */
public class JniUtil {
    static {
        System.loadLibrary("nativesqlite");
     }

    /**
     * Native 函数 返回数据库中  DataBaseHelper.S_TAB_NAME 表下所有数据列表
     * @param dbPath
     * @return
     */
    public static native ArrayList<String> getDataBaseResultList(String dbPath);


    public static ArrayList<String>  getDataBaseResultListNormal(Context context){
        ArrayList<String> resultList = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase database =   dataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseHelper.S_TAB_NAME,new String[]{"name"},null,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                resultList.add(cursor.getString(0));
            }
        }
        return resultList;
    }

}

