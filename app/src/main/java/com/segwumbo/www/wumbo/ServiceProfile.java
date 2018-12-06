package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ServiceProfile extends AppCompatActivity {

    private DatabaseReference databaseUsers;
    private DatabaseReference databaseServices;
    private Query databaseRole, databaseSelService;

    private ArrayList<Service> servicesOffered, bookedServices;
    private Service serviceToAdd;

    private UserAccount userAccount;
    private String userKey, service, username, address, phoneNumber, description, serviceKey;

    private Button bookService;
    private TextView serviceNameText, companyNameText, phoneNumberText, descriptionText, addressText;
    private static Boolean disabled = false;

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()){
                    userAccount = user.getValue(UserAccount.class);

                    if(userAccount.getUsername().equals(username)){
                        address = userAccount.getProfile().getAddress();
                        phoneNumber = userAccount.getProfile().getPhoneNumber();
                        description = userAccount.getProfile().getDescription();
                        populateFields(service, username, address, phoneNumber, description);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profile);

        // gets the reference of the database
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseRole = databaseUsers.orderByChild("role");
        databaseSelService = databaseServices.orderByChild("name");
        bookedServices = new ArrayList<>();

        serviceNameText = findViewById(R.id.service);
        companyNameText = findViewById(R.id.companyName);
        phoneNumberText = findViewById(R.id.phoneNumber);
        addressText = findViewById(R.id.address);
        descriptionText = findViewById(R.id.description);

        Bundle bundle = getIntent().getExtras();
        Bundle infoBundle = bundle.getBundle("bundle");

        username = infoBundle.getString("username");
        service = infoBundle.getString("service name");
    }

    public void populateFields(String service, String username, String address, String phoneNumber, String description){

        // Getting and setting all text views

        serviceNameText.setText(service);
        companyNameText.setText(username);
        phoneNumberText.setText(phoneNumber);
        addressText.setText(address);
        descriptionText.setText(description);

    }

    // Look at Available Services
    public void OnBookButtonClick(View view){

        /*
        Intent viewTimeIntent = new Intent(this, PickTime.class);

        Bundle bundle = new Bundle();
        viewTimeIntent.putExtra("bundle", bundle);

        startActivity(viewTimeIntent);




       // bookService.setEnabled(false);

        bookedServices.add(serviceToAdd);
        databaseUsers.child(userKey).child("booked services").setValue(bookedServices);

*/

        //databaseUsers.child(userKey).setValue(u.get(0));
        //bookedServices.add(serviceToAdd);
        //databaseUsers.child(userKey).child("booked services").setValue(bookedServices);
        Toast.makeText(view.getContext(), "You have booked this service!",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void OnRateButtonClick(View view){
        Intent viewServiceIntent = new Intent(this, RateUs.class);
        viewServiceIntent.putExtra("service name", service);
        startActivity(viewServiceIntent);
    }
}

