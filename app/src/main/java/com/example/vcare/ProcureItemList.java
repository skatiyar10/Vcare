package com.example.vcare;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;

public class ProcureItemList extends ArrayAdapter<Packedfooddata>
{   private  int pos;
    private Activity context;
    private List<Packedfooddata> pfdlist;


    public ProcureItemList(Activity context, @NonNull List<Packedfooddata> objects)
    {
        super(context,R.layout.custom_procure,objects);
        this.context = context;
        this.pfdlist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitem=inflater.inflate(R.layout.custom_procure,null,true);
        TextView textaddress=listviewitem.findViewById(R.id.pdct_addr);
        TextView textcontact=listviewitem.findViewById(R.id.pdct_contact);
        TextView textdate=listviewitem.findViewById(R.id.pdct_date);
        TextView textname=listviewitem.findViewById(R.id.pdct_name);
      /* final  Button button=listviewitem.findViewById(R.id.btnprocure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                DatabaseReference databaseref;
                databaseref= FirebaseDatabase.getInstance().getReference("Packedfooddata");
pfdlist.get(position).setProcure("Yes");
                Log.e("himanshu",position+"");
                databaseref.setValue(pfdlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                       // Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

        Packedfooddata pdfooddata=pfdlist.get(position);
        textaddress.setText(pdfooddata.getFoodaddress());
        textcontact.setText(pdfooddata.getFoodcontact());
        textdate.setText(pdfooddata.getFoodexpiry());
        textname.setText(pdfooddata.getFoodtitle());

       /* if(pdfooddata.getProcure().equals("Yes"))
        {
            button.setEnabled(false);
        }
        else
        {
            button.setEnabled(true);
        }*/
        return listviewitem;
    }


}
