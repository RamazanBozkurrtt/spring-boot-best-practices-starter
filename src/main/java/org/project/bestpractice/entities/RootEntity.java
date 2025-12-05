package org.project.bestpractice.entities;


public class RootEntity<T> {

    private boolean status;
    private String message;
    private T data;

    public RootEntity() {}

    public RootEntity(boolean status, String message, T data) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
    }

    public static <T> RootEntity<T> ok(T data) {

        return new RootEntity<>(true,"success",data);
    }

    public static <T> RootEntity<T> error(T data) {
        return new RootEntity<>(false,"error",data);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
