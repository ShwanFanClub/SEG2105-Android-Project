package com.segwumbo.www.wumbo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

    //Checks to see if an admin account has already been created
    private boolean adminExist(String role){

        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(role.equals(account.getRole())){
                return true;
            }
        }
        return false;
    }

    // click event for the create account button
    public void onCreateAccountClick(View view){

        EditText usernameText = findViewById(R.id.usernameCreateText);
        EditText passwordText = findViewById(R.id.passwordCreateText);
        EditText roleText = findViewById(R.id.roleCreateText);

        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String role = roleText.getText().toString().toLowerCase().trim();

        if(checkFieldValidity(username, password, role)){

            if(role.equals("admin") && adminExist(role)){
                Toast.makeText(this, "Admin account already exists!", Toast.LENGTH_LONG).show();
            }
            else if(!checkUsername(username)){
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
            }
            else {
                // generates unique primary key of the user
                String id = database.push().getKey();
                UserAccount newUser = new UserAccount(id, username, password, role);
                // stores user in database as a JSON object
                database.child(id).setValue(newUser);

                Toast.makeText(this, "Account successfully created!", Toast.LENGTH_LONG).show();
                finish();
            }
        }else{
            Toast.makeText(this, "Username must be "+ String.valueOf(usernameLength)+" characters long, " +
                    "Password must be " + String.valueOf(passwordLength)+ " characters long", Toast.LENGTH_LONG).show();
        }
    }
}
