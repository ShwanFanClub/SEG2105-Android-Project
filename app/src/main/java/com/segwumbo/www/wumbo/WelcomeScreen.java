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

        //Get user's name from firebase
        String userName = "Spongebob";

        //Get user's role from firebase
        String userRole = "Sponge";

        //Get user's username from firebase
        String userUsername = "sponge123";

        // Display user's name on the device
        TextView welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        welcomeUser.setText("Welcome to Wumbo, " + userName + "!");

        // Display user's role on the device
        TextView welcomeRole = (TextView) findViewById(R.id.userRole);
        welcomeRole.setText("Your role is: " + userRole);

        // Display user's username on device
        TextView welcomeUsername = (TextView) findViewById(R.id.userUsername);
        welcomeUsername.setText("Your username is: " + userUsername);


    }
}
