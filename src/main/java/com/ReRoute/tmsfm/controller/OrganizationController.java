package com.ReRoute.tmsfm.controller;

import com.ReRoute.tmsfm.dto.OrganizationCreateDto;
import com.ReRoute.tmsfm.dto.OrganizationDto;
import com.ReRoute.tmsfm.service.OrganizationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping(value = "/organization")
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateOrganization(@RequestBody OrganizationCreateDto organizationCreateDto) {
        //log.info("Create Organization");
        //return new OrganizationDto();
        organizationService.createOrganization(organizationCreateDto);
    }
}
