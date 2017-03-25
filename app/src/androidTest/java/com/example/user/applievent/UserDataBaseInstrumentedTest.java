package com.example.user.applievent;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.user.applievent.DatabaseInterface.User;
import com.example.user.applievent.DatabaseInterface.UserManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

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
            User userTest = new User("Prenom", "Nom", "0615842367", "pseudo", "superpwd");

            int count = userManager.getCount();
            assertEquals(0, count);

            userTest = userManager.insertUser(userTest);
            count = userManager.getCount();
            if (count != 1) {
                Assert.fail("No user found in the database, expected " + userTest + " to have been inserted into the database.");
            }

            User userFromDB = userManager.getUserWithNumber(userTest.getNumber());
            User updatedUser = new User(userFromDB.getId(), "ModifiedPrenom", "ModifiedNom", "0615842368", "ModifiedPseudo", userFromDB.getPwd());
            if (userFromDB != null) {
                userManager.updateUser(updatedUser);
            } else {
                Assert.fail("No user with number " + userTest.getNumber() + "found in the database, expected " + userTest + " to have been inserted into the database.");
            }

            User newUser = new User("ModifiedPrenom", "ModifiedNom", "0615842368", "uselessPseudo", "pwd");
            newUser = userManager.insertUser(newUser);
            if (newUser != null)
                Assert.fail("New user shouldn't have been add with already existant number " + newUser.getNumber());
            newUser = new User("ModifiedPrenom", "ModifiedNom", "0606060606", "ModifiedPseudo", "pwd");
            newUser = userManager.insertUser(newUser);
            if (newUser != null)
                Assert.fail("New user shouldn't have been add with already existant pseudo " + newUser.getPseudo());
            newUser = new User("ModifiedPrenom", "ModifiedNom", "0615842369", "NewPseudo","pwd");

            User invertedUser = new User("ModifiedNom", "ModifiedPrenom", "0615842370", "NewPseudo2", "pwd");
            userManager.insertUser(newUser);
            userManager.insertUser(invertedUser);


            ArrayList<User> modifiedUsers = userManager.getUserWithName(updatedUser.getFirstName() + " " + updatedUser.getLastName());
            if (modifiedUsers.size() != 3)
                Assert.fail("Expected 3 users with \"" + updatedUser.getFirstName() + " " + updatedUser.getLastName() + "\" research but got " + modifiedUsers.size());
            User modifiedUser = null;
            for (int i = 0; i < modifiedUsers.size(); i++) {
                User temporaryUser = modifiedUsers.get(i);
                if (temporaryUser.getPseudo().equalsIgnoreCase(updatedUser.getPseudo())) {
                    modifiedUser = temporaryUser;
                    break;
                }
            }

            if (modifiedUser != null) {
                userManager.removeUser(modifiedUser);
            } else {
                Assert.fail("No user with pseudo " + updatedUser.getPseudo() + "found in the database, expected " + updatedUser + " to have been inserted into the database.");
            }

            ArrayList<User> allLeftUsers = userManager.getUserWithName("ModifiedNom ModifiedPrenom l");
            if (allLeftUsers.size() != 2)
                Assert.fail("Expected 2 users with \"ModifiedNom ModifiedPrenom l\" research but got " + allLeftUsers.size());

            User inexistantUser = userManager.getUserWithPseudo(updatedUser.getPseudo());
            if (inexistantUser != null) {
                Assert.fail("Found user " + inexistantUser + " in the database, expected the user to have been removed from the database.");
            }

            allLeftUsers = userManager.getUserWithName("ModifiedNom");
            if (allLeftUsers.size() != 2)
                Assert.fail("Expected 2 users with \"ModifiedNom\" research but got " + allLeftUsers.size());
            allLeftUsers = userManager.getUserWithName("ModifiedPrenom");
            if (allLeftUsers.size() != 2)
                Assert.fail("Expected 2 users with \"ModifiedPrenom\" research but got " + allLeftUsers.size());

            for (int i = 0; i < allLeftUsers.size(); i++) {
                userManager.removeUser(allLeftUsers.get(i));
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
