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
            + UserIds.COL_PSEUDO + " TEXT NOT NULL, "
            + UserIds.COL_MAIL + " TEXT NOT NULL, "
            + UserIds.COL_PWD + " TEXT NOT NULL"
            +");";

    public DatabaseOpener(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DropTable(db);
        onCreate(db);
    }

    public void DropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + UserIds.TABLE_NAME + ";");
    }
}
