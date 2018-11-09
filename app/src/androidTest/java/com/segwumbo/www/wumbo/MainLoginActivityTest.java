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

    //Check User name

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

    @After
    public void tearDown() throws Exception {
    }
}