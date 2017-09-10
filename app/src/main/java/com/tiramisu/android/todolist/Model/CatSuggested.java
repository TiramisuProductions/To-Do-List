package com.tiramisu.android.todolist.Model;

import com.orm.SugarRecord;



public class CatSuggested extends SugarRecord {

    String cat_name;

    public CatSuggested() {

    }

    public CatSuggested(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
