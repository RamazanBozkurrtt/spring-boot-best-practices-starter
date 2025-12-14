package org.project.bestpractice.handling;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.project.bestpractice.exceptions.*;
import org.project.bestpractice.utils.NetworkUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    //Business Exception
    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ApiErrorResponse> handleBaseException(BusinessException ex, HttpServletRequest webRequest) {
        log.warn("Business Exception | Message: {} | Path: {}", ex.getMessage(), webRequest.getRequestURI());
        return buildErrorResponse(ex.getMessage(), webRequest.getRequestURI(), HttpStatus.BAD_REQUEST);
    }

    //Validation Exception Handler
    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        log.warn("Validation Error | Path: {}", request.getRequestURI());

        HashMap<String, List<String>> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), k -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        }
        ApiErrorResponse<Map<String, List<String>>> apiErrorResponse = ApiErrorResponse.<Map<String, List<String>>>builder()
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .hostName(NetworkUtils.getHostName())
                .message("Validasyon Hatası!")
                .id(UUID.randomUUID())
                .data(errors)
                .build();
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(Exception e, HttpServletRequest request) {
        log.warn("Failed Login (Bad Credentials) | IP: {} | Path: {}", request.getRemoteAddr(), request.getRequestURI());
        return buildErrorResponse(ErrorCode.AUTH_LOGIN_FAILED.getMessage(), request.getRequestURI(), ErrorCode.AUTH_LOGIN_FAILED.getHttpStatus());
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(Exception e, HttpServletRequest request) {
        log.warn("User Not Found | Path: {}", request.getRequestURI());
        return buildErrorResponse(ErrorCode.USER_NOT_FOUND.getMessage(), request.getRequestURI(), ErrorCode.USER_NOT_FOUND.getHttpStatus());
    }

    @ExceptionHandler(value = {LockedException.class, DisabledException.class})
    public ResponseEntity<ApiErrorResponse> handleAccountStatusExceptions(Exception e, HttpServletRequest request) {
        log.warn("Locked/Disabled Account Attempt | IP: {}", request.getRemoteAddr());
        return buildErrorResponse(ErrorCode.AUTH_LOCKED_OR_INACTIVE.getMessage(), request.getRequestURI(), ErrorCode.AUTH_LOCKED_OR_INACTIVE.getHttpStatus());
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ApiErrorResponse> handleExpiredJwtException(Exception e, HttpServletRequest request) {
        log.warn("Token Expired | IP: {}", request.getRemoteAddr());
        return buildErrorResponse(ErrorCode.AUTH_TOKEN_EXPIRED.getMessage(), request.getRequestURI(), ErrorCode.AUTH_TOKEN_EXPIRED.getHttpStatus());
    }

    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<ApiErrorResponse> handleSignatureException(Exception e, HttpServletRequest request) {
        log.warn("Invalid Token Signature (Potential Attack) | IP: {}", request.getRemoteAddr());
        return buildErrorResponse(ErrorCode.AUTH_INVALID_SIGNATURE.getMessage(), request.getRequestURI(), ErrorCode.AUTH_INVALID_SIGNATURE.getHttpStatus());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(Exception e, HttpServletRequest request) {
        log.warn("Access Denied (Forbidden) | User tried to access: {}", request.getRequestURI());
        return buildErrorResponse(ErrorCode.AUTH_UNAUTHORIZED.getMessage(), request.getRequestURI(), ErrorCode.AUTH_UNAUTHORIZED.getHttpStatus());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(Exception e, HttpServletRequest request) {
        log.warn("Runtime Exception: {}", e.getMessage());
        return buildErrorResponse(e.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST);
    }

    //Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception e, HttpServletRequest request) {

        UUID traceId = UUID.randomUUID();

        log.error("CRITICAL ERROR | TraceID: {} | Path: {} | Message: {}", traceId, request.getRequestURI(), e.getMessage(), e);

        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .id(traceId)
                .createTime(LocalDateTime.now())
                .path(request.getRequestURI())
                .message("Bilinmeyen bir hata gerçekleşti. Hata Kodu: " + traceId)
                .hostName(NetworkUtils.getHostName())
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<ApiErrorResponse> buildErrorResponse(String message, String path, HttpStatus status) {
        ApiErrorResponse<?> response = ApiErrorResponse.builder()
                .id(UUID.randomUUID())
                .path(path)
                .createTime(LocalDateTime.now())
                .message(message)
                .hostName(NetworkUtils.getHostName())
                .build();
        return new ResponseEntity<>(response, status);
    }
}