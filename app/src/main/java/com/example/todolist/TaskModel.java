package com.example.todolist;

public class TaskModel {
    private String taskName;
    private String date;

    public TaskModel(String taskName, String date) {
        this.taskName = taskName;
        this.date = date;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDate() {
        return date;
    }
}

