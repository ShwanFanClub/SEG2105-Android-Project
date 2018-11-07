package com.segwumbo.www.wumbo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainLoginActivity extends AppCompatActivity {

    // static database variables
    public static ArrayList<UserAccount> allUserAccounts = new ArrayList<UserAccount>();
    public static DatabaseReference databaseUserAccounts;

    @Override
    protected void onStart() {

        super.onStart();
        databaseUserAccounts.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(allUserAccounts != null) {

                    // getting all user account data from firebase
                    for(DataSnapshot userAccountSnapshot: dataSnapshot.getChildren()){
                        UserAccount account = userAccountSnapshot.getValue(UserAccount.class);
                        allUserAccounts.add(account);
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
        setContentView(R.layout.activity_main_login);

        // gets the reference of the database
        databaseUserAccounts = FirebaseDatabase.getInstance().getReference("users");
    }


    // on button click, goes to the create account page
    public void OnCreateAccountButton(View view){
        Intent createAccountIntent = new Intent(this, ModifyServices.class); // should be CreateAccount.class
        startActivity(createAccountIntent);
    }

    public boolean validUser(String username, String password){

        if(allUserAccounts == null){
            return false;
        }

        for(UserAccount account: allUserAccounts){

            if(account.getUsername().equals(username) &&
                    account.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    // on button click, checks to see if account is valid
    public void OnLoginButton(View view){

        EditText usernameText = findViewById(R.id.usernameEditText);
        EditText passwordText = findViewById(R.id.passwordEditText);

        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        // makes sure username and password are stored in the system
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter in a username & password", Toast.LENGTH_SHORT).show();
        }
        else if(validUser(username, password)){

            // changes to new screen
            Intent loginIntent = new Intent(this, WelcomeScreen.class);
            loginIntent.putExtra("username", username);

            usernameText.setText("");
            passwordText.setText("");

            startActivity(loginIntent);
        }
        else{//I want to get that green wall with daily commits

            Toast.makeText(this, "No Account Found", Toast.LENGTH_SHORT).show();
            passwordText.setText("");
        }
    }
}
