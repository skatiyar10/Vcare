package com.example.vcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NoInternet extends AppCompatActivity implements View.OnClickListener{
    Button tryagain;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        tryagain=findViewById(R.id.tryagain);
        tryagain.setOnClickListener( this);

    }


    @Override
    public void onClick(View v)
    {
        finish();
    }
}
