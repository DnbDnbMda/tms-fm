package com.reroute.tmsfm.controller;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.service.OrganizationServiceImpl;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationDto createOrganization(@RequestBody OrganizationDto organizationDto) {
        log.info("Получен запрос @PostMapping(value = \"/organization\")");
        log.info("Начальное состояние OrganizationDto в контроллере createOrganization: {}", organizationDto);
        return organizationServiceImpl.createOrganization(organizationDto);
    }

    @PatchMapping(value = "/organization")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationDto updateOrganization(@RequestBody OrganizationDto organizationDto) {
        log.info("Получен запрос @PatchMapping(value = \"/organization\")");
        log.info("Начальное состояние organizationDto в контроллере updateOrganization: {}", organizationDto);
        return organizationServiceImpl.updateOrganization(organizationDto);
    }

    @GetMapping(value = "/organization")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public List<OrganizationDto> getAllOrganizations(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                     @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Получен запрос @GetMapping(value = \"/organization\")");
        log.info("Значения параметров from={}, size={}", from, size);
        return organizationServiceImpl.getAllOrganizations(from, size);
    }

    @GetMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationDto getOrganizationById(@PathVariable("organizationId") UUID organizationId) {
        log.info("Получен запрос @GetMapping(value = \"/organization\") в контроллере getOrganizationById");
        log.info("organizationId в контроллере getOrganizationById: {}", organizationId);
        return organizationServiceImpl.getOrganizationById(organizationId);
    }

    @DeleteMapping(value = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrganizationById(@PathVariable("organizationId") UUID organizationId) {
        log.info("Получен запрос @DeleteMapping(value = \"/organization/{organizationId}\") в " +
                "контроллере deleteOrganizationById");
        log.info("organizationId в контроллере deleteOrganizationById: {}", organizationId);
        organizationServiceImpl.deleteOrganizationById(organizationId);
    }
}
