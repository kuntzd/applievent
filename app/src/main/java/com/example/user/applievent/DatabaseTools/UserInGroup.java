package com.example.user.applievent.DatabaseTools;

/**
 * Created by user on 13/03/2017.
 */

public class UserInGroup {
    private String pseudo;
    private int userId;
    private int groupId;

    public UserInGroup(String pseudo, int userId, int groupId) {
        this.pseudo = pseudo;
        this.userId = userId;
        this.groupId = groupId;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getUserId() {
        return userId;
    }

    public int getGroupId() {
        return groupId;
    }
}
