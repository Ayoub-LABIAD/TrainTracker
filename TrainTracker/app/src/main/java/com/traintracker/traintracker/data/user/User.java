package com.traintracker.traintracker.data.user;

public class User {
    private String username;
    private String type;
    private String password;

    public User(String username, String type, String password) {
        this.username = username;
        this.type = type;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
