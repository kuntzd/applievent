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
 * Created by user on 13/03/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GroupDataBaseInstrumentedTest {
    @Test
    public void TestGroupManager() {
        String dbName = "DataBaseOnlyForTest";
        Context context = InstrumentationRegistry.getTargetContext();
        UserManager userManager = new UserManager(context, dbName);
        GroupManager groupManager = new GroupManager(context, dbName);
        groupManager.open();
        userManager.open();
        try {
            User user1 = new User("Prenom1", "Nom1", "mail1", "superpwd1");
            User user2 = new User("Prenom2", "Nom2", "mail2", "superpwd2");

            int count = groupManager.getCount();
            assertEquals(0, count);

            user1 = userManager.insertUser(user1);
            user2 = userManager.insertUser(user2);
            UserWithPseudo userWithPseudo1 = new UserWithPseudo("pseudoInGroup1", user1);
            UserWithPseudo userWithPseudo2 = new UserWithPseudo("pseudoInGroup2", user2);
            Group groupTest = new Group("group", userWithPseudo1, userWithPseudo2);
            groupTest = groupManager.insertGroup(groupTest);

            count = groupManager.getCount();
            if (count != 1) {
                Assert.fail("No user found in the database, expected " + groupTest + " to have been inserted into the database.");
            }

            Group groupFromDB = groupManager.getGroupFromId(groupTest.getId(), userManager);
            Assert.assertEquals(2, groupFromDB.getAllUsers().size());
            EmptyGroup updatedGroup = new EmptyGroup(groupFromDB.getId(), "ModifiedName");
            if (groupFromDB != null) {
                groupManager.updateGroup(updatedGroup);
                groupManager.removeUserInGroup(updatedGroup.getId(), user1);
                groupManager.removeUserInGroup(updatedGroup.getId(), user2);
                groupManager.addUserInGroup(updatedGroup.getId(), "newPseudo", user1);
            } else {
                Assert.fail("No user with mail " + groupTest.getName() + "found in the database, expected " + groupTest + " to have been inserted into the database.");
            }

            Group modifiedGroup = groupManager.getGroupFromId(updatedGroup.getId(), userManager);
            Assert.assertEquals(1, modifiedGroup.getAllUsers().size());
            User userFromDb = modifiedGroup.getUserByPseudo("newPseudo");
            Assert.assertEquals(userFromDb.getMail(), "mail1");
            if (modifiedGroup != null) {
                groupManager.removeGroup(modifiedGroup);
            } else {
                Assert.fail("No user with mail " + modifiedGroup.getName() + "found in the database, expected " + modifiedGroup + " to have been inserted into the database.");
            }
            Group inexistantGroup = groupManager.getGroupFromId(modifiedGroup.getId(), userManager);
            if (inexistantGroup != null) {
                Assert.fail("Found user " + inexistantGroup + " in the database, expected the user to have been removed from the database.");
            }
            count = groupManager.getCount();
            assertEquals(0, count);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Assert.fail("Test error.\nOriginal error message :" + e.getMessage() + "\nStack trace : " + sw.toString());
        } finally {
            groupManager.close();
            userManager.close();
            context.deleteDatabase(dbName);
        }
    }
}
