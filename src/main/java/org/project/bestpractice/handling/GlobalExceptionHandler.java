package org.project.bestpractice.handling;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.project.bestpractice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    //Business Exception
    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ApiErrorResponse> handleBaseException(BusinessException ex, HttpServletRequest webRequest) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .path(webRequest.getRequestURI())
                .createTime(LocalDateTime.now())
                .message(ex.getMessage())
                .hostName(getHostName())
                .build();
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }

    private String getHostName(){
        try {
            return  InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            System.out.println("UnknownHostException");
        }
        return null;
    }

    //Validation Exception HAndler
    @ExceptionHandler(exception =  MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        HashMap<String, List<String>> errors = new HashMap<>();

        for(FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.computeIfAbsent(error.getField(),k->new ArrayList<>())
                    .add(error.getDefaultMessage());
        }
        ApiErrorResponse<Map<String, List<String>>> apiErrorResponse = ApiErrorResponse.<Map<String, List<String>>>builder()
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .hostName(getHostName())
                .message("Validation Error!")
                .id(UUID.randomUUID())
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(apiErrorResponse);
    }

    //Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception e, HttpServletRequest request) {
        // logging

        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Bilinmeyen bir hata gerçekleşti!")
                .hostName(getHostName())
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(Exception e, HttpServletRequest request) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Email veya şifre hatalı !")
                .hostName(getHostName())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResponse);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(Exception e, HttpServletRequest request) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Email Bulunamadı!")
                .hostName(getHostName())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
    }

    @ExceptionHandler(value = {LockedException.class, DisabledException.class})
    public ResponseEntity<ApiErrorResponse> handleAccountStatusExceptions(Exception e, HttpServletRequest request) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Hesabınız kilitlenmiş veya pasif durumda !")
                .hostName(getHostName())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorResponse);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ApiErrorResponse> handleExpiredJwtException(Exception e, HttpServletRequest request) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Oturum süreniz dolmuş, lütfen tekrar giriş yapın.")
                .hostName(getHostName())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResponse);
    }


    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<ApiErrorResponse> handleSignatureException(Exception e, HttpServletRequest request) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Geçersiz Token!")
                .hostName(getHostName())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResponse);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(Exception e, HttpServletRequest request) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .hostName(getHostName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

}
