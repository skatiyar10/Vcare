package com.example.vcare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class donate_dialog extends Dialog implements View.OnClickListener {
    TextView donate;
    Button donate_surplus;
    Context mActivity;

    public donate_dialog(@NonNull Context context) {
        super(context);
        mActivity=context;
        setContentView(R.layout.activity_donate_dialog);
        donate =findViewById(R.id.donate);
        donate_surplus = findViewById(R.id.d_surplus);
        //donate_discounted = findViewById(R.id.d_discount);
        donate_surplus.setOnClickListener(this);
       // donate_discounted.setOnClickListener(this);

    }

   /* public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_donate_dialog, container, false);
        donate = v.findViewById(R.id.donate);
        donate_surplus = v.findViewById(R.id.d_surplus);
        donate_discounted = v.findViewById(R.id.d_discount);
        donate_surplus.setOnClickListener(this);
        donate_discounted.setOnClickListener(this);
        return v;
    }*/
    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.d_surplus) {
            dismiss();
            Intent i = new Intent(getContext(),PackedFood.class);
            mActivity.startActivity(i);
        }
        else
            dismiss();
    }
}
