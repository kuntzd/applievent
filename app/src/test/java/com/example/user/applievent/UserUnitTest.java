package com.example.user.applievent;

import android.test.mock.MockContext;

import com.example.user.applievent.DatabaseInterface.UserManager;

import org.junit.Test;


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
