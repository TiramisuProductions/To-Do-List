package com.tiramisu.android.todolist.Model;

/**
 * Created by Sarvesh Palav on 24-09-2017.
 */

public class CategorySuggestionModel {

    public String suggestion;

    public CategorySuggestionModel(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
