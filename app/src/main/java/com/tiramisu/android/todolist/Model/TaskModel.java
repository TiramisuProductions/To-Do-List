package com.tiramisu.android.todolist.Model;

/**
 * Created by Sarvesh Palav on 24-09-2017.
 */

public class TaskModel {
    String id;
    String taskName;
    String dueDate;
    String reminder;
    String done;


    public TaskModel(String id, String taskName, String dueDate, String reminder, String done) {
        this.id = id;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.reminder = reminder;
        this.done = done;
    }


    public TaskModel(String taskName, String dueDate, String reminder, String done) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.reminder = reminder;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }
}
