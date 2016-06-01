package com.ahmedfahmi.gravity.managers;

/**
 * Created by Ahmed Fahmi on 5/30/2016.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    private String mobile;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public User(String email, String password, String mobile, String lastName, String firstName) {
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
