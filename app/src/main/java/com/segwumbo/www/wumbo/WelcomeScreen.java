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

        //Get user's role from firebase
        String userRole = "Sponge";

        // Display user's name on the device
        TextView welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        welcomeUser.setText("Welcome to Wumbo, " + userName + "!");

        // Display user's role on the device
        TextView welcomeRole = (TextView) findViewById(R.id.userRole);
        welcomeRole.setText("You are logged in as : " + userRole);

    }
}
