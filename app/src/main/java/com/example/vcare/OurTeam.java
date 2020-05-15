package com.example.vcare;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OurTeam extends AppCompatActivity {
    static int[] images1 = {R.drawable.sachin1, R.drawable.myprofile, R.drawable.mayank1, R.drawable.tyagi1,R.drawable.singh};
    public static String Names[] = {"Sachin Katiyar","Himanshu Agarwal","Mayank Chaplot","Shivam Tyagi","Shivam Singh"};
    public static String Expi[] = {"Experience","Experience","Experience","Experience","Experience"};
    public static String Years[] = {"8 Months","8 Months","6 Months","6 months","6 months"};
    public static String Phone[] = {"7742065586","9024402980","9672736961","9660834852","7266871111"};
    MyBroadcast broadcast;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);

        toolbar = findViewById(R.id.toolbar9);
        toolbar.setTitle("Our Team");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));
        ListView list = findViewById(R.id.list);
        CustomAdaptor ad = new CustomAdaptor();
        list.setAdapter(ad);
    }

    class CustomAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return Names.length;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_team,null);
            ImageView photo = v.findViewById(R.id.photo);
            TextView name = v.findViewById(R.id.pdct1);
            TextView expi = v.findViewById(R.id.expi);
            TextView years = v.findViewById(R.id.years);

            photo.setImageResource(images1[position]);
            name.setText(Names[position]);
            expi.setText(Expi[position]);
            years.setText(Years[position]);
            ImageButton call = v.findViewById(R.id.call);
            call.setImageResource(R.drawable.findjob8);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        Uri uri=Uri.parse("tel:"+Phone[position]);
                        Intent telIntent=new Intent(Intent.ACTION_DIAL,uri);

                        if(telIntent.resolveActivity(getPackageManager())!=null)
                            startActivity(telIntent);
                    }
                    if(position==1){
                        Uri uri=Uri.parse("tel:"+Phone[position]);
                        Intent telIntent=new Intent(Intent.ACTION_DIAL,uri);

                        if(telIntent.resolveActivity(getPackageManager())!=null)
                            startActivity(telIntent);
                    }
                    if(position==2){
                        Uri uri=Uri.parse("tel:"+Phone[position]);
                        Intent telIntent=new Intent(Intent.ACTION_DIAL,uri);

                        if(telIntent.resolveActivity(getPackageManager())!=null)
                            startActivity(telIntent);
                    }
                    if(position==3){
                        Uri uri=Uri.parse("tel:"+Phone[position]);
                        Intent telIntent=new Intent(Intent.ACTION_DIAL,uri);

                        if(telIntent.resolveActivity(getPackageManager())!=null)
                            startActivity(telIntent);
                    }
                    if(position==4){
                        Uri uri=Uri.parse("tel:"+Phone[position]);
                        Intent telIntent=new Intent(Intent.ACTION_DIAL,uri);

                        if(telIntent.resolveActivity(getPackageManager())!=null)
                            startActivity(telIntent);
                    }
                }
            });
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
