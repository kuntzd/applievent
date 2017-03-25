package com.example.user.applievent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 29/01/2017.
 */

public class UserManager extends Manager {

    private User cursorToUser(Cursor c) throws Exception {
        if (c.getCount() == 0)
            return null;
        if (c.getCount() > 1)
            throw new Exception("Retrieved " + c.getCount() + " users instead of 1");
        c.moveToFirst();
        User user = new User(c.getInt(UserIds.COL_ID_NUM), c.getString(UserIds.COL_FIRSTNAME_NUM), c.getString(UserIds.COL_LASTNAME_NUM), c.getString(UserIds.COL_NUMBER_NUM), c.getString(UserIds.COL_PSEUDO_NUM), c.getString(UserIds.COL_PWD_NUM));
        c.close();
        return user;
    }

    private ArrayList<User> cursorToMultipleUser(Cursor c) throws Exception {
        ArrayList<User> result = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            User user = new User(c.getInt(UserIds.COL_ID_NUM), c.getString(UserIds.COL_FIRSTNAME_NUM), c.getString(UserIds.COL_LASTNAME_NUM), c.getString(UserIds.COL_NUMBER_NUM), c.getString(UserIds.COL_PSEUDO_NUM), c.getString(UserIds.COL_PWD_NUM));
            result.add(user);
        }
        c.close();
        return result;
    }

    public UserManager(Context context, String dataBaseName) {
        super(context, dataBaseName);
    }

    public User insertUser(User user) throws Exception {
        ContentValues values = new ContentValues();
        if (user.getId() != -1)
            throw new Exception("Impossible to insert user with existing database identifier");
        values.put(UserIds.COL_FIRSTNAME, user.getFirstName());
        values.put(UserIds.COL_LASTNAME, user.getLastName());
        values.put(UserIds.COL_NUMBER, user.getNumber());
        values.put(UserIds.COL_PSEUDO, user.getPseudo());
        values.put(UserIds.COL_PWD, user.getPwd());
        long newId = bdd.insert(UserIds.TABLE_NAME, null, values);
        if (newId == -1)
            return null;
        return new User((int) newId, user.getFirstName(), user.getLastName(), user.getNumber(), user.getPseudo(), user.getPwd());
    }

    public int updateUser(User user) throws Exception {
        ContentValues values = new ContentValues();
        if (user.getId() == -1)
            throw new Exception("Impossible to update user with non-existing database identifier");
        values.put(UserIds.COL_FIRSTNAME, user.getFirstName());
        values.put(UserIds.COL_LASTNAME, user.getLastName());
        values.put(UserIds.COL_NUMBER, user.getNumber());
        values.put(UserIds.COL_PSEUDO, user.getPseudo());
        values.put(UserIds.COL_PWD, user.getPwd());
        return bdd.update(UserIds.TABLE_NAME, values, UserIds.COL_ID + " = " + user.getId(), null);
    }

    public int removeUser(User user) {
        bdd.delete(UserInGroupIds.TABLE_NAME, UserInGroupIds.COL_USERID + " = " + user.getId(), null);
        return bdd.delete(UserIds.TABLE_NAME, UserIds.COL_ID + " = " + user.getId(), null);
    }

    public ArrayList<User> getUserWithName(String searchString) throws Exception {
        ArrayList<User> result = new ArrayList<>();
        if (searchString.isEmpty())
            return result;
        String[] split = searchString.split(" ");
        if (split.length == 1) {
            String name = split[0];
            Cursor c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_FIRSTNAME + " LIKE \"" + name + "\"", null, null, null, null);
            result.addAll(cursorToMultipleUser(c));
            c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_LASTNAME + " LIKE \"" + name + "\"", null, null, null, null);
            result.addAll(cursorToMultipleUser(c));
            return result;
        }
        if (split.length > 1) {
            String firstName = split[0];
            String lastName = split[1];
            Cursor c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_FIRSTNAME + " LIKE \"" + firstName + "\" and " + UserIds.COL_LASTNAME + " LIKE \"" + lastName + "\"", null, null, null, null);
            result.addAll(cursorToMultipleUser(c));
            firstName = split[1];
            lastName = split[0];
            c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_FIRSTNAME + " LIKE \"" + firstName + "\" and " + UserIds.COL_LASTNAME + " LIKE \"" + lastName + "\"", null, null, null, null);
            result.addAll(cursorToMultipleUser(c));
            return result;
        }
        throw new Exception("Fatal error searching user " + searchString);
    }

    public User getUserWithNumber(String number) throws Exception {
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_NUMBER + " LIKE \"" + number + "\"", null, null, null, null);
        return cursorToUser(c);
    }

    public User getUserWithPseudo(String pseudo) throws Exception {
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_PSEUDO + " LIKE \"" + pseudo + "\"", null, null, null, null);
        return cursorToUser(c);
    }

    public User getUser(int id) throws Exception {
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[]{UserIds.COL_ID, UserIds.COL_FIRSTNAME, UserIds.COL_LASTNAME, UserIds.COL_NUMBER, UserIds.COL_PSEUDO, UserIds.COL_PWD}, UserIds.COL_ID + " = " + id, null, null, null, null);
        return cursorToUser(c);
    }

    public int getCount() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + UserIds.TABLE_NAME, new String[]{});
        return c.getCount();
    }
}
