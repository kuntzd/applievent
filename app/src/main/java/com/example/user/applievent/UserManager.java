package com.example.user.applievent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 29/01/2017.
 */

public class UserManager extends Manager{

    private User cursorToUser(Cursor c) throws Exception {
        if (c.getCount() == 0)
            return null;
        if(c.getCount() > 1)
            throw new Exception("Retrieved "+c.getCount()+" users instead of 1");
        c.moveToFirst();
        User user = new User(c.getInt(UserIds.COL_ID_NUM), c.getString(UserIds.COL_PSEUDO_NUM), c.getString(UserIds.COL_MAIL_NUM), c.getString(UserIds.COL_PWD_NUM));
        c.close();
        return user;
    }

    public UserManager(Context context, String dataBaseName){
        super(context, dataBaseName);
    }

    public User insertUser(User user) throws Exception {
        ContentValues values = new ContentValues();
        if(user.getId()!=-1) throw new Exception("Impossible to insert user with existing database identifier");
        values.put(UserIds.COL_PSEUDO, user.getPseudo());
        values.put(UserIds.COL_MAIL, user.getMail());
        values.put(UserIds.COL_PWD, user.getPwd());
        long newId = bdd.insert(UserIds.TABLE_NAME, null, values);
        return new User((int)newId, user.getPseudo(), user.getMail(), user.getPwd());
    }

    public int updateUser(User user) throws Exception {
        ContentValues values = new ContentValues();
        if(user.getId()==-1) throw new Exception("Impossible to update user with non-existing database identifier");
        values.put(UserIds.COL_PSEUDO, user.getPseudo());
        values.put(UserIds.COL_MAIL, user.getMail());
        values.put(UserIds.COL_PWD, user.getPwd());
        return bdd.update(UserIds.TABLE_NAME, values, UserIds.COL_ID + " = " +user.getId(), null);
    }

    public int removeUser(User user){
        bdd.delete(UserInGroupIds.TABLE_NAME, UserInGroupIds.COL_USERID + " = " + user.getId(), null);
        return bdd.delete(UserIds.TABLE_NAME, UserIds.COL_ID + " = " + user.getId(), null);
    }

    public User getUserWithPseudo(String pseudo) throws Exception {
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[] {UserIds.COL_ID, UserIds.COL_PSEUDO, UserIds.COL_MAIL, UserIds.COL_PWD}, UserIds.COL_PSEUDO + " LIKE \"" + pseudo +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    public User getUserWithMail(String mail) throws Exception {
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[] {UserIds.COL_ID, UserIds.COL_PSEUDO, UserIds.COL_MAIL, UserIds.COL_PWD}, UserIds.COL_MAIL + " LIKE \"" + mail +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    public User getUser(int id) throws Exception {
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[] {UserIds.COL_ID, UserIds.COL_PSEUDO, UserIds.COL_MAIL, UserIds.COL_PWD}, UserIds.COL_ID + " = " + id, null, null, null, null);
        return cursorToUser(c);
    }

    public int getCount(){
        Cursor c = bdd.rawQuery("SELECT * FROM " + UserIds.TABLE_NAME, new String[]{});
        return c.getCount();
    }
}
