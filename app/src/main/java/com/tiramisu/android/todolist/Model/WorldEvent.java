package com.tiramisu.android.todolist.Model;


public class WorldEvent {
    private final String message;

    public WorldEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
