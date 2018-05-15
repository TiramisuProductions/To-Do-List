package com.tiramisu.android.todolist.Model;


import com.google.firebase.firestore.Exclude;

/*Model for other categories except All*/
public class TaskModel {
    @Exclude String taskId;
    String taskName;
    boolean taskDone;

    public TaskModel() {
    }

    public TaskModel(String taskName, boolean taskDone) {
        this.taskName = taskName;
        this.taskDone = taskDone;
    }

  @Exclude  public String getTaskId() {
        return taskId;
    }

   @Exclude public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }
}
