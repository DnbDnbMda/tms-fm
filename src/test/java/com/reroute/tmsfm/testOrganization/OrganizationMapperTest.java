package com.reroute.tmsfm.testOrganization;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.mapper.OrganizationMapper;
import com.reroute.tmsfm.mapper.OrganizationMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(classes = {OrganizationMapperImpl.class})
public class OrganizationMapperTest {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Test
    public void testMapToOrganization() {
        OrganizationDto organizationDto = createOrganizationDto();
        Organization organization = organizationMapper.toOrganization(organizationDto);

        Assertions.assertEquals("8976440746", organization.getInn());
        Assertions.assertEquals("665099658", organization.getKpp());
        Assertions.assertEquals("ООО Организация 1", organization.getName());
        Assertions.assertEquals("Общество с ограниченной отвественностью Организация 1",
                organization.getFullName());
        Assertions.assertFalse(organization.isGroup());
        Assertions.assertFalse(organization.isMarkedOnDelete());
        Assertions.assertNotNull(organization.getId());
        Assertions.assertNotNull(organization.getChangedDate());
        Assertions.assertNotNull(organization.getCreatedDate());
    }

    @Test
    public void testMapToOrganizationNullOrgDto() {
        Organization organization = organizationMapper.toOrganization(createOrganizationDtoNull());
        Assertions.assertNull(organization);
    }

    @Test
    public void testMapToOrganizationDtoNullOrg() {
        OrganizationDto organizationDto = organizationMapper.toOrganizationDto(createOrganizationNull());
        Assertions.assertNull(organizationDto);
    }

    @Test
    public void testMapToOrganizationDto() {
        Organization organization = createOrganization();
        OrganizationDto organizationDto = organizationMapper.toOrganizationDto(organization);

        Assertions.assertEquals("8976440746", organizationDto.getInn());
        Assertions.assertEquals("665099658", organizationDto.getKpp());
        Assertions.assertFalse(organizationDto.isGroup());
        Assertions.assertFalse(organizationDto.isMarkedOnDelete());
        Assertions.assertEquals("ООО Организация 2", organizationDto.getName());
        Assertions.assertEquals("Общество с ограниченной отвественностью Организация 2",
                organizationDto.getFullName());
        Assertions.assertNotNull(organizationDto.getId());
        Assertions.assertNotNull(organizationDto.getChangedDate());
        Assertions.assertNotNull(organizationDto.getChangedDate());
    }

    @Test
    public void testMapUpdateOrganizationDtoNull() {

        Organization organization = createOrganizationForUpdate();
        OrganizationDto organizationDto = createOrganizationDtoNull();
        organizationMapper.updateOrganization(organizationDto, organization);

        Assertions.assertNotEquals("8976440746", organization.getInn());
        Assertions.assertNotEquals("665099658", organization.getKpp());
        Assertions.assertTrue(organization.isGroup());
        Assertions.assertTrue(organization.isMarkedOnDelete());
        Assertions.assertNotEquals(UUID.fromString("22222222-2222-2222-2222-222222222222"), organization.getId());
        Assertions.assertNotEquals("ООО Организация 1", organization.getName());
        Assertions.assertNotEquals("Общество с ограниченной отвественностью Организация 1",
                organization.getFullName());
        Assertions.assertEquals(LocalDateTime.of(2024, 11, 10, 10, 10),
                organization.getCreatedDate());
        Assertions.assertEquals(LocalDateTime.of(2024, 12, 1, 0, 0),
                organization.getChangedDate());
    }

    @Test
    public void testMapUpdateOrganization() {
        Organization organization = createOrganizationForUpdate();
        OrganizationDto organizationDto = createOrganizationDto();
        organizationMapper.updateOrganization(organizationDto, organization);

        Assertions.assertEquals("8976440746", organization.getInn());
        Assertions.assertEquals("665099658", organization.getKpp());
        Assertions.assertFalse(organization.isGroup());
        Assertions.assertFalse(organization.isMarkedOnDelete());
        Assertions.assertNotEquals(UUID.fromString("22222222-2222-2222-2222-222222222222"), organization.getId());
        Assertions.assertEquals("ООО Организация 1", organization.getName());
        Assertions.assertEquals("Общество с ограниченной отвественностью Организация 1",
                organization.getFullName());
        Assertions.assertEquals(LocalDateTime.of(2024, 11, 10, 10, 10),
                organization.getCreatedDate());
        Assertions.assertNotEquals(LocalDateTime.of(2024, 12, 1, 0, 0),
                organization.getChangedDate());
    }

    private OrganizationDto createOrganizationDto() {
        return OrganizationDto.builder()
                .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .kpp("665099658")
                .inn("8976440746")
                .name("ООО Организация 1")
                .fullName("Общество с ограниченной отвественностью Организация 1")
                .group(false)
                .markedOnDelete(false)
                .build();
    }

    private Organization createOrganizationNull() {
        Organization organization;
        organization = null;
        return organization;
    }

    private Organization createOrganization() {
        return Organization.builder()
                .id(UUID.randomUUID())
                .inn("8976440746")
                .kpp("665099658")
                .group(false)
                .markedOnDelete(false)
                .name("ООО Организация 2")
                .fullName("Общество с ограниченной отвественностью Организация 2")
                .changedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
    }

    private Organization createOrganizationForUpdate() {
        return Organization.builder()
                .id(UUID.randomUUID())
                .inn("7776440746")
                .kpp("777099658")
                .group(true)
                .markedOnDelete(true)
                .name("ООО Организация для обновления")
                .fullName("Общество с ограниченной отвественностью Организация для обновления")
                .changedDate(LocalDateTime.of(2024, 12, 1, 0, 0))
                .createdDate(LocalDateTime.of(2024, 11, 10, 10, 10))
                .build();
    }

    private OrganizationDto createOrganizationDtoNull() {
        OrganizationDto organizationDto;
        organizationDto = null;
        return organizationDto;
    }
}

