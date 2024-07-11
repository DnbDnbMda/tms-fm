package com.reroute.tmsfm.controller;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.service.OrganizationServiceImpl;
import com.reroute.tmsfm.validate.ValidationMarker;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Validated
@RestController
@Slf4j
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final OrganizationServiceImpl organizationServiceImpl;

    public OrganizationController(OrganizationServiceImpl organizationServiceImpl) {
        this.organizationServiceImpl = organizationServiceImpl;
    }

    @PostMapping(value = "/organization")
    @Validated({ValidationMarker.OnCreate.class})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrganizationDto> createOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        log.debug("POST /organization: {}", organizationDto);
        return ResponseEntity.of(organizationServiceImpl.createOrganization(organizationDto));
    }

    @PutMapping(value = "/organization")
    @ResponseStatus(HttpStatus.OK)
    @Validated({ValidationMarker.OnUpdate.class})
    public ResponseEntity<OrganizationDto> updateOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        log.debug("PUT /organization: {}", organizationDto);
        return ResponseEntity.of(organizationServiceImpl.updateOrganization(organizationDto));
    }

    @GetMapping(value = "/organization/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        log.debug("GET /organization/all");
        return ResponseEntity.of(organizationServiceImpl.getAllOrganizations());
    }

    @GetMapping(value = "/organization")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public ResponseEntity<List<OrganizationDto>> getAllOrganizationsPages(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer pageNumber,
            @RequestParam(defaultValue = "10") @Positive Integer pageSize) {
        log.debug("GET /organization from={}, size={}", pageNumber, pageSize);
        return ResponseEntity.of(organizationServiceImpl.getAllOrganizationsPages(pageNumber, pageSize));
    }

    @GetMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("organizationId")
                                                               @org.hibernate.validator.constraints.UUID
                                                                       (message = "Некорректный идентификатор")
                                                               String organizationId) {
        log.debug("GET /organization {}", organizationId);
        return ResponseEntity.of(organizationServiceImpl.getOrganizationById(UUID.fromString(organizationId)));
    }

    @DeleteMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void deleteOrganizationById(@PathVariable("organizationId")
                                       @org.hibernate.validator.constraints.UUID
                                               (message = "Некорректный идентификатор")
                                       String organizationId) {
        log.debug("DELETE /organization/{organizationId} {}", organizationId);
        organizationServiceImpl.deleteOrganizationById(UUID.fromString(organizationId));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            messages.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", messages);
        log.error(body.toString());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", messages);
        log.error(body.toString());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", messages);
        log.error(body.toString());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
