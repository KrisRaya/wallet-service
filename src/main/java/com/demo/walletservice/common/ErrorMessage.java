package com.demo.walletservice.common;

public class ErrorMessage {
    private String message;
    private String code;

    public ErrorMessage(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ErrorMessage(String message) {
        super();
        this.code = null;
        this.message = message;
    }

    public ErrorMessage() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
