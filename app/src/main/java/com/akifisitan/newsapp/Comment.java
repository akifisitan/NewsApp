package com.akifisitan.newsapp;

import java.io.Serializable;

// Comment class for storing comments
public class Comment implements Serializable {
    private int id, newsId;
    private String text, name;

    public Comment() {}

    public Comment(int newsId, String text, String name) {
        this.newsId = newsId;
        this.text = text;
        this.name = name;
    }

    public Comment(int id, int newsId, String text, String name) {
        this.id = id;
        this.newsId = newsId;
        this.text = text;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
