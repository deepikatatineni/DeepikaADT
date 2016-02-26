package com.example.deepikatatineni.sampleapplication;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements ApiResponseParser.RequestCompletedListener {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeApiRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void makeApiRequest() {
        String url = "http://api.nytimes.com/svc/topstories/v1/home.json?api-key=15629235341919a7b4b8b6e9344f9bca:6:72095783";
        ApiResponseParser parser = new ApiResponseParser(url, this);
        parser.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(JSONObject jsonData) {
        ApiResponse response = new ApiResponse();
        response.parse(jsonData);
        ArrayList<ApiResponse.ApiData> imageData = response.mImageData;
        ListView listview = (ListView) findViewById(R.id.lstView);
        MyAdapter ad = new MyAdapter(this, 0, imageData);
        listview.setAdapter(ad);
        Log.i("MainActivity", "onScuccess");
    }

    @Override
    public void onError(String error) {
        Log.e("MainActivity", error);
    }


    public class MyAdapter extends ArrayAdapter<ApiResponse.ApiData> {

        private ArrayList<ApiResponse.ApiData> mData;
        private Context mContext;

        private class Holder {
            TextView mTitleView;
        }

        public MyAdapter(Context context, int resource, ArrayList<ApiResponse.ApiData> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.mData = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder holder = null;
            Log.i("ListView", this.mData.get(position).title);
            if (view == null) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.view_news_row, parent, false);
                holder = new Holder();
                holder.mTitleView = (TextView) view.findViewById(R.id.newsTitle);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            if (holder != null) {
                holder.mTitleView.setText(this.mData.get(position).title);
            }
            return view;
        }


        @Override
        public int getCount() {
            Log.i("ListView", "Data : "+ this.mData.size());
            if (this.mData != null) {
                return this.mData.size();
            }
            return 0;
        }
    }


}

