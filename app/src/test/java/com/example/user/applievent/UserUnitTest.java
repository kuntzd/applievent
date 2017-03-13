package com.example.user.applievent;

import android.test.mock.MockContext;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by user on 03/02/2017.
 */

public class UserUnitTest {
    @Test
    public void TestOpenDatabase(){
        UserManager userManager = new UserManager(new MockContext(), "UnitTestDataBase");
        userManager.open();
        userManager.close();
    }
}
