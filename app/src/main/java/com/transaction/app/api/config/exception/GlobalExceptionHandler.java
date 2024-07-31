package com.transaction.app.api.config.exception;

import com.transaction.app.api.controllers.dto.ApiResponse;
import com.transactions.domain.exceptions.DomainException;
import com.transactions.domain.exceptions.InsufficientBalanceException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse> handleInsufficientBalanceException(InsufficientBalanceException e) {
        logger.info(e.getMessage());
        return ResponseEntity.status(200).body(new ApiResponse("51"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        if (e instanceof DomainException) {
            logger.info(e.getMessage());
        } else {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return ResponseEntity.status(200).body(new ApiResponse("07"));
    }
}