package com.segwumbo.www.wumbo;

import android.support.annotation.NonNull;
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
    private final String[] ENCRYPTCODE = new String[]{"0","i","ß","3","ω","f","Σ","ö","#","É"};
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
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

    @Override
    protected void onRestart(){
        super.onRestart();
        databaseUserAccounts.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allUserAccounts.clear();

                // getting all user account data from firebase
                for(DataSnapshot userAccountSnapshot: dataSnapshot.getChildren()){
                    UserAccount account = userAccountSnapshot.getValue(UserAccount.class);
                    allUserAccounts.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    // on button click, goes to the create account page
    public void OnCreateAccountButton(View view){
        Intent createAccountIntent = new Intent(this, SearchService.class);
        startActivity(createAccountIntent);
    }

    public UserAccount getUser(String username, String password){
        String encryptedPassword;
        if(allUserAccounts == null){ return null; }

        for(UserAccount account: allUserAccounts){
            encryptedPassword = encrypt(password);

            if(account.getUsername().equals(username) &&
                    account.getPassword().equals(encryptedPassword)){
                return account;
            }
        }
        return null;
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

        UserAccount user = getUser(username, password);
        if(user!=null){

            System.out.print(user.getRole());
            //if user is a service provider, brings up provider profile screen
            if (user.getRole().equals("service provider")){

                Intent loginIntent;
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("userKey", user.getId());
                boolean isEdit = (user.getProfile() == null);
                //Check if user already has a profile
                if (!isEdit) {
                    loginIntent = new Intent(this, ProfileActivity.class);

                    bundle.putString("company", user.getProfile().getCompanyName());
                    bundle.putString("phone number", user.getProfile().getPhoneNumber());
                    bundle.putString("address", user.getProfile().getAddress());
                    bundle.putString("description", user.getProfile().getDescription());
                    bundle.putBoolean("isLicensed", user.getProfile().isLicensed());
                }else{
                    loginIntent = new Intent(this, ProfileEditActivity.class);

                    bundle.putBoolean( "isEdit", isEdit);
                }

                loginIntent.putExtra("bundle", bundle);

                usernameText.setText("");
                passwordText.setText("");

                startActivity(loginIntent);
            }

            // If user is a customer, switch to home owner screen
            if (user.getRole().equals("home owner")) {

                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("userKey", user.getId());

                Intent loginIntent = new Intent(this, WelcomeCustomer.class);
                loginIntent.putExtra("bundle", bundle);
                loginIntent.putExtra("username",username);

                usernameText.setText("");
                passwordText.setText("");

                startActivity(loginIntent);

            }

            if (user.getRole().equals("admin")){
                // Changes to admin screen
                Intent loginIntent = new Intent(this, WelcomeAdmin.class);
                loginIntent.putExtra("username", username);

                usernameText.setText("");
                passwordText.setText("");

                startActivity(loginIntent);
            }
        }
        else{
            Toast.makeText(this, "No Account Found", Toast.LENGTH_SHORT).show();
            passwordText.setText("");
        }
    }
}