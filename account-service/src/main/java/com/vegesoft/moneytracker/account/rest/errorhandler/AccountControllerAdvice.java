package com.vegesoft.moneytracker.account.rest.errorhandler;

import com.vegesoft.moneytracker.account.exception.NotFoundAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AccountControllerAdvice {

    @ExceptionHandler(NotFoundAccountException.class)
    @ResponseBody
    protected ResponseEntity<ErrorBody> handle(NotFoundAccountException ex) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorBody(ex.getMessage(), httpStatus.value()), httpStatus);
    }
}
