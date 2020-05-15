package com.example.vcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PackedFood extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    EditText productname;
    EditText productdesc;
    TextView productexpiry;
    EditText productMRP;
    EditText productdisMRP;
    EditText productcontact;
    EditText productaddress;
    Toolbar toolbar;

    Packedfooddata packedfooddata;
    DatabaseReference databaseref;
    Button donate;

    DatePickerDialog datePickerDialog;
    private GoogleMap mMap;
    String addr="";
    Location locate;
    private FusedLocationProviderClient fusedLocationClient;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packed_food);
        productname=findViewById(R.id.pdtname);
        productaddress=findViewById(R.id.pdtaddress);
        productcontact=findViewById(R.id.pdtcontact);
        productMRP=findViewById(R.id.pdtMRP);
        productdisMRP=findViewById(R.id.pdtDisMRP);
        productdesc=findViewById(R.id.pdtdescription);
        productexpiry=findViewById(R.id.pdtexp);
        donate=findViewById(R.id.fooddonate);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Donate Food");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.Mycolor));

        databaseref= FirebaseDatabase.getInstance().getReference("Packedfooddata");


        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String pname=productname.getText().toString().trim();
                String pdesc=productdesc.getText().toString().trim();
                String pexpiry=productexpiry.getText().toString().trim();
                String pcontact=productcontact.getText().toString().trim();
                String paddress=productaddress.getText().toString().trim();
                if(TextUtils.isEmpty(pname)||TextUtils.isEmpty(pdesc)||TextUtils.isEmpty(pexpiry)||TextUtils.isEmpty(pcontact)||TextUtils.isEmpty(paddress))
                {
                    Toast.makeText(getApplicationContext(),"Product Details can't be empty ",Toast.LENGTH_SHORT).show();
                }
                else {
                    packedfooddata = new Packedfooddata(pname, pdesc, pexpiry, pcontact, paddress, "No");
                    // databaseReference.push().setValue("packedfooddata");
                    databaseref.push().setValue(packedfooddata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Toast.makeText(getApplicationContext(), "Thanks for your Contribution", Toast.LENGTH_SHORT).show();
                            productname.setText(" ");
                            productdesc.setText(" ");
                            //productexpiry.setText(day+"-"+(month+1)+"-"+year);
                            productcontact.setText(" ");
                            // productaddress.setText(" ");
                            productMRP.setText(" ");
                            productdisMRP.setText(" ");
                        }
                    });

                }

            }
        });

        productexpiry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(PackedFood.this,new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
                    {
                        productexpiry.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
                Toast.makeText(getApplicationContext(),(day+"-"+(month+1)+"-"+year),Toast.LENGTH_SHORT).show();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        askpermission();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings=mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        // Add a marker in Sydney, Australia, and move the camera.

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try
        {
            addresses = geocoder.getFromLocation(locate.getLatitude(), locate.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            addr=addr+ addresses.get(0).getAddressLine(0);// If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            Log.e("address",addr);
            productaddress.setText(addr);
            LatLng sydney = new LatLng(locate.getLatitude(), locate.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locate.getLatitude(), locate.getLongitude()), 17.0f));
        }
        catch (Exception e)
        {

        }


    }

    @Override
    public boolean onMyLocationButtonClick() {


        return false;
    }

    public void askpermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    locate=location;
                                    mapFragment.getMapAsync(PackedFood.this);
                                    // Logic to handle location object
                                }
                            }
                        });

            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101)
        {
            if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                mapFragment.getMapAsync(this);
            }
            else
            {
                askpermission();
            }
        }
    }
}
