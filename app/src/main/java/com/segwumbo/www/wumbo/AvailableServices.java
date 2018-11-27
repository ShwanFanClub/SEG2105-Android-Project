package com.segwumbo.www.wumbo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableServices extends AppCompatActivity {

    ArrayList<Service> allServices = new ArrayList<Service>();
    static ArrayList<Service> servicesToAdd = new ArrayList<>();
    DatabaseReference databaseServices;
    Button AddButton;
    RecyclerView rvServices;
    DatabaseReference databaseUsers;
    String userKey;
    UserAccount userAccount;
    Bundle infoBundle;
    String userName, company, phoneNumber, address, description, profileID;
    Boolean isLicensed;
    ArrayList<String> servicesNames;

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
        databaseServices.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (allServices != null) {

                    // getting all user account data from firebase
                    for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                        Service service = serviceSnapshot.getValue(Service.class);
                        allServices.add(service);
                    }
                    ServiceAdapter sAdapter = new ServiceAdapter( allServices, new ClickListener() {
                        @Override
                        public void onPositionClicked(int position) {}
                    }, 2);
                    rvServices.setAdapter(sAdapter);
                }
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
        setContentView(R.layout.activity_available_services);

        // gets the reference of the database
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        rvServices = findViewById(R.id.availableServices);
        AddButton = rvServices.findViewById(R.id.add_button);

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
        servicesNames = new ArrayList<>();

    }

    static void addService(Service service){

        servicesToAdd.add(service);
    }

    static void removeService(Service service){

        servicesToAdd.remove(service);
    }

    public void onClickUpdate(View view){

        for(Service s: servicesToAdd){
            servicesNames.add(s.getName());
        }

        ServiceProviderProfile sProfile = new ServiceProviderProfile(profileID, userName, address, phoneNumber, company,isLicensed, description);
        ServiceProviderProfile sP = new ServiceProviderProfile(sProfile, servicesToAdd, servicesNames);
        userAccount = new UserAccount(userAccount,sP);
        databaseUsers.child(userKey).child("profile").child("servicesOffered").setValue(servicesToAdd);
        Toast.makeText(this, "Services Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
