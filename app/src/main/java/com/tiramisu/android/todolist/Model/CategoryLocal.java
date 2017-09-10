package com.tiramisu.android.todolist.Model;

import com.orm.SugarRecord;

/**
 * Created by sarveshpalav on 19/08/17.
 */

public class CategoryLocal extends SugarRecord {

    String catname;


    public CategoryLocal() {
    }

    public CategoryLocal(String catname) {
        this.catname = catname;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}
