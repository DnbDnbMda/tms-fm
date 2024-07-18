package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrganizationService {
    OrganizationDto createOrganization(OrganizationDto organizationDto);

    OrganizationDto updateOrganization(OrganizationDto organizationDto);

    void deleteOrganizationById(UUID organisationId);

    List<OrganizationDto> getAllOrganizationsPages(Integer from, Integer size);

    List<OrganizationDto> getAllOrganizations();

    OrganizationDto getOrganizationById(UUID organisationId);
}
