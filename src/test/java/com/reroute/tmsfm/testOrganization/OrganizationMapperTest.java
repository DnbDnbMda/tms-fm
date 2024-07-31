package com.reroute.tmsfm.testOrganization;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.mapper.OrganizationMapper;
import com.reroute.tmsfm.mapper.OrganizationMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    private OrganizationDto createOrganizationDto() {
        return OrganizationDto.builder()
                .id(UUID.randomUUID())
                .kpp("665099658")
                .inn("8976440746")
                .name("ООО Организация 1")
                .fullName("Общество с ограниченной отвественностью Организация 1")
                .group(false)
                .markedOnDelete(false)
                .build();
    }
}

