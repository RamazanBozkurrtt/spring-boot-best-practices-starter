package org.project.bestpractice.handling;


import org.project.bestpractice.exceptions.ApiError;
import org.project.bestpractice.exceptions.ApiErrorValidations;
import org.project.bestpractice.exceptions.BaseException;
import org.project.bestpractice.exceptions.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError> handleBaseException(BaseException ex, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMessage(),webRequest));
    }

    private <M> ApiError<M> createApiError(M message,WebRequest webRequest){
        ApiError<M>  apiError = new ApiError<>();
        Exception<M>  exception = new Exception<>();
        exception.setCreateDate(LocalDateTime.now());
        exception.setPath(webRequest.getDescription(false).substring(4));
        exception.setHostName(getHostName());
        exception.setErrorMessage(message);

        apiError.setException(exception);
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        return apiError;

    }

    private String getHostName(){
        try {
            return  InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            System.out.println("UnknownHostException");
        }
        return null;
    }

    private List<String> addValueToList(List<String> list,String value){
        list.add(value);
        return list;
    }

    @ExceptionHandler(exception =  MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorValidations> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HashMap<String, List<String>> errors = new HashMap<String,List<String>>();

        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError)error).getField();
            if (errors.containsKey(fieldName)) {
                errors.put(fieldName,addValueToList(errors.get(fieldName),error.getDefaultMessage()));
            }else{
                errors.put(fieldName,addValueToList(new ArrayList<String>(),error.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(errors));
    }

    private <T> ApiErrorValidations<T> createApiError(T errors){
        ApiErrorValidations<T> apiError = new ApiErrorValidations<>();
        apiError.setErrorTime(LocalDateTime.now());
        apiError.setId(UUID.randomUUID().toString());
        apiError.setData(errors);

        return apiError;
    }
}
