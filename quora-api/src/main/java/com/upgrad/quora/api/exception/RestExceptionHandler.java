package com.upgrad.quora.api.exception;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> signupRestrictedException(SignUpRestrictedException sgr, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(sgr.getCode()).message(sgr.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException(AuthenticationFailedException afe, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(afe.getCode()).message(afe.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(AuthorizationFailedException athr, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(athr.getCode()).message(athr.getErrorMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException usr, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(usr.getCode()).message(usr.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<ErrorResponse> invalidQuestionException(InvalidQuestionException ques, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(ques.getCode()).message(ques.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }
}
