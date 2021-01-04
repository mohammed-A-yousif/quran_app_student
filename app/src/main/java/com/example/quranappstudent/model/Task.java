package com.example.quranappstudent.model;

public class Task {
    int Id;
    String TaskName;
    String TaskDec;
    String Teacher;
    String Student;
    int TaskStatus;
    String CreatedAt;

    public Task(int Id, String TaskName, String TaskDec, String Teacher, String Student, int TaskStatus, String CreatedAt){
        this.Id = Id;
        this.TaskName = TaskName;
        this.TaskDec = TaskDec;
        this.Teacher = Teacher;
        this.Student = Student;
        this.TaskStatus = TaskStatus;
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

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public void setTaskStatus(int taskStatus) {
        TaskStatus = taskStatus;
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

    public String getTeacher() {
        return Teacher;
    }

    public String getStudent() {
        return Student;
    }

    public int getTaskStatus() {
        return TaskStatus;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }
}