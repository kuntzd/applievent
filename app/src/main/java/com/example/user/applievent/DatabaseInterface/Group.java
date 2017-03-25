package com.example.user.applievent.DatabaseInterface;

import com.example.user.applievent.DatabaseTools.UserWithPseudo;

import java.util.HashMap;

/**
 * Created by user on 25/02/2017.
 */

public class Group {
    private int id;
    private String name;
    private HashMap<String, User> group;

    public Group(int id, String name, HashMap<String, User> group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }
    public Group(String name, UserWithPseudo...users) {
        this.id = -1;
        this.name = name;
        group = new HashMap<>();
        for(UserWithPseudo user : users){
            group.put(user.getPseudo(), user.getUser());
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, User> getAllUsers(){ return group;}

    public User getUserByPseudo(String pseudo){
        return group.get(pseudo);
    }

}
