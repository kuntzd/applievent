package com.example.user.applievent;

/**
 * Created by user on 29/01/2017.
 */

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String mail;
    private String pwd;

    public User(int id, String firstName, String lastName, String mail, String pwd) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.pwd = pwd;
    }

    public User(String firstName, String lastName, String mail, String pwd) {
        this.id = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getPwd() {
        return pwd;
    }

    public String toString() {
        return "id : " + id + "\nFirst name : " + firstName + "\nLast name : " + lastName + "\nMail : " + mail;
    }
}
