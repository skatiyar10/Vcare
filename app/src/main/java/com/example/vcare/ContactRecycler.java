package com.example.vcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class ContactRecycler extends AppCompatActivity  {
    RecyclerView recycler;
    Toolbar toolbar;
    Location loc;
    ContactAdapter adapter;
    static ArrayList<Distance> distance=null;
    TextView branch;
    private FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView.LayoutManager manager;
    Context c;
    ConstraintLayout layout;

    public ContactRecycler() {
        loc = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactrecycler);
        c=this;
        layout=findViewById(R.id.layout);
        recycler=findViewById(R.id.contactrecycler);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Restaurants");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));
        //progress=findViewById(R.id.progress);
        manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        branch=findViewById(R.id.ourbranch);
        branch.setVisibility(View.GONE);
        distance=new ArrayList<>();

        distance.add(new Distance("Qila Patiala","Panchwati Cir,Raja Park, Jaipur\n" +
                "Pincode: 302004\n" +
                "09783597835",26.9061597, 75.8122381,"09783597835" ));
        distance.add(new Distance("Jaipur Adda","4-D Villa, Khasa Kothi Circle,Station Rd, Jaipur,\n" +
                //"near Ahinsa Circle,C-Scheme, Jaipur\n" +
                "Pincode: 302001\n" +
                //"+91-0141-4032221\n" +
                "8003099217",26.9170585, 75.7933359,"8003099217"));
        distance.add(new Distance("The Great Kabab Factory","Radisson Jaipur City Center,MI Road, Khasa Kothi Circle,Jaipur\n" +
                "Pincode: 302001\n" +
                //"+91-0141-4002221\n" +
                "09828506663",26.9170585, 75.7886711, "09828506663"));
        distance.add(new Distance("RJ 14","plot no 132, 133, 151, Ajmer Rd,Opposite Indus Ind Bank, Shanti Nagar,DCM, Jaipur\n"+
                "Pincode: 302019\n" +

                "9887125555",26.8875742 ,75.7568437,"9887125555"));
        // toolbar.setTitleTextColor(getResources().getColor(R.color.status_welcome));


        branch.setVisibility(View.VISIBLE);
        adapter=new ContactAdapter(c);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {


                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},148);

            }
            else
            {
                checknearby();
            }

        }
        else
        {
            checknearby();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==148)
        {if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            checknearby();
        }
    }

    public void checknearby()
    {
        getLocation();
        adapter=new ContactAdapter(c);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

    }


    public void getAll(Location l)
    {float maxdis=3000000;int pos=-1;
        ArrayList<Distance> mycontact=new ArrayList<>();
        Location branch=new Location("");
        for(int i=0;i<distance.size();i++)
        {
            branch.setLatitude(distance.get(i).lat);

            branch.setLongitude(distance.get(i).lon);

            if(maxdis>=(l.distanceTo(branch)/1000))
            {
                maxdis=(l.distanceTo(branch)/1000);

                pos=i;
            }

        }

        mycontact.add(distance.get(pos));
        distance.set(pos,distance.get(0));
        distance.set(0,mycontact.get(0));
        updateui();
    }
    public void buildGpsMessage()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
    public void getLocation()
    {

        final LocationManager locationManager=(LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            buildGpsMessage();
        }

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {


            fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
            LocationRequest locationRequest= LocationRequest.create();
            locationRequest.setFastestInterval(2000);
            locationRequest.setInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location!=null)
                                {
                                    Log.e("location ",location.getLatitude()+"   "+location.getLongitude());
                                    getAll(location);



                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }

            });

            Task<LocationSettingsResponse> locationSettingsResponseTask = task.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {

                            // Show the dialog by calling startResolutionForResult(),
                            //
                        } catch (Exception sendEx) {
                            // Ignore the error.
                        }
                    }
                }
            });
        }
    }

    public void updateui()
    {
        adapter=new ContactAdapter(c);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }
}
class Distance
{
    public String adress,title,phone;
    public double lat,lon;

    public Distance(String title,String adress,double lat,double lon,String phone)
    {   this.title=title;
        this.adress=adress;
        this.lat=lat;
        this.lon=lon;
        this.phone=phone;
    }
}

