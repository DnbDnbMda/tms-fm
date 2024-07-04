package com.reroute.tmsfm.mapper;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.service.OrganizationService;
import com.reroute.tmsfm.service.OrganizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;


@SpringBootTest(classes = OrganizationServiceImpl.class)
class OrganizationServiceTest {

//    @Autowired
//    private OrganizationServiceImpl organizationService;
//    //private OrganizationMapper organizationMapper;
//
//    @Test
//    void testCreateOrganization() {
//        Optional<OrganizationDto> createOrganizationDto =
//                organizationService.createOrganization(null);
//        Assertions.assertEquals("6546545646", createOrganizationDto.get().getInn());
//    }
//
//    private OrganizationDto createOrganizationDtoForTest() {
//        OrganizationDto organizationDto = new OrganizationDto();
//        OrganizationDto.builder()
//                .id("49d9acff-345d-4b20-9b82-09bd7d448f5e")
//                .name("Reroute")
//                .inn("6546545646")
//                .kpp("65456465465")
//                .parent("")
//                .markedOnDelete(false)
//                .fullName("reroute")
//                .group(false)
//                .build();
//        return organizationDto;
//
//    }
}
