package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationService {
    Optional<OrganizationDto> createOrganization(OrganizationDto organizationDto);

    Optional<OrganizationDto> updateOrganization(OrganizationDto organizationDto);

    void deleteOrganizationById(UUID organisationId);

    Optional<List<OrganizationDto>> getAllOrganizationsPages(Integer from, Integer size);

    Optional<List<OrganizationDto>> getAllOrganizations();

    Optional<OrganizationDto> getOrganizationById(UUID organisationId);
}
