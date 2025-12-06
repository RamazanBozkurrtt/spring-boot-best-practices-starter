package org.project.bestpractice.exceptions;

public class BaseException extends RuntimeException{

    BaseException(){

    }

    BaseException(ErrorMesage message){
        super(message.buildErrorMessage());
    }
}
