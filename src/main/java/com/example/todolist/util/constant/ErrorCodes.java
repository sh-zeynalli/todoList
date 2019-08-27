package com.example.todolist.util.constant;

public enum ErrorCodes {

    GENERAL_ERROR(5001,"General Error"),
    DATA_NOT_FOUND(5002,"Empty Field"),
    SUCCESS(0,"Success");


    private int code;

    private String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
