package com.tiramisu.android.todolist.Model;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by sarveshpalav on 18/08/17.
 */

public class LocalData extends SugarRecord {


    String Catid;
    String Taskid;
    String CategoryName;
    String TaskName;
    String CatFirebaseid;
    String TaskFirebaseid;
    String DueDate;
    boolean isdone;
    boolean reminder;


    public LocalData() {
    }

    public LocalData(String catid, String taskid, String categoryName, String taskName, String catFirebaseid, String taskFirebaseid, String dueDate, boolean isdone, boolean reminder) {
        Catid = catid;
        Taskid = taskid;
        CategoryName = categoryName;
        TaskName = taskName;
        CatFirebaseid = catFirebaseid;
        TaskFirebaseid = taskFirebaseid;
        DueDate = dueDate;
        this.isdone = isdone;
        this.reminder = reminder;
    }


    public String getCatid() {
        return Catid;
    }

    public void setCatid(String catid) {
        Catid = catid;
    }

    public String getTaskid() {
        return Taskid;
    }

    public void setTaskid(String taskid) {
        Taskid = taskid;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getCatFirebaseid() {
        return CatFirebaseid;
    }

    public void setCatFirebaseid(String catFirebaseid) {
        CatFirebaseid = catFirebaseid;
    }

    public String getTaskFirebaseid() {
        return TaskFirebaseid;
    }

    public void setTaskFirebaseid(String taskFirebaseid) {
        TaskFirebaseid = taskFirebaseid;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public boolean isdone() {
        return isdone;
    }

    public void setIsdone(boolean isdone) {
        this.isdone = isdone;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
