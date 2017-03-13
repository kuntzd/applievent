package com.example.user.applievent;

/**
 * Created by user on 13/03/2017.
 */

public class UserWithPseudo {
    private String pseudo;
    private User user;

    public UserWithPseudo(String pseudo, User user) {
        this.pseudo = pseudo;
        this.user = user;
    }

    public String getPseudo() {
        return pseudo;
    }

    public User getUser() {
        return user;
    }
}
