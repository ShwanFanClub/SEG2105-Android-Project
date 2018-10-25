package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;


public class CreateAccount extends AppCompatActivity {
    private final int usernameLength = 6;
    private final int passwordLength = 8;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        // database reference for the MainLoginActivity class
        database = MainLoginActivity.database;
    }

    // makes sure fields are of certain length
    private boolean checkFieldValidity(String username, String password, String role){

        if(username.length() < usernameLength ||
                password.length() < passwordLength ||
                role.length() == 0){
            return false;
        } else{
            return true;
        }
    }

    // check to see if username already exists
    private boolean checkUsername(String username){

        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(username.equals(account.getUsername())){
                return false;
            }
        }
        return true;
    }

    // lets user go back after creating an account
    public void OnBackButton(View view){

        Intent createAccountIntent = new Intent(this, MainLoginActivity.class);
        startActivity(createAccountIntent);
    }

    // click event for the create account button
    // not added to the create account button yet cuz it DOESNT SHOW UP
    public void onCreateAccountClick(){

        EditText usernameText = findViewById(R.id.usernameCreateText);
        EditText passwordText = findViewById(R.id.passwordCreateText);
        EditText roleText = findViewById(R.id.roleCreateText);

        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String role = roleText.getText().toString().trim();

        if(checkFieldValidity(username, password, role)){
            if(!checkUsername(username)){
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
            }
            else {
                // generates unique primary key of the user
                String id = database.push().getKey();
                UserAccount newUser = new UserAccount(id, username, password, role);
                // stores user in database as a JSON object
                database.child(id).setValue(newUser);

                Toast.makeText(this, "Account sucessfully created!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Username must be "+ String.valueOf(usernameLength)+" characters long, " +
                    "Password must be " + String.valueOf(passwordLength)+ " characters long", Toast.LENGTH_LONG).show();
        }
    }
}
