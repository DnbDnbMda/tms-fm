package com.ReRoute.tmsfm.service;

import com.ReRoute.tmsfm.dto.OrganizationCreateDto;
import com.ReRoute.tmsfm.entity.Organization;
import com.ReRoute.tmsfm.entity.OrganizationMapper;
import com.ReRoute.tmsfm.repository.OrganizationRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class OrganizationService {

    private final OrganizationMapper organizationMapper;
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationMapper organizationMapper, OrganizationRepository organizationRepository) {
        this.organizationMapper = organizationMapper;
        this.organizationRepository = organizationRepository;
    }

    public void createOrganization(OrganizationCreateDto organizationCreateDto) {
        Organization organization = organizationMapper.toOrganization(organizationCreateDto);
        organizationRepository.save(organization);
    }
}
