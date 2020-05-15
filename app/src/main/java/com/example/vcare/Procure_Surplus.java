package com.example.vcare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Procure_Surplus extends AppCompatActivity
{
    Toolbar toolbar;
    DatabaseReference databaseReference;
    ListView listview;
    List<Packedfooddata> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procure__surplus);

        toolbar = findViewById(R.id.toolbar4);
        toolbar.setTitle("Procure Food");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));
        databaseReference= FirebaseDatabase.getInstance().getReference("Packedfooddata");

        listview = findViewById(R.id.listProcure);
        foodlist=new ArrayList<>();

       // CustomAdaptor ad = new CustomAdaptor();
        //listview.setAdapter(ad);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot artistsnapshot: dataSnapshot.getChildren())
                {

                    Packedfooddata packed=artistsnapshot.getValue(Packedfooddata.class);
                    foodlist.add(packed);

                }
                Log.e("PackedFoodHimanshu",foodlist.size()+"");
                ProcureItemList adapter1=new ProcureItemList(Procure_Surplus.this,foodlist);
                listview.setAdapter(adapter1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class CustomAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return foodlist.size();
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
            View v = getLayoutInflater().inflate(R.layout.custom_procure,null);

            return v;
        }
    }
}
