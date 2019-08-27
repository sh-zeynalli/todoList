package com.example.todolist.search;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchReq {

    private String task;
    private String priority;

    @Override
    public String toString() {
        return "SearchReq{" +
                "task='" + task + '\'' +
                ", priority='" + priority + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //new
    private Date date;
    private String category;


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
