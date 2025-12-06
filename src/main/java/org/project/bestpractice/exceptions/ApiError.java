package org.project.bestpractice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<M> {

    private Integer status;

    private Exception<M> exception;


}
