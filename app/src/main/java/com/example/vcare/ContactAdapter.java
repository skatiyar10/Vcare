package com.example.vcare;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
    Context context;
    public ContactAdapter(Context c) {
        super();
        context=c;

    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_restaurant,parent,false);
        return new ContactHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {

            if(position==0)
            {
                holder.nearbranch.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.nearbranch.setVisibility(View.INVISIBLE);
            }
        }
        else
        {holder.nearbranch.setVisibility(View.INVISIBLE);

        }

        holder.texttitle.setText(ContactRecycler.distance.get(position).title);
        holder.textnearaddress.setText(ContactRecycler.distance.get(position).adress);
        holder.textphone.setText(ContactRecycler.distance.get(position).phone);
    }

    @Override
    public int getItemCount() {
        return ContactRecycler.distance.size();
    }
}

