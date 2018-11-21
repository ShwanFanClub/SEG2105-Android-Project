package com.segwumbo.www.wumbo;

import android.support.test.annotation.UiThreadTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileEditActivityTest {

    /*
    @Rule
    public ActivityTestRule<ProfileEditActivity> mActivityTestRule = new ActivityTestRule<ProfileEditActivity>(ProfileEditActivity.class);
    private ProfileEditActivity mActivity = null;

    private Bundle infoBundle;

    */

    @Before
    public void setUp() throws Exception {

        //mActivity = mActivityTestRule.getActivity();
    }

    // Test 1: Test to see if all fields - Company Name, Phone Number, Address, About - are all filled in
    @Test
    @UiThreadTest
    public void testSuccessAllFilled() throws Exception{

        /*
        infoBundle = new Bundle();
        Bundle outerBundle = new Bundle();
        outerBundle.putBundle("bundle", infoBundle);

        infoBundle.putString("company", "Wendy's");
        infoBundle.putString("phone number", "613-737-1111");
        infoBundle.putString("address", "99 Rideau Street");

        String company = infoBundle.getString("company");
        String phone = infoBundle.getString("phone number");
        String address = infoBundle.getString("address");

        */
        assertTrue(ProfileEditActivity.checkMandatoryFieldsValid("Wendys","613-737-1111","99 Rideau Street"));
    }


    // Test 2: Test to see if 2/3 fields - Company Name, Phone Number, Address - are filled
    @Test
    @UiThreadTest
    public void testSuccessOneBlank() throws Exception{

        /*
        infoBundle = new Bundle();
        Bundle outerBundle = new Bundle();
        outerBundle.putBundle("bundle", infoBundle);

        infoBundle.putString("company", "Wendy's");
        infoBundle.putString("phone number", "");
        infoBundle.putString("address", "99 Rideau Street");

        String company = infoBundle.getString("company");
        String phone = infoBundle.getString("phone number");
        String address = infoBundle.getString("address");
        */

        assertFalse(ProfileEditActivity.checkMandatoryFieldsValid("Wendys","","99 Rideau Street"));

    }


    @After
    public void tearDown() throws Exception {
    }

}