package com.example.user.applievent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 07/12/2016.
 */

public class DatabaseOpener extends SQLiteOpenHelper {

    private static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS " + UserIds.TABLE_NAME + " ("
            + UserIds.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserIds.COL_FIRSTNAME + " TEXT NOT NULL, "
            + UserIds.COL_LASTNAME + " TEXT NOT NULL, "
            + UserIds.COL_NUMBER + " TEXT NOT NULL UNIQUE, "
            + UserIds.COL_PSEUDO + " TEXT NOT NULL UNIQUE, "
            + UserIds.COL_PWD + " TEXT NOT NULL"
            +");";

    private static final String CREATE_GROUP = "CREATE TABLE IF NOT EXISTS " + GroupIds.TABLE_NAME + " ("
            + GroupIds.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GroupIds.COL_NAME + " TEXT NOT NULL"
            +");";

    private static final String CREATE_USERINGROUP = "CREATE TABLE IF NOT EXISTS " + UserInGroupIds.TABLE_NAME + " ("
            + UserInGroupIds.COL_PSEUDO + " TEXT NOT NULL, "
            + UserInGroupIds.COL_USERID + " INTEGER NOT NULL, "
            + UserInGroupIds.COL_GROUPID + " INTEGER NOT NULL, "
            + "FOREIGN KEY("+UserInGroupIds.COL_USERID+") REFERENCES "+UserIds.TABLE_NAME+"("+UserIds.COL_ID+"), "
            + "FOREIGN KEY("+UserInGroupIds.COL_GROUPID+") REFERENCES "+GroupIds.TABLE_NAME+"("+GroupIds.COL_ID+")"
            +");";

    public DatabaseOpener(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_GROUP);
        db.execSQL(CREATE_USERINGROUP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DropTable(db);
        onCreate(db);
    }

    public void DropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + UserIds.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GroupIds.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + UserInGroupIds.TABLE_NAME + ";");
    }
}
