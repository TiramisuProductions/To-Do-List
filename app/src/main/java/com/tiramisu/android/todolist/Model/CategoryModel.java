package com.tiramisu.android.todolist.Model;


import com.google.firebase.firestore.Exclude;

public class CategoryModel {

    String categoryName;

    @Exclude String categoryId;


    @Exclude public String getCategoryId() {
        return categoryId;
    }

   @Exclude public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryModel() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;

    }


}
