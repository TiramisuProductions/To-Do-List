package com.tiramisu.android.todolist.Model;

/**
 * Created by Sarvesh Palav on 11/29/2017.
 */

public class UserModel {
    String email;
    String name;
    String uid;
    String userName;
    String gender;



    public UserModel() {
    }

    public String getUserName() {
        return userName;
    }

    public UserModel(String email, String name, String uid, String userName, String gender) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.userName = userName;
        this.gender = gender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
