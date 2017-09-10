package com.tiramisu.android.todolist.Model;

import com.orm.SugarRecord;

import java.util.List;



public class Task extends SugarRecord{
   public  long catid;
   public String taskname;
   public  String catfirebaseid;
   public String taskfirebaseid;
    public long datecreated;
    public long duedate;
   public  long duetime;
   public boolean isdone;

    public Task() {
    }

    public Task(long catid, String taskname, String catfirebaseid, String taskfirebaseid, long datecreated, long duedate, long duetime, boolean isdone) {
        this.catid = catid;
        this.taskname = taskname;

        this.catfirebaseid = catfirebaseid;
        this.taskfirebaseid = taskfirebaseid;
        this.datecreated = datecreated;

        this.duedate = duedate;
        this.duetime = duetime;
        this.isdone = isdone;
    }

    public long getCatid() {
        return catid;
    }

    public void setCatid(long catid) {
        this.catid = catid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getCatfirebaseid() {
        return catfirebaseid;
    }

    public void setCatfirebaseid(String catfirebaseid) {
        this.catfirebaseid = catfirebaseid;
    }

    public String getTaskfirebaseid() {
        return taskfirebaseid;
    }

    public void setTaskfirebaseid(String taskfirebaseid) {
        this.taskfirebaseid = taskfirebaseid;
    }

    public long getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(long datecreated) {
        this.datecreated = datecreated;
    }

    public long getDuedate() {
        return duedate;
    }

    public void setDuedate(long duedate) {
        this.duedate = duedate;
    }

    public long getDuetime() {
        return duetime;
    }

    public void setDuetime(long duetime) {
        this.duetime = duetime;
    }

    public boolean isdone() {
        return isdone;
    }

    public void setIsdone(boolean isdone) {
        this.isdone = isdone;
    }
}