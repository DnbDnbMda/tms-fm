package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationService {
    Optional<OrganizationDto> createOrganization(OrganizationDto organizationDto);

    Optional<OrganizationDto> updateOrganization(OrganizationDto organizationDto);

    void deleteOrganizationById(UUID organisationId);

    List<OrganizationDto> getAllOrganizations(Integer from, Integer size);

    Optional<OrganizationDto> getOrganizationById(UUID organisationId);
}
