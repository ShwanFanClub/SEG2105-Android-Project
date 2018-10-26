package com.segwumbo.www.wumbo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Bundle bundle = getIntent().getExtras();

        //Get user's name from firebase
        String userName = bundle.getString("username");
        // Display user's name on the device
        TextView welcomeUser = findViewById(R.id.welcomeUser);
        welcomeUser.setText("Welcome to Wumbo, " + userName + "!");

        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(account.getUsername().equals(userName)){

                // Display user's role on the device
                TextView welcomeRole = findViewById(R.id.userRole);
                welcomeRole.setText("You are logged in as: " + account.getRole());
            }
        }

    }
}
