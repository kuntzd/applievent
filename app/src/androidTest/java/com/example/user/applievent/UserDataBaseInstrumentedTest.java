package com.example.user.applievent;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.Assert.assertEquals;

/**
 * Created by user on 05/02/2017.
 */
@RunWith(AndroidJUnit4.class)
public class UserDataBaseInstrumentedTest {
    @Test
    public void TestUserManager() {
        String dbName = "DataBaseOnlyForTest";
        Context context = InstrumentationRegistry.getTargetContext();
        UserManager userManager = new UserManager(context, dbName);
        userManager.open();
        try {
            User userTest = new User("pseudo", "mail", "superpwd");

            int count = userManager.getCount();
            assertEquals(0, count);

            userTest = userManager.insertUser(userTest);
            count = userManager.getCount();
            if (count != 1) {
                Assert.fail("No user found in the database, expected " + userTest + " to have been inserted into the database.");
            }

            User userFromDB = userManager.getUserWithMail(userTest.getMail());
            User updatedUser = new User(userFromDB.getId(), "ModifiedPseudo", "ModifiedMail", userFromDB.getPwd());
            if (userFromDB != null) {
                userManager.updateUser(updatedUser);
            } else {
                Assert.fail("No user with mail " + userTest.getMail() + "found in the database, expected " + userTest + " to have been inserted into the database.");
            }

            User modifiedUser = userManager.getUserWithPseudo(updatedUser.getPseudo());
            if (modifiedUser != null) {
                userManager.removeUser(modifiedUser);
            } else {
                Assert.fail("No user with mail " + updatedUser.getPseudo() + "found in the database, expected " + updatedUser + " to have been inserted into the database.");
            }
            User inexistantUser = userManager.getUserWithMail(updatedUser.getMail());
            if (inexistantUser != null) {
                Assert.fail("Found user " + inexistantUser + " in the database, expected the user to have been removed from the database.");
            }
            count = userManager.getCount();
            assertEquals(0, count);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Assert.fail("Test error.\nOriginal error message :" + e.getMessage() + "\nStack trace : " + sw.toString());
        } finally {
            userManager.close();
            context.deleteDatabase(dbName);
        }
    }
}
