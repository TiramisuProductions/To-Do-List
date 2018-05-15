package com.tiramisu.android.todolist.Model;


 /*Model for All category*/
public class AllTasksModel {
    String catId;    //Id of each category
    String taskId;   //Id of each each task
    String done;     //Boolean to check if task is selected
    String dueDate;  //Date set to each task
     String dueTime;     //Time set to each task
    String reminder; //
    String taskName; //Name of each task


    public AllTasksModel(String catId, String taskId, String done, String dueDate,String dueTime, String reminder, String taskName) {
        this.catId = catId;
        this.taskId = taskId;
        this.done = done;
        this.dueDate = dueDate;
        this.dueTime=dueTime;
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

     public String getDueTime() {return dueTime;}

     public void setDueTime(String dueTime) {this.dueTime = dueTime;}

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
