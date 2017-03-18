package com.example.user.applievent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 04/03/2017.
 */

public class GroupManager extends Manager {

    private static EmptyGroup cursorToEmptyGroup(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        int id = c.getInt(GroupIds.COL_ID_NUM);
        String groupName = c.getString(GroupIds.COL_NAME_NUM);
        c.close();
        return new EmptyGroup(id, groupName);
    }

    private static List<UserInGroup> cursorToUsersInGroup(Cursor c) {
        if (c.getCount() == 0)
            return null;
        ArrayList<UserInGroup> result = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            UserInGroup userInGroup = new UserInGroup(c.getString(UserInGroupIds.COL_PSEUDO_NUM), c.getInt(UserInGroupIds.COL_USERID_NUM), c.getInt(UserInGroupIds.COL_GROUPID_NUM));
            result.add(userInGroup);
        }
        c.close();
        return result;
    }

    protected GroupManager(Context context, String dataBaseName) {
        super(context, dataBaseName);
    }

    public long addUserInGroup(long groupId, String pseudo, User user) {
        ContentValues values = new ContentValues();
        values.put(UserInGroupIds.COL_GROUPID, groupId);
        values.put(UserInGroupIds.COL_PSEUDO, pseudo);
        values.put(UserInGroupIds.COL_USERID, user.getId());
        return bdd.insert(UserInGroupIds.TABLE_NAME, null, values);
    }

    public Group getGroupFromId(int id, UserManager userManager) throws Exception {
        Cursor c = bdd.query(GroupIds.TABLE_NAME, new String[]{GroupIds.COL_ID, GroupIds.COL_NAME}, GroupIds.COL_ID + " = " + id, null, null, null, null);
        EmptyGroup emptyGroup = cursorToEmptyGroup(c);
        if(emptyGroup == null)
            return null;
        c = bdd.query(UserInGroupIds.TABLE_NAME, new String[]{UserInGroupIds.COL_USERID, UserInGroupIds.COL_GROUPID, UserInGroupIds.COL_PSEUDO}, UserInGroupIds.COL_GROUPID + " =  " + emptyGroup.getId(), null, null, null, null);
        List<UserInGroup> userInGroups = cursorToUsersInGroup(c);
        List<UserWithPseudo> users = new ArrayList<>();
        for (UserInGroup userInGroup: userInGroups) {
            users.add(new UserWithPseudo(userInGroup.getPseudo(),userManager.getUser(userInGroup.getUserId())));
        }
        return Group.BuildFromEmptyGroup(emptyGroup, users);
    }

    public Group insertGroup(Group group) throws Exception {
        ContentValues values = new ContentValues();
        if (group.getId() != -1)
            throw new Exception("Impossible to insert user with existing database identifier");
        values.put(GroupIds.COL_NAME, group.getName());
        long groupId = bdd.insert(GroupIds.TABLE_NAME, null, values);
        HashMap<String, User> users = group.getAllUsers();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            String pseudo = entry.getKey();
            User user = entry.getValue();
            addUserInGroup(groupId, pseudo, user);
        }
        return new Group((int)groupId, group.getName(), group.getAllUsers());
    }

    public int removeUserInGroup(long groupId, User user) {
        return bdd.delete(UserInGroupIds.TABLE_NAME, UserInGroupIds.COL_GROUPID + " = " + groupId + " AND " + UserInGroupIds.COL_USERID + " = " + user.getId(), null);
    }

    public long updateGroup(EmptyGroup group) throws Exception {
        ContentValues values = new ContentValues();
        if (group.getId() == -1)
            throw new Exception("Impossible to insert user with existing database identifier");
        values.put(GroupIds.COL_NAME, group.getName());
        return bdd.update(GroupIds.TABLE_NAME, values, GroupIds.COL_ID + "=" + group.getId(), null);
    }

    public int removeGroup(Group group) throws Exception {
        bdd.delete(GroupIds.TABLE_NAME, GroupIds.COL_ID + " = " + group.getId(), null);
        bdd.delete(UserInGroupIds.TABLE_NAME, UserInGroupIds.COL_GROUPID + " = " + group.getId(), null);
        return 0;
    }

    public int getCount(){
        Cursor c = bdd.rawQuery("SELECT * FROM " + GroupIds.TABLE_NAME, new String[]{});
        return c.getCount();
    }
}
