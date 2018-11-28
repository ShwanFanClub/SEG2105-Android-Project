package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeCustomer extends AppCompatActivity {

    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_customer);

        Bundle bundle = getIntent().getExtras();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        //Get user's name from firebase
        String userName = bundle.getString("username");
        // Display user's name on the device
        TextView welcomeHomeowner = findViewById(R.id.welcomeHomeowner);

        welcomeHomeowner.setText("Welcome to Wumbo, " + userName + "!");

        // Get user's role from firebase
        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(account.getUsername().equals(userName)){

                // Display user's role on the device
                TextView welcomeRole = findViewById(R.id.homeownerRole);

                welcomeRole.setText("You are logged in as: " + account.getRole());
            } // End if
        } // End for

    }

    public void OnServiceAvailableButtonClick(View view){
        Intent createNewServiceIntent = new Intent(this, ViewServices.class);
        startActivity(createNewServiceIntent);
    }

    public void OnServiceBookedButtonClick(View view){
        Intent createNewServiceIntent = new Intent(this, BookedServices.class);
        startActivity(createNewServiceIntent);
    }

}
