package com.segwumbo.www.wumbo;

import android.util.Log;
import android.widget.TextView;

import org.junit.Test;
import org.junit.Before;


import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private MainLoginActivity loginActivity;

    @Before
    public void setup(){
        loginActivity = new MainLoginActivity();
    }

    @Test
    public void bothRight(){
        TextView usernameField = loginActivity.findViewById(R.id.usernameEditText);
        usernameField.setText("admin");
        TextView passwordField = loginActivity.findViewById(R.id.passwordEditText);
        passwordField.setText("admin");
        assertTrue(loginActivity.validUser(loginActivity.findViewById(R.id.usernameEditText).toString(), loginActivity.findViewById(R.id.passwordEditText).toString()));
    }

    



}