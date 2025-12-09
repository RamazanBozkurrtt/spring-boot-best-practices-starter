package org.project.bestpractice.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private MessageTypes messageType;

    BusinessException(){

    }
    public BusinessException(MessageTypes messageType){
        super(messageType.getMessage());
        this.messageType=messageType;
    }
}
