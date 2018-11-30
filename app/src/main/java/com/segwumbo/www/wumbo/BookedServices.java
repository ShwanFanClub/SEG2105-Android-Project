package com.segwumbo.www.wumbo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookedServices extends AppCompatActivity {

    /*

    private static DatabaseReference databaseUsers;
    private DatabaseReference databaseServices;
    private Query databaseRoles;

    private RecyclerView rvServicesBooked;
    private UserAccount userAccount;
    private Bundle infoBundle;
    private static String userKey;

    private ArrayList<Service> allServicesView, currentServicesView;
    private static ArrayList<Service> servicesBooked;

    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {

                    if (databaseRoles.equals(("home owner"))) {
                        userAccount = user.getValue(UserAccount.class);
                        ArrayList<Service> tempService = userAccount.getHOProfile().getServicesBooked();
                        if (tempService != null && !tempService.isEmpty()) {
                            currentServicesView.addAll(tempService);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseUsers.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if (user.getKey().equals(userKey)) {

                        userAccount = user.getValue(UserAccount.class);

                    }
                }

                ServiceAdapter sAdapter = new ServiceAdapter(allServicesView, new ClickListener() {
                    @Override
                    public void onPositionClicked(int position) {
                    }
                }, 3);
                rvServicesBooked.setAdapter(sAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        rvServicesBooked.setLayoutManager(new LinearLayoutManager(this));

    } */

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booked_services);

            /*

            databaseServices = FirebaseDatabase.getInstance().getReference("services");
            databaseUsers = FirebaseDatabase.getInstance().getReference("users");
            databaseRoles = databaseUsers.orderByChild("role");

            allServicesView = new ArrayList<Service>();
            currentServicesView = new ArrayList<Service>();

            Bundle bundle = getIntent().getExtras();
            infoBundle = bundle.getBundle("bundle");

            rvServicesBooked = findViewById(R.id.rvBooked);

            userKey = infoBundle.getString("userKey");
            */
        }

    }
