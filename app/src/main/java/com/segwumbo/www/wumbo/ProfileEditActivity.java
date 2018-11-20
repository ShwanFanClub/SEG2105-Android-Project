package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEditActivity extends AppCompatActivity {

    DatabaseReference databaseProfile;
    DatabaseReference databaseUsers;
    boolean isEdit;

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

        TextView userNameText = findViewById(R.id.providerProfileUsername);
        userNameText.setText(userName);

        if (isEdit){
            populateFields();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseProfile = FirebaseDatabase.getInstance().getReference("profiles");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        Bundle bundle = getIntent().getExtras();
        infoBundle = bundle.getBundle("bundle");

        userName = infoBundle.getString("username");
        userKey = infoBundle.getString("userKey");

        if(isEdit) {
            company = infoBundle.getString("company");
            phoneNumber = infoBundle.getString("phone number");
            address = infoBundle.getString("address");
            description = infoBundle.getString("description");
            isLicensed = infoBundle.getBoolean("isLicensed");
            profileID = infoBundle.getString("profile ID");
        }
    }

    public void OnCreateSaveProfileClick(View view) {

        EditText companyText = findViewById(R.id.editTextProviderCompany);
        EditText phoneNumberText = findViewById(R.id.editTextProviderPhone);
        EditText addressText = findViewById(R.id.editTextProviderAddress);
        EditText descriptionText = findViewById(R.id.editTextProviderDescription);
        TextView userNameText = findViewById(R.id.providerProfileUsername);


        boolean isLicensed = ((CheckBox)findViewById(R.id.isLicensed)).isChecked();
        String company = companyText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();
        String address = addressText.getText().toString().trim();
        String description = descriptionText.getText().toString();
        String userName = userNameText.getText().toString();

        infoBundle.clear();

        infoBundle.putString("username", userName);
        infoBundle.putString("userKey", userKey);
        infoBundle.putString("company", company);
        infoBundle.putString("phone number", phoneNumber);
        infoBundle.putString("address", address);
        infoBundle.putString("description", description);
        infoBundle.putBoolean("isLicensed", isLicensed);

        if (checkMandatoryFieldsValid(company, phoneNumber, address)) {

            if(!isEdit){
                // generates unique primary key of the user
                String id = databaseProfile.push().getKey();

                infoBundle.putString("profile ID", id);

                ServiceProviderProfile newProfile = new ServiceProviderProfile(id, userName, address, phoneNumber, company, isLicensed, description);
                databaseProfile.child(id).setValue(newProfile);

                UserAccount userAccountWithProfile = new UserAccount(userAccount, newProfile);
                databaseUsers.child(userKey).setValue(userAccountWithProfile);

                Toast.makeText(this, "Profile Created!", Toast.LENGTH_SHORT).show();
            }else{
                ServiceProviderProfile newProfile = new ServiceProviderProfile(profileID, userName, address, phoneNumber, company, isLicensed, description);
                databaseProfile.child(profileID).setValue(newProfile);

                UserAccount userAccountWithProfile = new UserAccount(userAccount, newProfile);
                databaseUsers.child(userKey).setValue(userAccountWithProfile);

                Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
            }

            Intent saveProfileIntent = new Intent(this, ProfileActivity.class);
            saveProfileIntent.putExtra("bundle", infoBundle);

            startActivity(saveProfileIntent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Required Field Have Not Been Completed", Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkMandatoryFieldsValid(String company, String phoneNumber, String address){
        return !(company.equals("") || phoneNumber.equals("") || address.equals(""));
    }

    public void populateFields(){
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

}
