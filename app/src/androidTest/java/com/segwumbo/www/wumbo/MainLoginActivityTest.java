package com.segwumbo.www.wumbo;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainLoginActivityTest {

    @Rule
    public ActivityTestRule<MainLoginActivity> mActivityTestRule = new ActivityTestRule<MainLoginActivity>(MainLoginActivity.class);
    private MainLoginActivity mActivity = null;

    private static ArrayList<UserAccount> allUserAccounts = new ArrayList<UserAccount>();

    private TextView textUser;
    private TextView textPassword;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        UserAccount acc1 = new UserAccount("abcde/", "hello.world@gmail.com", "rufus", "snoopy", "sponge");
        MainLoginActivity.allUserAccounts.add(acc1);

    }

    // Test 1: Test to see if both Username and Password are right
    @Test
    @UiThreadTest
    public void testSuccessBothRight() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.usernameEditText));
        textUser = mActivity.findViewById(R.id.usernameEditText);
        textUser.setText("rufus");
        String name = textUser.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.passwordEditText));
        textPassword = mActivity.findViewById(R.id.passwordEditText);
        textPassword.setText("snoopy");
        String password = textPassword.getText().toString();

        assertSame(mActivity.validUser(name, password), true);
    }

    // Test 2: Test to see if both Username and Password are blank
    @Test
    @UiThreadTest
    public void testFailBothBlank()throws Exception{
        assertNotNull(mActivity.findViewById(R.id.usernameEditText));
        textUser = mActivity.findViewById(R.id.usernameEditText);
        textUser.setText("");
        String name = textUser.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.passwordEditText));
        textPassword = mActivity.findViewById(R.id.passwordEditText);
        textPassword.setText("");
        String password = textPassword.getText().toString();

        assertSame(mActivity.validUser(name, password), false);
    }

    // Test 3: Test to see if Username is right, and Password is wrong
    @Test
    @UiThreadTest
    public void testFailRightUsernameWrongPassword() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.usernameEditText));
        textUser = mActivity.findViewById(R.id.usernameEditText);
        textUser.setText("rufus");
        String name = textUser.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.passwordEditText));
        textPassword = mActivity.findViewById(R.id.passwordEditText);
        textPassword.setText("fido");
        String password = textPassword.getText().toString();

        assertSame(mActivity.validUser(name, password), false);
    }

    // Test 4: Test to see if Username is wrong, and Password is right
    @Test
    @UiThreadTest
    public void testFailWrongUsernameRightPassword() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.usernameEditText));
        textUser = mActivity.findViewById(R.id.usernameEditText);
        textUser.setText("goofy");
        String name = textUser.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.passwordEditText));
        textPassword = mActivity.findViewById(R.id.passwordEditText);
        textPassword.setText("snoopy");
        String password = textPassword.getText().toString();

        assertSame(mActivity.validUser(name, password), false);
    }

    // Test 5: Test to see if both Username and Password are wrong
    @Test
    @UiThreadTest
    public void testFailWrongUsernameWrongPassword() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.usernameEditText));
        textUser = mActivity.findViewById(R.id.usernameEditText);
        textUser.setText("scooby");
        String name = textUser.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.passwordEditText));
        textPassword = mActivity.findViewById(R.id.passwordEditText);
        textPassword.setText("scrappy");
        String password = textPassword.getText().toString();

        assertSame(mActivity.validUser(name, password), false);
    }

    @After
    public void tearDown() throws Exception {
    }
}