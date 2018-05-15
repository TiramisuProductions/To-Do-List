package com.tiramisu.android.todolist.Model;



public class GoalsModel {
    public String id;
    public String name;

    public GoalsModel() {
    }

    public GoalsModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
