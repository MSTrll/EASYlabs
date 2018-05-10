package com.example.xiaomi.test.JSONclasses.entities;


import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

public class Task implements Entity {
    private int id;
    private String name;
    private int parent;
    private int number;
    private String text;
    private String formula;
    private String author;

    public Task(int id, String name, int parent, int number, String text, String formula, String author) {
        this.setId(id);
        this.setName(name);
        this.setParent(parent);
        this.setNumber(number);
        this.setText(text);
        this.setFormula(formula);
        this.setAuthor(author);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
