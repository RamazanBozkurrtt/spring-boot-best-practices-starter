package org.project.bestpractice.exceptions;


import lombok.Getter;

@Getter
public enum MessageTypes {

    NO_RECORD_EXISTS("1001","Record Not Found"),
    RECORD_ALREADY_EXIST("1002","Record AlredyExist"),
    GENERAL_EXCEPTION("9999","An Exception Occurred");

    private String code;
    private String message;

    MessageTypes(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
