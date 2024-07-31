package com.reroute.tmsfm.controller;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.service.OrganizationServiceImpl;
import com.reroute.tmsfm.validate.ValidationMarker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    public ResponseEntity<OrganizationDto> createOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        log.debug("POST /organization: {}", organizationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organizationServiceImpl.createOrganization(organizationDto));
    }

    @PutMapping(value = "/organization")
    @Validated({ValidationMarker.OnUpdate.class})
    public ResponseEntity<OrganizationDto> updateOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        log.debug("PUT /organization: {}", organizationDto);
        return ResponseEntity.status(HttpStatus.OK).body(organizationServiceImpl.updateOrganization(organizationDto));
    }

    @GetMapping(value = "/organization/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        log.debug("GET /organization/all");
        return ResponseEntity.status(HttpStatus.OK).body(organizationServiceImpl.getAllOrganizations());
    }

    @GetMapping(value = "/organization")
    @Validated
    public ResponseEntity<List<OrganizationDto>> getAllOrganizationsPages(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer pageNumber,
            @RequestParam(defaultValue = "10") @Positive Integer pageSize) {
        log.debug("GET /organization from={}, size={}", pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(organizationServiceImpl.getAllOrganizationsPages(pageNumber, pageSize));
    }

    @GetMapping(value = "/organization/{organizationId}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("organizationId") UUID organizationId) {
        log.debug("GET /organization {}", organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(organizationServiceImpl.getOrganizationById(organizationId));
    }

    @DeleteMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void deleteOrganizationById(@PathVariable("organizationId") UUID organizationId) {
        log.debug("DELETE /organization/{organizationId} {}", organizationId);
        organizationServiceImpl.deleteOrganizationById(organizationId);
    }
}
