package com.example.vcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Recruitment extends AppCompatActivity {
    EditText name ,mobile,email,age ,address;
    Toolbar toolbar;
    DatabaseReference databaseref;
    RecruitmentGet recruitmentGet;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Volunteer Recruitment");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        submit=findViewById(R.id.buttonSubmit);


        databaseref= FirebaseDatabase.getInstance().getReference("Recruit");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rname=name.getText().toString().trim();
                String rmob=mobile.getText().toString().trim();
                String remail=email.getText().toString().trim();
                String rage=age.getText().toString().trim();
                String raddress=address.getText().toString().trim();

                recruitmentGet = new RecruitmentGet(rname,rmob,remail,rage,raddress);
                if (TextUtils.isEmpty(rname) || TextUtils.isEmpty(rmob) ||TextUtils.isEmpty(remail) || TextUtils.isEmpty(rage) ||TextUtils.isEmpty(raddress))
                {
                    Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseref.push().setValue(recruitmentGet).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            name.setText(" ");
                            mobile.setText(" ");
                            email.setText(" ");
                            age.setText(" ");
                            address.setText(" ");
                            Toast.makeText(getApplicationContext(), "Our team will contact you soon!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}