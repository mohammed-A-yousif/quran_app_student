package com.example.quranappstudent;

public class Task {
    int Id;
    String TaskName;
    String TaskDec;
    String CreatedAt;

    public Task(int Id, String TaskName, String TaskDec, String CreatedAt){
        this.Id = Id;
        this.TaskName = TaskName;
        this.TaskDec = TaskDec;
        this.CreatedAt = CreatedAt;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public void setTaskDec(String taskDec) {
        TaskDec = taskDec;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public int getId() {
        return Id;
    }

    public String getTaskName() {
        return TaskName;
    }

    public String getTaskDec() {
        return TaskDec;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }
}
