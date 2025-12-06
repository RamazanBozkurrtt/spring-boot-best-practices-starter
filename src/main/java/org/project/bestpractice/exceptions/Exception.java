package org.project.bestpractice.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exception<M> {

    private String hostName;
    private String path;
    private LocalDateTime createDate;
    private M errorMessage;
}
