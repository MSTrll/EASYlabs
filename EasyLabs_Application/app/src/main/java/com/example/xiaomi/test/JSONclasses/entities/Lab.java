package com.example.xiaomi.test.JSONclasses.entities;


import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

public class Lab implements Entity {
    private int id;
    private String title;
    private int number;
    private String author;
    private int taskCount;

    public Lab(int id, String title, int number, String author, int taskCount) {
        this.setId(id);
        this.setTitle(title);
        this.setNumber(number);
        this.setAuthor(author);
        this.setTaskCount(taskCount);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
