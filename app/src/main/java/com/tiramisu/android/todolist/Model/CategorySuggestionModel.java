package com.tiramisu.android.todolist.Model;



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
