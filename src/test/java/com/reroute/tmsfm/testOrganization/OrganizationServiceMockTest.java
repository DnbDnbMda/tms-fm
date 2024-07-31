package com.reroute.tmsfm.testOrganization;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.exception.OrganizationDtoBodyIsNull;
import com.reroute.tmsfm.exception.OrganizationWithSpecifiedIdAlreadyExists;
import com.reroute.tmsfm.mapper.OrganizationMapper;
import com.reroute.tmsfm.repository.OrganizationRepository;
import com.reroute.tmsfm.service.OrganizationService;
import com.reroute.tmsfm.service.OrganizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceMockTest {

    @Mock
    private OrganizationMapper organizationMapper;

    @Mock
    private OrganizationRepository organizationRepository;

    private OrganizationService organizationService;

    @BeforeEach
    public void init() {
        this.organizationService = new OrganizationServiceImpl(organizationRepository, organizationMapper);
    }

    @Test
    public void createOrganization() {
        OrganizationDto organizationDto = createOrganizationDtoObject(null, false);
        when(organizationRepository.save(any())).thenReturn(createOrganizationObject(null, true));
        organizationService.createOrganization(organizationDto);
        verify(organizationMapper).toOrganization(any());
        verify(organizationMapper).toOrganizationDto(any());
        verify(organizationMapper, times(1)).toOrganizationDto(any());
        verify(organizationMapper, atLeastOnce()).toOrganization(any());
    }

    @Test
    public void createOrganizationExistId() {
        OrganizationDto organizationDto =
                createOrganizationDtoObject(UUID.fromString("31111111-1111-1111-1111-111111111111"), false);
        when(organizationRepository.existsById(any())).thenReturn(true);
        Assertions.assertThrows(OrganizationWithSpecifiedIdAlreadyExists.class,
                () -> organizationService.createOrganization(organizationDto));
    }

    @Test
    public void createOrganizationDtoIsNull() {
        Assertions.assertThrows(OrganizationDtoBodyIsNull.class,
                () -> organizationService.createOrganization(null));
        verify(organizationRepository, never()).save(any());
        verify(organizationMapper, never()).toOrganization(any());
        verifyNoInteractions(organizationRepository, organizationMapper);
    }

    @Test
    public void updateOrganizationNormalDto() {
        OrganizationDto organizationDto =
                createOrganizationDtoObject(UUID.fromString("31111111-1111-1111-1111-111111111111"), false);
        Organization organization = createOrganizationObject
                (UUID.fromString("31111111-1111-1111-1111-111111111111"), false);
        when(organizationRepository.existsById(any())).thenReturn(true);
        when(organizationMapper.updateOrganization(any(), any())).thenReturn(organization);

        organizationService.updateOrganization(organizationDto);

        verify(organizationRepository, times(1))
                .existsById(UUID.fromString("31111111-1111-1111-1111-111111111111"));
        verify(organizationMapper, times(1)).updateOrganization(any(), any());
    }

    @Test
    public void getOrganizationByIdExist() {
        Organization organization =
                createOrganizationObject(UUID.fromString("31111111-1111-1111-1111-111111111111"), false);
        OrganizationDto organizationDto =
                createOrganizationDtoObject(UUID.fromString("31111111-1111-1111-1111-111111111111"), false);
        when(organizationRepository.findById(any())).thenReturn(Optional.of(organization));
        when(organizationMapper.toOrganizationDto(any())).thenReturn(organizationDto);

        organizationService.getOrganizationById(UUID.fromString("31111111-1111-1111-1111-111111111111"));

        verify(organizationRepository).findById(any());
        verify(organizationMapper).toOrganizationDto(any());
    }

    @Test
    public void deleteOrganizationExistId() {
        when(organizationRepository.existsById(any())).thenReturn(true);

        organizationService.deleteOrganizationById(UUID.randomUUID());

        verify(organizationRepository, times(1)).deleteById(any());
    }

    private OrganizationDto createOrganizationDtoObject(UUID uuid, boolean randId) {
        UUID uuidForOrgDto;
        if (randId) {
            uuidForOrgDto = UUID.randomUUID();
        } else {
            uuidForOrgDto = uuid;
        }

        return OrganizationDto.builder()
                .id(uuidForOrgDto)
                .kpp("665099658")
                .inn("8976440746")
                .name("ООО Организация 1")
                .fullName("Общество с ограниченной отвественностью Организация 1")
                .createdDate(null)
                .changedDate(null)
                .group(true)
                .markedOnDelete(true)
                .build();
    }

    private Organization createOrganizationObject(UUID uuid, boolean randId) {
        UUID uuidForOrg;
        if (randId) {
            uuidForOrg = UUID.randomUUID();
        } else {
            uuidForOrg = uuid;
        }

        return Organization.builder()
                .id(uuidForOrg)
                .kpp("665099658")
                .inn("8976440746")
                .name("ООО Организация 1")
                .fullName("Общество с ограниченной отвественностью Организация 1")
                .createdDate(null)
                .changedDate(null)
                .group(true)
                .markedOnDelete(true)
                .build();
    }
}

