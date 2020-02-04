package com.doctorondemand.doctorondemand.findmydoctor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doctorondemand.doctorondemand.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<DrViewHolder> {
    private List<DrDetails>mdrdetails;
    public Context mcontext;
    public Adapter(Context mcontext,List<DrDetails>mdrdetails){
        this.mdrdetails=mdrdetails;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public DrViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewfinddoctor
        ,viewGroup,false);
        return new DrViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DrViewHolder drViewHolder, int i) {
        drViewHolder.tvdrname.setText(mdrdetails.get(i).getDrname());
        drViewHolder.tvspecialization.setText(mdrdetails.get(i).getSpecialization());
        drViewHolder.tvemail.setText(mdrdetails.get(i).getEmail());
        drViewHolder.tvphoneno.setText(mdrdetails.get(i).getPhoneno());
        drViewHolder.tvaddress.setText(mdrdetails.get(i).getAddress());
        drViewHolder.status.setText(mdrdetails.get(i).getStatus());
        if(mdrdetails.get(i).getStatus().equals("Offline")){
            drViewHolder.onlineicon.setBackgroundResource(R.drawable.ic_brightness_red_black_24dp);
            drViewHolder.status.setTextColor(Color.RED);
        }
        else{
            drViewHolder.onlineicon.setBackgroundResource(R.drawable.ic_brightness_1_black_24dp);
            drViewHolder.status.setTextColor(Color.GREEN);

        }
        drViewHolder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(mcontext, DoctorProfile.class);
                mintent.putExtra("Name",drViewHolder.tvdrname.getText().toString());
                mintent.putExtra("Specialization",drViewHolder.tvspecialization.getText().toString());
                mintent.putExtra("Address",drViewHolder.tvaddress.getText().toString());
                mintent.putExtra("Email",drViewHolder.tvemail.getText().toString());
                mintent.putExtra("Phoneno",drViewHolder.tvphoneno.getText().toString());
                mintent.putExtra("Status",drViewHolder.status.getText().toString());
                mcontext.startActivity(mintent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdrdetails.size();
    }
}
