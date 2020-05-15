package com.example.vcare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Procure_dialog extends Dialog implements View.OnClickListener  {
    TextView donate;
    Button donate_surplus;
    Context mActivity;

    public Procure_dialog(@NonNull Context context) {
        super(context);
        mActivity=context;
        setContentView(R.layout.activity_procure_dialog);
        donate =findViewById(R.id.donate);
        donate_surplus = findViewById(R.id.d_surplus);
        //donate_discounted = findViewById(R.id.d_discount);
        donate_surplus.setOnClickListener(this);
        //donate_discounted.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.d_surplus) {
            dismiss();
            Intent i = new Intent(getContext(),Procure_Surplus.class);
            mActivity.startActivity(i);
        }

        else
            dismiss();
    }
}
