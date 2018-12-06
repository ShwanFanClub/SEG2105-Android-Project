package com.segwumbo.www.wumbo;

import static org.junit.Assert.*;
import org.junit.Test;

import com.segwumbo.www.wumbo.MainLoginActivity;

public class PasswordEncryptionTest {
    @Test
    public void testBlank(){
        String password = "";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "";
        assertEquals("Test Blank Password, Expected blank", expected,encrypted);
    }

    @Test
    public void testPasswordType1(){
        String password = "123456789";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "123456789";
        assertNotEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }

    @Test
    public void testPasswordType2(){
        String password = "abcedfghi";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }

    @Test
    public void testPasswordType3(){
        String password = "!@#$%^&*()_+";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "!@#$%^&*()_+";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }

    @Test
    public void testPasswordType4(){
        String password = "ABC123!@#  D";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "ABC123!@#  D";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);

    @Test
    public void testPasswordType5(){
        String password = "123456789";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "123456789";
        assertNotEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }

    @Test
    public void testPasswordType6(){
        String password = "abcedfghi";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }

    @Test
    public void testPasswordType7(){
        String password = "!@#$%^&*()_+";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "!@#$%^&*()_+";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }

    @Test
    public void testPasswordType8(){
        String password = "ABC123!@#  D";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "ABC123!@#  D";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }
    @Test
    public void testPasswordType9(){
        String password = "ABC123!@#  D";
        String encrypted = MainLoginActivity.encrypt(password);
        String expected = "ABC123!@#  D";
        assertEquals("Test Valid Password, Expected not the same", expected,encrypted);
    }


}
