package com.rutax.auth;

public class User {
    private final String username;
    private String password;
    private String name;

    public User(String _username, String _password, String _name){
        this.username=_username;
        this.password=_password;
        this.name=_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setName(String name) {
        this.name = name;
    }
}
