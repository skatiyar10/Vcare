package com.example.vcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIngoogle extends AppCompatActivity
{
    GoogleSignInClient mgooglesignclient;
    private  SignInButton signin;
   // Button SignoutButton;
    private int RC_SIGN_IN=1;
    private  String TAG="SIgnInGOOGLE";
    public FirebaseAuth mAuth;
    ImageView logo;
    SharedPreferences sf;
    PersonalData personalData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_ingoogle);

        logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.vcare3);
        signin= (SignInButton) findViewById(R.id.googlesignin);
       // SignoutButton=findViewById(R.id.signoutbutton);

        mAuth = FirebaseAuth.getInstance();

       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
       mgooglesignclient= GoogleSignIn.getClient(this,gso);

        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent signinintent=mgooglesignclient.getSignInIntent();
                startActivityForResult(signinintent,RC_SIGN_IN);

            }
        });
/*
        SignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SignOut();
            }
        });

 */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
       // Intent i = new Intent(this,SignUp.class);
        //startActivity(i);
    }

    private void updateUI(FirebaseUser currentUser)
    {
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account!=null)
        {
            String personalname;
            String personalemail;
           // String personId;
            String persongivenname;
            //String personfamilyname;
            Uri personphoto;
            String imageuser;
            personalname=account.getDisplayName();
            personalemail =account.getEmail();
           // personId=account.getId();
            persongivenname=account.getGivenName();
            //personfamilyname=account.getFamilyName();
            personphoto=account.getPhotoUrl();
            imageuser=personphoto.toString().trim();

            personalData = new PersonalData(personalname,personalemail,imageuser);
            FirebaseDatabase.getInstance().getReference("PersonalData")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(personalData).addOnCompleteListener(new OnCompleteListener<Void>(){
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                   // Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                }
            });

            sf=getSharedPreferences("FromsignIn",MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("keyname",personalname);
            editor.putString("keyemail",personalemail);
            editor.putString("keyimage",personphoto.toString());
            editor.commit();

           // Toast.makeText(this,"Name of User:"+personphoto.toString().trim(),Toast.LENGTH_SHORT).show();
           // signin.setVisibility(View.GONE);
           //SignoutButton.setVisibility(View.VISIBLE);

            Intent i = new Intent(this,MainActivity.class);
           finish();
            startActivity(i);
        }
        else
            {
                signin.setVisibility(View.VISIBLE);
           // SignoutButton.setVisibility(View.GONE);
            Toast.makeText(this,"Please Sign In",Toast.LENGTH_SHORT).show();
        }

    }

    public void SignOut()
    {
        FirebaseAuth.getInstance().signOut();
        if(mgooglesignclient==null)
            //Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
           Log.e(TAG,"hgfghgjhjkjlk");
       mgooglesignclient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });


    }




}
