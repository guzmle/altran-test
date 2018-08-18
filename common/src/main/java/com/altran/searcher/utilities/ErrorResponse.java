package com.altran.searcher.utilities;

/**
 * Created by guzmle on 17/8/18.
 */
public class ErrorResponse {
    private String message;
    private int code;

    public ErrorResponse(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
