package com.example.lee.ndkdemonativesqlite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lee on 2016/4/28.
 */
public class ResultActivity extends AppCompatActivity{


    private ListViewCompat mListViewCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mListViewCompat = (ListViewCompat) findViewById(R.id.lv_result_list);
        ArrayList<String> resultList = getIntent().getStringArrayListExtra(ResultConst.EXTRA_RESULT_LIST);
        if (resultList != null){
            ResultAdapter adapter = new ResultAdapter(this,0,resultList);
            mListViewCompat.setAdapter(adapter);
        }
    }




    static class ResultAdapter extends ArrayAdapter<String>{

        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<String>  mResultList;


        public ResultAdapter(Context context, int resource,ArrayList<String> list) {
            super(context, resource);
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mResultList = new ArrayList<>(list);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.item_result_list,parent,false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(mResultList.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            return mResultList.size();
        }

    }

    static  class  ViewHolder{
        TextView  tv;
        ViewHolder(View rootView){
            tv = (TextView) rootView.findViewById(R.id.tv_result);
        }
    }


}
