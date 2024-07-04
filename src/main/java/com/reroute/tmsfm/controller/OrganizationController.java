package com.reroute.tmsfm.controller;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.service.OrganizationServiceImpl;
import com.reroute.tmsfm.utility.NotBlankUUID;
import com.reroute.tmsfm.utility.ValidationMarker;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        log.debug("Получен запрос @PostMapping(value = \"/organization\"), {}", organizationDto);
        return ResponseEntity.of(organizationServiceImpl.createOrganization(organizationDto));
    }

    @PutMapping(value = "/organization")
    @ResponseStatus(HttpStatus.OK)
    @Validated({ValidationMarker.OnUpdate.class})
    public ResponseEntity<OrganizationDto> updateOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        log.debug("Получен запрос @PatchMapping(value = \"/organization\") {}", organizationDto);
        return ResponseEntity.of(organizationServiceImpl.updateOrganization(organizationDto));
    }

    @GetMapping(value = "/organization")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public List<OrganizationDto> getAllOrganizations(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                     @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.debug("Получен запрос @GetMapping(value = \"/organization\")");
        log.debug("Значения параметров from={}, size={}", from, size);
        return organizationServiceImpl.getAllOrganizations(from, size);
    }

    @GetMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("organizationId") @org.hibernate.validator.constraints.UUID UUID organizationId) {
        log.debug("Получен запрос @GetMapping(value = \"/organization\") в контроллере getOrganizationById");
        log.debug("organizationId в контроллере getOrganizationById: {}", organizationId);
        return ResponseEntity.of(organizationServiceImpl.getOrganizationById(organizationId));
    }

    @DeleteMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void deleteOrganizationById(@PathVariable("organizationId") @NotBlankUUID UUID organizationId) {
        log.debug("Получен запрос @DeleteMapping(value = \"/organization/{organizationId}\") {}", organizationId);
        organizationServiceImpl.deleteOrganizationById(organizationId);
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
}
