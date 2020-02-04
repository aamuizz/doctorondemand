package com.doctorondemand.doctorondemand.findmydoctor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.doctorondemand.doctorondemand.MainActivity;
import com.doctorondemand.doctorondemand.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class findmydoctor extends AppCompatActivity {
    RecyclerView mRecyclerView;
    EditText drsearch;
    ProgressBar progressBar;
    Switch showonlinedoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmydoctor);
        drsearch = findViewById(R.id.searchdrname);
        drsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loaddoctorstext(drsearch.getText().toString(),showonlinedoctors.isChecked());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(linearLayoutManager);
        showonlinedoctors = findViewById(R.id.switch1);
        progressBar = findViewById(R.id.finddoctorprogress);
        showonlinedoctors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<DrDetails> mdrdetails = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Map<String, String> drdetails = (Map<String, String>) ds.getValue();
                                if(drsearch.getText().toString().equals("")){
                                    if (drdetails.get("Status").equals("Online")) {
                                        mdrdetails.add(new DrDetails(drdetails.get("Name"), drdetails.get("Specialization"),
                                                drdetails.get("Address"), drdetails.get("Phoneno"), drdetails.get("Email"), drdetails.get("Status")));
                                    }
                                }
                                else{
                                    if (drdetails.get("Status").equals("Online")&&drdetails.get("Name").toLowerCase().contains(drsearch.getText().toString().toLowerCase())) {
                                        mdrdetails.add(new DrDetails(drdetails.get("Name"), drdetails.get("Specialization"),
                                                drdetails.get("Address"), drdetails.get("Phoneno"), drdetails.get("Email"), drdetails.get("Status")));
                                    }
                                }

                            }
                            Adapter adapter = new Adapter(findmydoctor.this, mdrdetails);
                            mRecyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    if(drsearch.getText().toString().equals("")) {
                        loaddoctors();
                    }else{
                        loaddoctorstext(drsearch.getText().toString(),b);
                    }
                }
            }

        });
        loaddoctors();
     }
     public void loaddoctors(){
         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 List<DrDetails>mdrdetails = new ArrayList<>();
                 for(DataSnapshot ds: dataSnapshot.getChildren()){
                     Map<String,String>drdetails = (Map<String,String>)ds.getValue();
                     mdrdetails.add(new DrDetails(drdetails.get("Name"), drdetails.get("Specialization"),
                             drdetails.get("Address"), drdetails.get("Phoneno"), drdetails.get("Email"), drdetails.get("Status")));

                 }
                 Adapter adapter = new Adapter(findmydoctor.this,mdrdetails);
                 mRecyclerView.setAdapter(adapter);
                 progressBar.setVisibility(View.GONE);
                 mRecyclerView.setVisibility(View.VISIBLE);

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

     }
     public void loaddoctorstext(final String name, final boolean onlinedoctor){
         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 List<DrDetails>mdrdetails = new ArrayList<>();
                 for(DataSnapshot ds: dataSnapshot.getChildren()){
                     Map<String,String>drdetails = (Map<String,String>)ds.getValue();
                     if(!onlinedoctor) {
                         if (drdetails.get("Name").toLowerCase().contains(name.toLowerCase())) {
                             mdrdetails.add(new DrDetails(drdetails.get("Name"), drdetails.get("Specialization"),
                                     drdetails.get("Address"), drdetails.get("Phoneno"), drdetails.get("Email"), drdetails.get("Status")));
                         }
                     }
                     else{
                         if (drdetails.get("Status").equals("Online")&&drdetails.get("Name").toLowerCase().contains(drsearch.getText().toString().toLowerCase())) {
                             mdrdetails.add(new DrDetails(drdetails.get("Name"), drdetails.get("Specialization"),
                                     drdetails.get("Address"), drdetails.get("Phoneno"), drdetails.get("Email"), drdetails.get("Status")));
                         }
                     }
                 }
                 Adapter adapter = new Adapter(findmydoctor.this,mdrdetails);
                 mRecyclerView.setAdapter(adapter);
                 progressBar.setVisibility(View.GONE);
                 mRecyclerView.setVisibility(View.VISIBLE);

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
     }
}
