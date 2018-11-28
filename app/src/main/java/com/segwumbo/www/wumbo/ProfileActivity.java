package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference databaseServices;
    private static DatabaseReference databaseUsers;
    private static RecyclerView rvServices;

    private UserAccount userAccount;

    private static Bundle infoBundle;
    private String userName, company, phoneNumber, address, description;
    private static String userKey;
    private boolean isLicensed;
    private static ArrayList<Service> servicesOffered;
    private ArrayList<TimeAvailable> Days;
    private TextView timeSlot, userNameText, companyNameText, phoneNumberText, descriptionText, addressText;
    private CheckBox isLicensedCheckBox;

    @Override
    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if (user.getKey().equals(userKey)) {
                        userAccount = user.getValue(UserAccount.class);
                        Days = userAccount.getProfile().sDays;
                        String temp = "";
                        if (Days != null && !Days.isEmpty()) {
                            for (int i = 0; i < Days.size(); i++) {
                                temp += Days.get(i).day + ":\t\t" + Days.get(i).startHour + ":" + Days.get(i).startMin + " to " + Days.get(i).endHour + ":" + Days.get(i).endMin + "\n";
                            }
                        }
                        timeSlot.setText(temp);

                        servicesOffered = userAccount.getProfile().getServicesOffered();

                        if (servicesOffered != null && !servicesOffered.isEmpty()) {
                            rvServices.setVisibility(View.VISIBLE);

                            ServiceAdapter sAdapter = new ServiceAdapter(servicesOffered, new ClickListener() {
                                @Override
                                public void onPositionClicked(int position) {
                                }
                            }, 3);
                            sAdapter.setAllUpdateInvisible();
                            rvServices.setAdapter(sAdapter);
                        }

                        //Update variable values
                        company = userAccount.getProfile().getCompanyName();
                        phoneNumber = userAccount.getProfile().getPhoneNumber();
                        address = userAccount.getProfile().getAddress();
                        description = userAccount.getProfile().getDescription();
                        isLicensed = userAccount.getProfile().isLicensed();

                        //Update information text-boxes
                        companyNameText.setText(company);
                        phoneNumberText.setText(userAccount.getProfile().getPhoneNumber());
                        addressText.setText(userAccount.getProfile().getAddress());
                        descriptionText.setText(userAccount.getProfile().getDescription());
                        isLicensedCheckBox.setChecked(userAccount.getProfile().isLicensed());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        rvServices.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userNameText = findViewById(R.id.providerProfileUsername);
        companyNameText = findViewById(R.id.providerProfileCompany);
        phoneNumberText = findViewById(R.id.providerProfilePhoneNumber);
        addressText = findViewById(R.id.providerProfileAddress);
        descriptionText = findViewById(R.id.providerProfileDescription);
        isLicensedCheckBox = findViewById(R.id.providerProfileIsLicensed);

        databaseServices = FirebaseDatabase.getInstance().getReference("services");
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
        rvServices = findViewById(R.id.providerProfileServicesRecyclerView);
        timeSlot = findViewById(R.id.timeslots);
        populateFields(userName, company, address, phoneNumber, description, isLicensed);
    }

    private void populateFields(String username, String company, String address, String phoneNumber, String description, boolean licensed){
        // Getting and setting all text views
        userNameText.setText(username);
        companyNameText.setText(company);
        phoneNumberText.setText(phoneNumber);
        addressText.setText(address);
        descriptionText.setText(description);
        isLicensedCheckBox.setChecked(licensed);
    }

    public void OnCreateEditProfileButtonClick(View view){
        Intent editProfileIntent = new Intent(this, ProfileEditActivity.class);

        infoBundle.putString("company", company);
        infoBundle.putString("phone number", phoneNumber);
        infoBundle.putString("address", address);
        infoBundle.putString("description", description);
        infoBundle.putBoolean("isLicensed", isLicensed);

        editProfileIntent.putExtra("isEdit", true);
        editProfileIntent.putExtra("bundle", infoBundle);

        startActivity(editProfileIntent);
    }

    public void onUpdate(View view){
        Intent updateTimeIntent = new Intent(this, UpdateTime.class);
        updateTimeIntent.putExtra("bundle",infoBundle);
        startActivity(updateTimeIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onStart();
    }

    public void onEditServicesClick(View view){
        Intent addServices = new Intent(this, AvailableServices.class);
        addServices.putExtra("bundle",infoBundle);
        startActivity(addServices);
    }

    public static void removeService(Service service, int position){
        servicesOffered.remove(service);
        databaseUsers.child(userKey).child("profile").child("servicesOffered").setValue(servicesOffered);
        if(servicesOffered.size() == 0){
            rvServices.setVisibility(View.GONE);
        }
    }
}
