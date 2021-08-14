package com.changgou.entity;
import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private Integer code;
    private String message;
    private T data;

    public Result(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
