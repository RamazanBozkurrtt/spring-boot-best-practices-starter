package org.project.bestpractice.controller;

import org.project.bestpractice.utils.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class RestBaseController { // Abstract olması mantıklı, tek başına new'lenmez.

    public <T> ResponseEntity<RestResponse<T>> ok(T data) {
        return ResponseEntity.ok(RestResponse.ok(data));
    }

    public <T> ResponseEntity<RestResponse<T>> created(T data) {
        // HTTP Header'da 201 döner, Body içinde de Created mesajı döner.
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.created(data));
    }

    public <T> ResponseEntity<RestResponse<T>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(RestResponse.empty());
    }
}