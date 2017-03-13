package com.example.user.applievent;

import java.util.Collection;
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

    public static Group BuildFromEmptyGroup(EmptyGroup emptyGroup, Collection<UserWithPseudo> users){
        HashMap<String , User> group = new HashMap<>();
        for(UserWithPseudo user : users){
            group.put(user.getPseudo(), user.getUser());
        }
        return new Group(emptyGroup.getId(), emptyGroup.getName(), group);
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
