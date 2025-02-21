package com.example.todolist;

public class TaskModel {
    private int id;
    private String taskName;
    private String date;
    private int isCompleted;

    public TaskModel(int id, String taskName, String date, int isCompleted) {
        this.id = id;
        this.taskName = taskName;
        this.date = date;
        this.isCompleted = isCompleted;
    }
    public TaskModel(String taskName, String date) {
        this.id = -1; // Default ID (you can ignore this when inserting)
        this.taskName = taskName;
        this.date = date;
        this.isCompleted = 0; // Default task is not completed
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDate() {
        return date;
    }

    public int isCompleted() {
        return isCompleted;
    }

    public void setCompleted(int completed) {
        isCompleted = completed;
    }

}


