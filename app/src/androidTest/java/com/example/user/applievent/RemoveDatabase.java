package com.example.user.applievent;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by user on 13/03/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RemoveDatabase {
    @Test
    public void RemoveDB() {
        Context context = InstrumentationRegistry.getTargetContext();
        String[] databaseList = context.databaseList();
        for (int i = 0; i < databaseList.length; i++)
            context.deleteDatabase(databaseList[i]);
    }
}
