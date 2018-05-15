package com.tiramisu.android.todolist.Model;


public class UserUID  {

    String email;
    String uid;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserUID()
    {

    }


    public UserUID(String email, String UID, String username) {
        this.email = email;
        this.uid = UID;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return uid;
    }

    public void setUID(String UID) {
        this.uid = UID;
    }
}
