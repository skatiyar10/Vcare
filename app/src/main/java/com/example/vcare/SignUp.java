package com.example.vcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner spinnercity;
    EditText txtmobile;
    SharedPreferences sharedpreferences;
    Button signup;
    ImageView logoimg;
    DatabaseReference dataReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spinnercity=findViewById(R.id.cityspinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.cities,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercity.setAdapter(adapter);
        spinnercity.setOnItemSelectedListener(this);

       txtmobile=findViewById(R.id.mobileno);
       logoimg=findViewById(R.id.imageView2);
       logoimg.setImageResource(R.drawable.vcare);

        signup=findViewById(R.id.Signup);
//        dataReference= FirebaseDatabase.getInstance().getReference("Person");
       //firebaseAuth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // FirebaseAuth.getInstance().signOut();
                //Toast.makeText(getApplicationContext(),"In sign Up", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Profile.class);
               startActivity(i);
                Toast.makeText(getApplicationContext(),"In sign Up", Toast.LENGTH_LONG).show();



                /*
                final String Fname=txtfirstname.getText().toString();
                final String Lname=txtlastname.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(Fname,Lname)
                       .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful())
                              {
                                    // Sign in success, update UI with the signed-in user's information
                                    PersonalData personalData=new PersonalData(Fname,Lname);
                                    FirebaseDatabase.getInstance().getReference("Person")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                    .setValue(personalData).addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                    });

                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                   Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show(); }
                           }
                        });
            */
            }

        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String text=parent.getItemAtPosition(position).toString();
        sharedpreferences=getSharedPreferences("sign",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("citykey", text);
        editor.commit();
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}

