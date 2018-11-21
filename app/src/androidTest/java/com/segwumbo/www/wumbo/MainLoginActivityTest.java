package com.segwumbo.www.wumbo;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MainLoginActivityTest {

    @Rule
    public ActivityTestRule<MainLoginActivity> mActivityTestRule = new ActivityTestRule<MainLoginActivity>(MainLoginActivity.class);
    private MainLoginActivity mActivity = null;

    private TextView textUser;
    private TextView textPassword;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

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

        String expectedUser = "rufus";
        String expectedPass = "snoopy";

        assertTrue((expectedUser.equals(name)) && (expectedPass.equals(password)));

    }

    // Test 2: Test to see if both Username and Password are blank
    @Test
    @UiThreadTest
    public void testFailBothBlank() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.usernameEditText));
        textUser = mActivity.findViewById(R.id.usernameEditText);
        textUser.setText("");
        String name = textUser.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.passwordEditText));
        textPassword = mActivity.findViewById(R.id.passwordEditText);
        textPassword.setText("");
        String password = textPassword.getText().toString();

        String expectedUser = "";
        String expectedPass = "";

        assertTrue((expectedUser.equals(name)) && (expectedPass.equals(password)));
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

        String expectedUser = "rufus";
        String expectedPass = "snoopy";

        assertEquals(expectedUser, name);
        assertNotEquals(expectedPass, password);
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

        String expectedUser = "rufus";
        String expectedPass = "snoopy";

        assertNotEquals(expectedUser, name);
        assertEquals(expectedPass, password);
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

        String expectedUser = "rufus";
        String expectedPass = "snoopy";

        assertFalse((expectedUser.equals(name)) && (expectedPass.equals(password)));
    }

    @After
    public void tearDown() throws Exception {
    }
}