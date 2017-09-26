package com.tiramisu.android.todolist.Model;

/**
 * Created by Sarvesh Palav on 25-09-2017.
 */

public class TaskSuggestionModel {
    public String Suggestion;


    public TaskSuggestionModel(String suggestion) {
        Suggestion = suggestion;
    }

    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }
}
