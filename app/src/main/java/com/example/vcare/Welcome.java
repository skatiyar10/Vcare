package com.example.vcare;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
    MyBroadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView i = findViewById(R.id.imageView);
        i.setImageResource(R.drawable.vcare3);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.splash);
        i.setAnimation(a);

a.setAnimationListener(new Animation.AnimationListener()
{
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        finish();
        startActivity(new Intent(getApplicationContext(), SignIngoogle.class));
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
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
