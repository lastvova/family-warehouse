package com.familywarehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GoodsAlreadyExistsException extends RuntimeException {

    public GoodsAlreadyExistsException(String message) {
        super(message);
    }
}
