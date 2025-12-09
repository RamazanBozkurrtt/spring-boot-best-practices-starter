package org.project.bestpractice.controller;

import org.project.bestpractice.utils.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestBaseController<T> {

    public <T> ResponseEntity<RestResponse<T>> ok(T data) {
        return ResponseEntity.ok(RestResponse.ok(data));
    }

    public <T> ResponseEntity<RestResponse<T>> of() {
        return ResponseEntity.ok(RestResponse.empty());
    }

    // 201 CREATED dönüşü (Ekleme işlemleri için Best Practice)
    public <T> ResponseEntity<RestResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.ok(data));
    }
}
