package com.tiramisu.android.todolist;



public class TaskDetails {

    String DateandTime,Name;
       boolean Status;

    public TaskDetails(String dateandTime, String name, boolean status) {
        DateandTime = dateandTime;
        Name = name;
        Status = status;
    }

    public String getDateandTime() {
        return DateandTime;
    }

    public void setDateandTime(String dateandTime) {
        DateandTime = dateandTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }


}
