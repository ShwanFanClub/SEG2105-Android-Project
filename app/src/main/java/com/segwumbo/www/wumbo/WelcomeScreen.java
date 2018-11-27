package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeScreen extends AppCompatActivity {
    private DatabaseReference databaseUsers;
    private int numberOfUsers;
    private UserAccount userAccount;
    private TextView userList;

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String temp = "";
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    numberOfUsers++;
                    userAccount = user.getValue(UserAccount.class);

                    assert userAccount != null;
                    temp += numberOfUsers + ". " + userAccount.getUsername() + "\n";
                }
                userList.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Bundle bundle = getIntent().getExtras();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        numberOfUsers = 0;

        //Get user's name from firebase
        String userName = bundle.getString("username");
        // Display user's name on the device
        TextView welcomeUser = findViewById(R.id.welcomeUser);
        TextView userListTitle = findViewById(R.id.userLists);
        userList = findViewById(R.id.registeredUsers);
        Button createService = findViewById(R.id.serviceCreate);

        createService.setVisibility(View.GONE);
        userListTitle.setVisibility(View.GONE);
        userList.setVisibility(View.GONE);
        welcomeUser.setText("Welcome to Wumbo, " + userName + "!");

        if(userName.equals("admin")){
            createService.setVisibility(View.VISIBLE);
            userListTitle.setVisibility(View.VISIBLE);
            userList.setVisibility(View.VISIBLE);
        }

        for(UserAccount account: MainLoginActivity.allUserAccounts){
            if(account.getUsername().equals(userName)){

                // Display user's role on the device
                TextView welcomeRole = findViewById(R.id.userRole);

                welcomeRole.setText("You are logged in as: " + account.getRole());
            }
        }

    }

    public void OnCreateServiceButtonClick(View view){
        Intent createNewServiceIntent = new Intent(this, ModifyServices.class);
        startActivity(createNewServiceIntent);
    }
}
