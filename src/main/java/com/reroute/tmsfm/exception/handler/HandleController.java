package com.reroute.tmsfm.exception.handler;

import com.reroute.tmsfm.exception.OrganizationDtoBodyIsNull;
import com.reroute.tmsfm.exception.OrganizationNotFound;
import com.reroute.tmsfm.exception.OrganizationWithSpecifiedIdAlreadyExists;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class HandleController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messages.add(fieldError.getField() + " :" + fieldError.getDefaultMessage());
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", messages);
        log.error("{}:{}", body, ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e) {

        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            messages.add(violation.getPropertyPath() + " :" + violation.getMessage());
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", messages);
        log.error("{}:{}", body, e.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(value = {OrganizationDtoBodyIsNull.class})
    public ResponseEntity<Object> organizationDtoBodyIsNull(OrganizationDtoBodyIsNull e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + e.getClass());
    }

    @ExceptionHandler(value = {OrganizationNotFound.class})
    public ResponseEntity<Object> organizationNotFound(OrganizationNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + e.getClass());
    }

    @ExceptionHandler(value = {OrganizationWithSpecifiedIdAlreadyExists.class})
    public ResponseEntity<Object> organizationWithSpecifiedIdAlreadyExists(OrganizationWithSpecifiedIdAlreadyExists e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + e.getClass());
    }

    @ExceptionHandler(value = {EOFException.class, IOException.class})
    public ResponseEntity<Object> ioException(EOFException e, IOException ex) {
        log.error("{}:{}", e.getMessage(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + e.getClass());
    }
}
