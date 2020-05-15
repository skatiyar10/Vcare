package com.example.vcare;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Gallery extends AppCompatActivity {
    static int[] Img = {R.drawable.slider3, R.drawable.gallery1, R.drawable.gallery2, R.drawable.gallery3, R.drawable.gallery4, R.drawable.slider2, R.drawable.slider1};
    Toolbar toolbar;
    MyBroadcast broadcast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Gallery");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));
        //toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        ListView list = findViewById(R.id.list1);
        CustomAdaptor ad = new CustomAdaptor();
        list.setAdapter(ad);
    }

    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return Img.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_gallery,null);
            ImageView imageView = v.findViewById(R.id.galleryImg);
            imageView.setImageResource(Img[position]);
            return v;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcast=new MyBroadcast();
        IntentFilter filter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcast,filter);

    }

    @Override
    protected void onStop() {
        super.onStop();

        //unregisterReceiver(broadCast);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcast);
    }
}
