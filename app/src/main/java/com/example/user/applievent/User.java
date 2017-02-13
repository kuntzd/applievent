package com.example.user.applievent;

/**
 * Created by user on 29/01/2017.
 */

public class User {
    private int id;
    private String pseudo;
    private String mail;
    private String pwd;

    public User(int id, String pseudo, String mail, String pwd){
        this.id = id;
        this.pseudo = pseudo;
        this.mail = mail;
        this.pwd = pwd;
    }

    public User(String pseudo, String mail, String pwd){
        this.id = -1;
        this.pseudo = pseudo;
        this.mail = mail;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMail() {
        return mail;
    }

    public String getPwd() {
        return pwd;
    }

    public String toString(){
        return "id : "+id+"\npseudo : "+pseudo+"\nMail : "+mail;
    }
}
