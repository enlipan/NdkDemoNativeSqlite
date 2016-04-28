package com.example.lee.ndkdemonativesqlite.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Lee on 2016/4/28.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    public static final String S_DB_NAME = "nativesql.db";

    public  static  final String S_TAB_NAME = "result";

    private static final String  S_CREATE_DB_FILE_NAME = "database_create_script.sql";

    private static final int  S_DATABASE_VERSION = 1;

    private Context mContext;

    public DataBaseHelper(Context context){
        this(context,S_DB_NAME,null,S_DATABASE_VERSION);
        mContext = context;
    }


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version,null);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        InputStream  inStreamCreate = null;
        /*InputStream  inputStreamInsert = null;*/
        try {
            inStreamCreate = mContext.getAssets().open(S_CREATE_DB_FILE_NAME);
            String  createDbSql = parseDbScript(inStreamCreate);
            db.execSQL(createDbSql);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inStreamCreate != null) {
                    inStreamCreate.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String parseDbScript(InputStream inStreamCreate) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inStreamCreate,"UTF-8");
        BufferedReader bufferReader = new BufferedReader(reader);
        String contentLine = bufferReader.readLine();
        while (!TextUtils.isEmpty(contentLine)){
            builder.append(contentLine);
            contentLine = bufferReader.readLine();
        }
        bufferReader.close();
        reader.close();
        return  builder.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
