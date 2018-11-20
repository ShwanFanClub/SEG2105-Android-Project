package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    ArrayList<Service> servicesOffered = new ArrayList<Service>();
    DatabaseReference databaseServices;
    DatabaseReference databaseProfiles;
    DatabaseReference databaseUsers;
    RecyclerView rvServices;

    String userKey;
    UserAccount userAccount;

    Bundle infoBundle;
    String userName;
    String company;
    String phoneNumber;
    String address;
    String description;
    boolean isLicensed;
    String profileID;
    String servicesOfferedString;

    @Override
    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()){
                    if (user.getKey().equals(userKey)){
                        userAccount = user.getValue(UserAccount.class);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        populateFields();

        if (servicesOfferedString !=null && !servicesOffered.equals("")) {
            String[] servicesOfferedStrArray = servicesOfferedString.split(" ");

            for (String serviceKey : servicesOfferedStrArray) {
                retrieveServicesOffered(serviceKey);
            }

            rvServices = (RecyclerView) findViewById(R.id.providerProfileServicesRecyclerView);
            ServiceAdapter sAdapter = new ServiceAdapter(servicesOffered, new ClickListener() {
                @Override
                public void onPositionClicked(int position) {
                }
            });
            sAdapter.setAllUpdateInvisible();
            rvServices.setAdapter(sAdapter);
            rvServices.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        Bundle bundle = getIntent().getExtras();
        infoBundle = bundle.getBundle("bundle");

        userName = infoBundle.getString("username");
        userKey = infoBundle.getString("userKey");
        company = infoBundle.getString("company");
        phoneNumber = infoBundle.getString("phone number");
        address = infoBundle.getString("address");
        description = infoBundle.getString("description");
        isLicensed = infoBundle.getBoolean("isLicensed");
        profileID = infoBundle.getString("profile ID");
        servicesOfferedString = infoBundle.getString("services offered");

        populateFields();
    }

    public void populateFields(){
        // Getting and setting all text views
        TextView userNameText = findViewById(R.id.providerProfileUsername);
        userNameText.setText(userName);

        TextView companyNameText = findViewById(R.id.providerProfileCompany);
        companyNameText.setText(company);

        TextView phoneNumberText = findViewById(R.id.providerProfilePhoneNumber);
        phoneNumberText.setText(phoneNumber);

        TextView addressText = findViewById(R.id.providerProfileAddress);
        addressText.setText(address);

        TextView descriptionText = findViewById(R.id.providerProfileDescription);
        descriptionText.setText(description);

        CheckBox isLicensedCheckBox = findViewById(R.id.providerProfileIsLicensed);
        isLicensedCheckBox.setChecked(isLicensed);
    }

    public void retrieveServicesOffered(final String serviceKey){
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot service : dataSnapshot.getChildren()){
                    if (service.getKey().equals(serviceKey)){
                        servicesOffered.add(service.getValue(Service.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void OnCreateEditProfileButtonClick(View view){
        Intent editProfileIntent = new Intent(this, ProfileEditActivity.class);

        editProfileIntent.putExtra("isEdit", true);
        editProfileIntent.putExtra("bundle", infoBundle);

        startActivity(editProfileIntent);
    }
    public void onUpdate(View view){
        startActivity(new Intent(this, UpdateTime.class));
    }
}
