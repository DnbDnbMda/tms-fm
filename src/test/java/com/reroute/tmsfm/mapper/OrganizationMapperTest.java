package com.reroute.tmsfm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OrganizationMapperImpl.class})
public class OrganizationMapperTest {

    @Autowired
    private OrganizationMapper organizationMapper;

//    @Test
//    public void testMapToOrganization() {
//        OrganizationDto organizationDto = createOrganizationDto();
//        Organization organization = organizationMapper.toOrganization(organizationDto);
//
//        Assertions.assertEquals("4564", organization.getInn());
//        Assertions.assertEquals("test", organization.getName());
//        Assertions.assertEquals("fulltest", organization.getFullName());
//        Assertions.assertNotNull(organization.getId());
//    }
//
//    @Test
//    public void testMapToOrganizationDto() {
//        Organization organization = createOrganization();
//        OrganizationDto organizationDto = organizationMapper.toOrganizationDto(organization);
//
//        Assertions.assertEquals("4321", organizationDto.getInn());
//        Assertions.assertEquals("987", organizationDto.getKpp());
//        Assertions.assertEquals("testOrg", organizationDto.getName());
//        Assertions.assertNotNull(organizationDto.getCreatedDate());
//        Assertions.assertEquals(LocalDateTime.now().getDayOfYear(), organizationDto.getCreatedDate().getDayOfYear());
//    }
//
//    private OrganizationDto createOrganizationDto() {
//        return OrganizationDto.builder()
//                .id(UUID.randomUUID())
//                .inn("4564")
//                .name("test")
//                .fullName("fulltest")
//                .build();
//    }
//
//    private Organization createOrganization() {
//        return Organization.builder()
//                .inn("4321")
//                .kpp("987")
//                .name("testOrg")
//                .createdDate(LocalDateTime.now())
//                .build();
//    }

}
