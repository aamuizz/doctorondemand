package com.doctorondemand.doctorondemand.findmydoctor;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctorondemand.doctorondemand.R;

class DrViewHolder extends RecyclerView.ViewHolder {
    TextView tvdrname,tvspecialization,tvaddress,tvemail,tvphoneno,status,onlineicon;
    RelativeLayout mlayout;

    public DrViewHolder(@NonNull View itemView) {
        super(itemView);
        tvdrname = (TextView)itemView.findViewById(R.id.tvdrname);
        tvspecialization=(TextView)itemView.findViewById(R.id.tvspecialization);
        tvaddress = (TextView)itemView.findViewById(R.id.tvaddress);
        tvemail = itemView.findViewById(R.id.tvemail);
        tvphoneno = itemView.findViewById(R.id.tvphoneno);
        status = itemView.findViewById(R.id.statuschecker);
        onlineicon= itemView.findViewById(R.id.onlineicon);

        mlayout = itemView.findViewById(R.id.rlayout);
    }
}
