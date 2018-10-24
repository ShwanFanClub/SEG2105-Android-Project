package com.segwumbo.www.wumbo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class MainLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }

    public void OnCreateAccountButton(View view){

        //Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccount.class);
        //startActivityForResult(createAccountIntent, 0);
    }

    public void OnLoginButton(View view){

        EditText usernameText = findViewById(R.id.usernameEditText);
        EditText passwordText = findViewById(R.id.psswrdEditText);

        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        boolean validUser = true;

        if(validUser){

            Intent loginIntent = new Intent(this, WelcomeScreen.class);
            startActivity(loginIntent);
        }
        else{

            Toast.makeText(this, "No User Found", Toast.LENGTH_LONG).show();
            passwordText.setText("");
        }
    }
}
