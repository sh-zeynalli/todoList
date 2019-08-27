package com.example.todolist.handling.exception;

import java.util.ArrayList;

public class Response {

    private int code;
    private String message;
    private ArrayList<String> list;

    public Response() {
    }

    public Response(int code, String message) {

        this.code = code;
        this.message = message;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Response(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<String> getList() {

        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
