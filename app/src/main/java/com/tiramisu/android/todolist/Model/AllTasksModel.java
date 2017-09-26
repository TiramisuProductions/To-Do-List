package com.tiramisu.android.todolist.Model;

/**
 * Created by Sarvesh Palav on 25-09-2017.
 */

public class AllTasksModel {
    String catId;
    String taskId;
    String done;
    String dueDate;
    String reminder;
    String taskName;


    public AllTasksModel(String catId, String taskId, String done, String dueDate, String reminder, String taskName) {
        this.catId = catId;
        this.taskId = taskId;
        this.done = done;
        this.dueDate = dueDate;
        this.reminder = reminder;
        this.taskName = taskName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
