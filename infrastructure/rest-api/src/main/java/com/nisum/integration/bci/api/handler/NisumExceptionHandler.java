package com.nisum.integration.bci.api.handler;

import com.nisum.integration.bci.api.handler.error.ErrorMessage;
import com.nisum.integration.bci.domain.exception.DomainException;
import com.nisum.integration.bci.domain.exception.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NisumExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage domainExceptionResolver(DomainException domainException) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(domainException.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage tokenExceptionResolver(TokenException tokenException) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(tokenException.getMessage());
        return errorMessage;
    }
}
