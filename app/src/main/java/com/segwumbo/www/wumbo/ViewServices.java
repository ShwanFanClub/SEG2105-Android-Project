package com.segwumbo.www.wumbo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewServices extends AppCompatActivity {

    private DatabaseReference databaseUsers;
    private DatabaseReference databaseServices;
    private Query databaseRoles;

    private RecyclerView rvServices;
    private UserAccount userAccount, homeOwnerAcc;
    private Bundle infoBundle;
    private String userKey;
    private int viewChangeIndex;


    private ArrayList<Service> allServicesView, currentServicesView, combined;

    @Override
    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()){

                    if (databaseRoles.equals(("home owner"))){
                        userAccount = user.getValue(UserAccount.class);
                        ArrayList<Service> tempService = userAccount.getProfile().getServicesOffered();
                        if(tempService != null && !tempService.isEmpty()) {
                            currentServicesView.addAll(tempService);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseServices.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                    Service service = serviceSnapshot.getValue(Service.class);

                    allServicesView.add(service);

                    }


                    ServiceAdapter sAdapter = new ServiceAdapter(allServicesView, new ClickListener() {
                        @Override
                        public void onPositionClicked(int position) { }
                    }, 5);
                    rvServices.setAdapter(sAdapter);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        rvServices.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseRoles = databaseUsers.orderByChild("role");

        allServicesView = new ArrayList<Service>();
        currentServicesView = new ArrayList<Service>();

        Bundle bundle = getIntent().getExtras();
        infoBundle = bundle.getBundle("bundle");

        rvServices = findViewById(R.id.rvServicesView);

        userKey = infoBundle.getString("userKey");


    }
}

