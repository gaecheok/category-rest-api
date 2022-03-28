package com.test.categoryrestapi.common;

public class ErrorResponse {
    Object code;
    Object message;

    public ErrorResponse() {}
    public ErrorResponse(Object code, Object message) {
        this.code = code;
        this.message = message;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
