package com.example.lee.ndkdemonativesqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.lee.ndkdemonativesqlite.db.DataBaseHelper;
import com.example.lee.ndkdemonativesqlite.util.JniUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.et_result);
    }

    public void  showResult(View v){
        Intent intent  = new Intent(this,ResultActivity.class);
        long currentMs = System.currentTimeMillis();
        JniUtil. getDataBaseResultListNormal(this);
        Log.d(TAG,">>>>>>>  Normal: " + (System.currentTimeMillis() - currentMs));
        currentMs = System.currentTimeMillis();
        JniUtil.getDataBaseResultList(this.getDatabasePath(DataBaseHelper.S_DB_NAME).getAbsolutePath());
        Log.d(TAG,">>>>>>>  Native: " + (System.currentTimeMillis() - currentMs));
        ArrayList<String>  resultList  = new ArrayList<>(JniUtil.getDataBaseResultList(this.getDatabasePath(DataBaseHelper.S_DB_NAME).getAbsolutePath()));
        ArrayList<String>  array = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            array.add(resultList.get(i));
        }
        intent.putStringArrayListExtra(ResultConst.EXTRA_RESULT_LIST,array);
        startActivity(intent);
    }


    public void  addResult(View v){
        if (TextUtils.isEmpty(mEditText.getText().toString())) return;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",mEditText.getText().toString());
        dataBase.beginTransaction();
        for (int i = 0; i < 10000; i++) {
            dataBase.insert(DataBaseHelper.S_TAB_NAME,null,values);
        }
        dataBase.setTransactionSuccessful();
        dataBase.endTransaction();
    }


}
