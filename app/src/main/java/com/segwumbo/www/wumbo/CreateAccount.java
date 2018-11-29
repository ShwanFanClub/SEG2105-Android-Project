package com.segwumbo.www.wumbo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import android.widget.Spinner;


public class CreateAccount extends AppCompatActivity {

    private final int USERNAMELENGTH = 1;
    private final int PASSWORDLENGTH = 2;
    private final String[] ENCRYPTCODE = new String[]{"0","i","ß","3","ω","f","Σ","ö","#","É"};
    private DatabaseReference database;
    private Spinner dropdown;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // database reference for the MainLoginActivity class
        database = MainLoginActivity.databaseUserAccounts;

        if(adminExist()){
            //If an admin account already exits, the option to create an admin account option is removed
            items = new String[]{"Home Owner", "Service Provider"};
        }
        else{
            items = new String[]{"Home Owner", "Service Provider", "Admin"};
        }

        setContentView(R.layout.activity_create_account);
        dropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
    // makes sure fields are of certain length
    private boolean checkFieldValidity(String email, String username, String password, String role){

        return !(username.length() < USERNAMELENGTH||password.length() < PASSWORDLENGTH||email == null);
    }

    // check to see if username already exists
    private boolean checkUsername(String username){

        // first username so must not exist
        if(MainLoginActivity.allUserAccounts == null){
            return false;
        }

        // username exists
        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(username.equals(account.getUsername())){
                return true;
            }
        }
        return false;
    }

    private boolean isEmailValid(@NonNull String email){ return email.contains("@"); }

    //Checks to see if an admin account has already been created
    private boolean adminExist(){

        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(("admin").equals(account.getRole())){
                return true;
            }
        }
        return false;
    }

    private String encrypt(String password){
        String encrypted = "";
        byte[] bytes = password.getBytes();

        for(byte b: bytes){
            Byte byteInt = b;

            encrypted += Integer.toHexString(byteInt);

        }
        for(int i = 1; i < ENCRYPTCODE.length; i++){
            encrypted = encrypted.replaceAll(String.valueOf(i), ENCRYPTCODE[i]);
        }
        return encrypted;
    }

    public void OnCreateAccountClick(View view){

        EditText usernameText = findViewById(R.id.usernameCreateText);
        EditText passwordText = findViewById(R.id.passwordCreateText);
        EditText emailText = findViewById(R.id.emailText);

        String username = usernameText.getText().toString().trim();
        String tempPassword = passwordText.getText().toString().trim();
        String role = items[dropdown.getSelectedItemPosition()].toLowerCase().trim();
        String email = emailText.getText().toString().trim();

        String password = encrypt(tempPassword);

        if(checkFieldValidity(email, username, password, role)){
            if(checkUsername(username)){
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
            }
            else if(!isEmailValid(email)){

                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            }
            else {
                // generates unique primary key of the user
                String id = database.push().getKey();
                UserAccount newUser;

                if(role.equals("admin")){

                    newUser = new UserAccount(id, email, "admin", "admin", role);
                    // stores user in database as a JSON object
                    database.child(id).setValue(newUser);

                    Toast.makeText(this, "Admin Account successfully created! Username & " +
                            "password automatically set to 'admin'", Toast.LENGTH_LONG).show();
                }
                else{
                    newUser = new UserAccount(id, email, username, password, role);

                    // stores user in database as a JSON object
                    database.child(id).setValue(newUser);

                    Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }else{
            Toast.makeText(this, "Username must be at least "+ String.valueOf(USERNAMELENGTH)+" characters long, " +
                    "Password must be at least" + String.valueOf(PASSWORDLENGTH)+ " characters long", Toast.LENGTH_LONG).show();
        }
    }
}