package com.example.vcare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static int[] images = {R.drawable.donate, R.drawable.procure, R.drawable.profile, R.drawable.ourteam1, R.drawable.gallery,R.drawable.recruit1,R.drawable.restaurant};
    public static String Names[] = {"Donate","Procure","Profile","Our Team","Gallery","Recruitment","Restaurant"};
    // ImageView donate,procure;
    MyBroadcast broadcast;
     Context context = this;
    SliderView sliderView;
     //Toolbar toolbar;
   // private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderView = findViewById(R.id.imageSlider);

        final SliderAdapterExample adapter = new SliderAdapterExample(this);
        adapter.setCount(5);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });



        ListView list = findViewById(R.id.MyList);
        CustomAdaptor ad = new CustomAdaptor();
        list.setAdapter(ad);

       /* logout=findViewById(R.id.button2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        */

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
            View v = getLayoutInflater().inflate(R.layout.customlist,null);
            ImageView img = v.findViewById(R.id.imageView3);
            TextView name = v.findViewById(R.id.textView12);
            CardView cardView = v.findViewById(R.id.card);

            img.setImageResource(images[position]);
            name.setText(Names[position]);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        final Dialog dialog = new donate_dialog(context);
                        // dialog.setContentView(R.layout.activity_donate_dialog);
                        dialog.show();
                    }
                    if(position==1){
                        final Dialog dialog = new Procure_dialog(context);
                        // dialog.setContentView(R.layout.activity_donate_dialog);
                        dialog.show();
                    }
                    if(position==2){
                        Intent i = new Intent(MainActivity.this, Profile.class);
                        startActivity(i);
                    }
                    if(position==3){
                        Intent i = new Intent(MainActivity.this,OurTeam.class);
                        startActivity(i);
                    }
                    if(position==4){
                        Intent i = new Intent(MainActivity.this,Gallery.class);
                        startActivity(i);
                    }
                    if(position==5){
                        Intent i = new Intent(MainActivity.this,Recruitment.class);
                        startActivity(i);
                    }
                    if(position==6){
                        Intent i = new Intent(MainActivity.this,ContactRecycler.class);
                        startActivity(i);
                    }
                    //if(position==5){
                //FirebaseAuth.getInstance().signOut();
                        //SignIngoogle ggl=new SignIngoogle();
                       // ggl.SignOut();

                       // Toast.makeText(getApplicationContext(),"Logout Successfully",Toast.LENGTH_SHORT).show();
                       //Intent i=new Intent(getApplicationContext(),Welcome.class);
                        //startActivity(i);
                    //}
                }
            });

            return v;
        }
    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
       // mDemoSlider.stopAutoCycle();
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        broadcast=new MyBroadcast();
        IntentFilter filter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcast,filter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcast);
    }

}
