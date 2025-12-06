package org.project.bestpractice.exceptions;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorValidations<T> {

    private String id;
    private LocalDateTime errorTime;
    private T data;

}
