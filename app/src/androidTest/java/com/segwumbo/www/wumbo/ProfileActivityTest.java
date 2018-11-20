package com.segwumbo.www.wumbo;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<MainLoginActivity> mActivityTestRule = new ActivityTestRule<MainLoginActivity>(MainLoginActivity.class);
    private MainLoginActivity mActivity = null;

    private TextView textPhone;
    private TextView textAddress;
    private TextView textAbout;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    // Test 1: Test to see if all fields - Phone Number, Address, About - are blank
    @Test
    @UiThreadTest
    public void testFailBothBlank() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.providerProfilePhoneNumber));
        textPhone = mActivity.findViewById(R.id.providerProfilePhoneNumber);
        textPhone.setText("");
        String phone = textPhone.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.providerProfileAddress));
        textAddress = mActivity.findViewById(R.id.providerProfileAddress);
        textAddress.setText("");
        String address = textAddress.getText().toString();

        assertNotNull(mActivity.findViewById(R.id.providerProfileDescription));
        textAbout = mActivity.findViewById(R.id.providerProfileDescription);
        textAbout.setText("");
        String about = textAddress.getText().toString();

        // assertSame(mActivity.validUser(phone, address, about), false);

    }

    @After
    public void tearDown() throws Exception {
    }
}
