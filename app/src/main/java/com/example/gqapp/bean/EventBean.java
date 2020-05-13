package com.example.gqapp.bean;

/**
 * created by lxx at 2020/4/9 17:32
 * 描述:
 */
public class EventBean<T> {
    private int code;
    private T data;
    private T fra;

    public EventBean(int code) {
        this.code = code;
    }

    public EventBean(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public EventBean(int code, T data,T fra) {
        this.code = code;
        this.data = data;
    }

    public T getFra() {
        return fra;
    }

    public void setFra(T fra) {
        this.fra = fra;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
