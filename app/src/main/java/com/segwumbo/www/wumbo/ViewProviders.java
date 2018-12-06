package com.segwumbo.www.wumbo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProviders extends AppCompatActivity {

    private static DatabaseReference databaseUsers;

    private RecyclerView rvUsers;
    private Bundle infoBundle;
    private TextView nameOfService;
    private String nameOfServiceString;
    private String userKey, companyName;
    private static String serviceName;
    private ArrayList<UserAccount> serviceProviders;

    @Override
    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceProviders.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                    UserAccount user = userSnapshot.getValue(UserAccount.class);

                    if(user.getRole().equals("service provider")){
                        ArrayList<Service> tempService = new ArrayList<>();
                        if(user.getProfile() != null){

                            if(user.getProfile().getServicesOffered() != null){
                                tempService = user.getProfile().getServicesOffered();
                            }
                        }
                        if(tempService != null && !tempService.isEmpty()) {
                            for(Service s: tempService){
                                if(s.getName().equals(nameOfServiceString)){
                                    serviceProviders.add(user);
                                }
                            }
                        }
                    }

                    ProviderAdapter sAdapter = new ProviderAdapter(serviceProviders, new ClickListener() {
                        @Override
                        public void onPositionClicked(int position) {}
                    }, nameOfServiceString);
                    rvUsers.setAdapter(sAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_provider);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        nameOfService = findViewById(R.id.NameOfService);
        Bundle bundle = getIntent().getExtras();
        infoBundle = bundle.getBundle("bundle");

        nameOfServiceString = infoBundle.getString("name");
        nameOfService.setText(nameOfServiceString);
        rvUsers = findViewById(R.id.rvUsersView);
        serviceProviders = new ArrayList<>();

        userKey = infoBundle.getString("userKey");
    }
}

