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

    private DatabaseReference databaseUsers;
    private boolean isEdit, isLicensed;

    private String userKey, company, userName, phoneNumber, address, description;
    private UserAccount userAccount;
    private Bundle infoBundle;

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
                System.out.println("The read failed: " + databaseError.getCode());
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

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        Bundle bundle = getIntent().getExtras();
        infoBundle = bundle.getBundle("bundle");

        userName = infoBundle.getString("username");
        userKey = infoBundle.getString("userKey");
        isEdit = bundle.getBoolean("isEdit");

        if(isEdit) {
            company = infoBundle.getString("company");
            phoneNumber = infoBundle.getString("phone number").replaceAll("-", "");
            address = infoBundle.getString("address");
            description = infoBundle.getString("description");
            isLicensed = infoBundle.getBoolean("isLicensed");
            populateFields();
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
        String address = addressText.getText().toString().trim();
        String description = descriptionText.getText().toString();
        String userName = userNameText.getText().toString();
        String phoneNumber = phoneNumberText.getText().toString().trim();

        int phoneNumberLength = phoneNumber.length();

        switch(phoneNumberLength){
            case 10: phoneNumber = phoneNumber.substring(0,3) + "-"
                    + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6);
                    break;

            case 11: phoneNumber = phoneNumber.substring(0,1) + "-" + phoneNumber.substring(1,4) + "-"
                    + phoneNumber.substring(4,7) + "-" + phoneNumber.substring(7);
                    break;

            case 12: phoneNumber = phoneNumber.substring(0,2) + "-" + phoneNumber.substring(2,5) + "-"
                    + phoneNumber.substring(5,8) + "-" + phoneNumber.substring(8);
                    break;

            case 13: phoneNumber = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3,6) + "-"
                    + phoneNumber.substring(6,9) + "-" + phoneNumber.substring(9);
                    break;
        }

        infoBundle.clear();

        infoBundle.putString("username", userName);
        infoBundle.putString("userKey", userKey);
        infoBundle.putString("company", company);
        infoBundle.putString("phone number", phoneNumber);
        infoBundle.putString("address", address);
        infoBundle.putString("description", description);
        infoBundle.putBoolean("isLicensed", isLicensed);

        if (checkMandatoryFieldsValid(company, phoneNumber, address) && phoneNumber.replaceAll("-","").length() >= 10) {
            if(!isEdit){
                // generates unique primary key of the user

                ServiceProviderProfile newProfile = new ServiceProviderProfile(userName, address, phoneNumber, company, isLicensed, description);

                UserAccount userAccountWithProfile = new UserAccount(userAccount, newProfile);
                databaseUsers.child(userKey).setValue(userAccountWithProfile);

                Toast.makeText(this, "Profile Created!", Toast.LENGTH_SHORT).show();

                Intent saveProfileIntent = new Intent(this, ProfileActivity.class);
                saveProfileIntent.putExtra("bundle", infoBundle);

                startActivity(saveProfileIntent);
            }
            else{
                databaseUsers.child(userKey).child("profile").child("address").setValue(address);
                databaseUsers.child(userKey).child("profile").child("companyName").setValue(company);
                databaseUsers.child(userKey).child("profile").child("description").setValue(description);
                databaseUsers.child(userKey).child("profile").child("licensed").setValue(isLicensed);
                databaseUsers.child(userKey).child("profile").child("phoneNumber").setValue(phoneNumber);
                Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        else if(phoneNumber.replaceAll("-","").length() < 10){
            Toast.makeText(this, "Phone number must be at least 10 digits long", Toast.LENGTH_LONG).show();
        }
        else { Toast.makeText(this, "Required Field Have Not Been Completed", Toast.LENGTH_LONG).show(); }
    }

    public static boolean checkMandatoryFieldsValid(String company, String phoneNumber, String address){
        return !(company.equals("") || phoneNumber.equals("") || address.equals(""));
    }

    public void populateFields(){
        EditText companyNameText = findViewById(R.id.editTextProviderCompany);
        companyNameText.setText(company);

        EditText phoneNumberText = findViewById(R.id.editTextProviderPhone);
        phoneNumberText.setText(phoneNumber);

        EditText addressText = findViewById(R.id.editTextProviderAddress);
        addressText.setText(address);

        EditText descriptionText = findViewById(R.id.editTextProviderDescription);
        descriptionText.setText(description);

        CheckBox isLicensedCheckBox = findViewById(R.id.isLicensed);
        isLicensedCheckBox.setChecked(isLicensed);
    }
}