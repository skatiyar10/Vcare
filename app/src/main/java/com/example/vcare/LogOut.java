package com.example.vcare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LogOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        //FirebaseAuth.getInstance().signOut();
    }
}
