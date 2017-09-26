package com.tiramisu.android.todolist.Model;


public class Details {

    private String id;        //Database unique user id
    private String name;      //Full name of the user
    private String username;  //Unique username
    private String gender;


    public Details(){}

    public Details(String id, String name, String username, String gender) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
