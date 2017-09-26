package com.tiramisu.android.todolist.Model;

/**
 * Created by Sarvesh Palav on 23-09-2017.
 */

public class CategoryModel {

    String name;
    String id;
    String items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CategoryModel(String id,String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
