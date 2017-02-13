package com.example.user.applievent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 29/01/2017.
 */

public class UserManager {

    private SQLiteDatabase bdd;
    private DatabaseOpener maBaseSQLite;


    private User cursorToUser(Cursor c){
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        User user = new User(c.getInt(UserIds.COL_ID_NUM), c.getString(UserIds.COL_PSEUDO_NUM), c.getString(UserIds.COL_MAIL_NUM), c.getString(UserIds.COL_PWD_NUM));
        c.close();
        return user;
    }

    public UserManager(Context context, String dataBaseName){
        maBaseSQLite = new DatabaseOpener(context, dataBaseName, null, 1);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertUser(User user) throws Exception {
        ContentValues values = new ContentValues();
        if(user.getId()!=-1) throw new Exception("Impossible to insert user with existing database identifier");
        values.put(UserIds.COL_PSEUDO, user.getPseudo());
        values.put(UserIds.COL_MAIL, user.getMail());
        values.put(UserIds.COL_PWD, user.getPwd());
        return bdd.insert(UserIds.TABLE_NAME, null, values);
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
        return bdd.delete(UserIds.TABLE_NAME, UserIds.COL_ID + " = " +user.getId(), null);
    }

    public User getUserWithPseudo(String pseudo){
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[] {UserIds.COL_ID, UserIds.COL_PSEUDO, UserIds.COL_MAIL, UserIds.COL_PWD}, UserIds.COL_PSEUDO + " LIKE \"" + pseudo +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    public User getUserWithMail(String mail){
        Cursor c = bdd.query(UserIds.TABLE_NAME, new String[] {UserIds.COL_ID, UserIds.COL_PSEUDO, UserIds.COL_MAIL, UserIds.COL_PWD}, UserIds.COL_MAIL + " LIKE \"" + mail +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    public int getCount(){
        Cursor c = bdd.rawQuery("SELECT * FROM " + UserIds.TABLE_NAME, new String[]{});
        return c.getCount();
    }

    public User getFirstUser(){
        Cursor c = bdd.rawQuery("SELECT * FROM " + UserIds.TABLE_NAME, new String[]{});
        return cursorToUser(c);
    }

    public void RemoveTable(){
        maBaseSQLite.DropTable(bdd);
    }
}
