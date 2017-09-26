package com.tiramisu.android.todolist.Model;


/*Model for other categories except All*/
public class TaskModel {
    String id;

    String taskName; //Name of the task
    String dueDate;  //Date set to each task
    String dueTime;  //Time set to each task
    String reminder;
    String done;

    public TaskModel(String taskName, String dueDate, String dueTime, String reminder, String done) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.reminder = reminder;
        this.done = done;
    }

    public TaskModel(String id, String taskName, String dueDate, String dueTime, String reminder, String done) {
        this.id = id;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.reminder = reminder;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
