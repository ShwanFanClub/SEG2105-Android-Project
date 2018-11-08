package com.segwumbo.www.wumbo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ModifyServices extends AppCompatActivity {

    // static database variables
    public static ArrayList<Service> allServices = new ArrayList<Service>();
    public static DatabaseReference databaseServices;

    @Override
    protected void onStart() {

        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (allServices != null) {

                    // getting all user account data from firebase
                    for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                        Service service = serviceSnapshot.getValue(Service.class);
                        allServices.add(service);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        // gets the reference of the database
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
    }


    // determines if fields are valid
    private boolean checkFieldValidity(String serviceName, String hourlyRate) {

        // makes sure the service isnt blank
        if (serviceName == null || serviceName.length() == 0) {
            return false;
        }

        // check to see if hourlyRate is valid type double
        try {
            double hourlyRateConvert = Double.parseDouble(hourlyRate);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    // checks to see if service already exists
    private boolean checkServiceName(String serviceName) {

        // checks to see if service exists
        if (allServices != null) {
            // username exists
            for (Service service : allServices) {

                if (serviceName.equals(service.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void OnCreateServiceClick(View view) {

        EditText serviceNameText = findViewById(R.id.serviceNameText);
        EditText hourlyRateText = findViewById(R.id.hourlyRateText);

        String serviceName = serviceNameText.getText().toString().trim();
        String hourlyRate = hourlyRateText.getText().toString();

        if (checkFieldValidity(serviceName, hourlyRate)) {

            // checks if service already exists
            if (checkServiceName(serviceName)) {
                Toast.makeText(this, "Service already exists!", Toast.LENGTH_SHORT).show();
            } else {
                // generates unique primary key of the user
                String id = databaseServices.push().getKey();
                Service newService;

                newService = new Service(id, serviceName, Double.parseDouble(hourlyRate));

                // stores user in database as a JSON object
                databaseServices.child(id).setValue(newService);

                Toast.makeText(this, "Service successfully created!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        else
        {
            Toast.makeText(this, "Service or hourly rate are not valid", Toast.LENGTH_LONG).show();
        }
    }
}
