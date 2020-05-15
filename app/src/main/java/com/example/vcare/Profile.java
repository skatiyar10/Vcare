package com.example.vcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity
{
    ImageView userPhoto;
    TextView userName;
    TextView userEmail;
   // TextView userContact;
    EditText mobile;
    Button save;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));

        userPhoto=findViewById(R.id.userphoto);
        userName=findViewById(R.id.username);
        //userContact=findViewById(R.id.usercontact);
        userEmail=findViewById(R.id.useremail);
        mobile=findViewById(R.id.usercontact);

       //SignIngoogle takevalue=new SignIngoogle();
        //userName.setText(takevalue.returnname());
        SharedPreferences sharedPref = this.getSharedPreferences("FromsignIn",Context.MODE_PRIVATE);
        String name = sharedPref.getString("keyname","default");
        String email = sharedPref.getString("keyemail","default");
        String image=sharedPref.getString("keyimage",null);
        String m=sharedPref.getString("keymobile",null);
        mobile.setText(m);
       // int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
        userName.setText(name);
        Log.e("image",image);

        userEmail.setText(email);
        userPhoto.setImageResource(R.drawable.vcare);
       // userPhoto.setImageURI(Uri.parse(image));
        Picasso.get().load(image).into(userPhoto);

        save = findViewById(R.id.buttonsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mob = mobile.getText().toString().trim();
                SharedPreferences pref=getSharedPreferences("FromsignIn",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("keymobile",mob);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Saved Succesfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
