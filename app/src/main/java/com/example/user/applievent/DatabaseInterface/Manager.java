package com.example.user.applievent.DatabaseInterface;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.applievent.DatabaseTools.DatabaseOpener;

/**
 * Created by user on 04/03/2017.
 */

public abstract class Manager {

    protected SQLiteDatabase bdd;
    protected DatabaseOpener maBaseSQLite;

    protected Manager(Context context, String dataBaseName) {
        maBaseSQLite = new DatabaseOpener(context, dataBaseName, null, 1);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }
    public void close(){
        bdd.close();
    }
}

