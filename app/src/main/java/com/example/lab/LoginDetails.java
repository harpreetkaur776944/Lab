package com.example.lab;

public class LoginDetails {
    String Name;
    String Username;
    String Password;

    public LoginDetails(String name, String username, String password) {
        Name = name;
        Username = username;
        Password = password;
    }

    public LoginDetails() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
