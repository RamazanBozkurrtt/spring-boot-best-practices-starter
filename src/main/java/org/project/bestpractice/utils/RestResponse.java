package org.project.bestpractice.utils;



import java.time.LocalDateTime;

public class RestResponse<T> {

    private boolean status;
    private String message;
    private T data;
    private LocalDateTime responseTime;

    public RestResponse() {}

    public RestResponse(boolean status, String message, T data, LocalDateTime responseTime) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
        this.setResponseTime(responseTime);
    }

    public static <T> RestResponse<T> ok(T data) {

        return new RestResponse<>(true,"success",data,LocalDateTime.now());
    }

    public static <T> RestResponse<T> of(T data) {
        return new RestResponse<>(true,"İşlem başarılı",data,LocalDateTime.now());
    }

    public static <T> RestResponse<T> empty() {

        return new RestResponse<>(true, "İşlem Başarılı", null, LocalDateTime.now());
    }

    public static <T> RestResponse<T> created(T data) {
        return new RestResponse<>(true,"created",data,LocalDateTime.now());
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

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }
}

