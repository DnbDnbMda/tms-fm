package com.reroute.tmsfm.mapper;

import com.reroute.tmsfm.controller.OrganizationController;
import com.reroute.tmsfm.dto.OrganizationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = {OrganizationController.class})
class OrganizationControllerTest {

//    @Autowired
//    OrganizationController organizationController;
//
//    @Test
//    void createOrganizationTest() {
//        ResponseEntity<OrganizationDto> responseEntity =
//                organizationController.createOrganization(createOrganizationDtoForTest());
//        //Assertions.assertNotNull(responseEntity);
//    }
//
//    private OrganizationDto createOrganizationDtoForTest() {
//        OrganizationDto organizationDto = new OrganizationDto();
//        OrganizationDto.builder()
//                .id("49d9acff-345d-4b20-9b82-09bd7d448f5e")
//                .name("Reroute")
//                .inn("6546545646")
//                .kpp("65456465465")
//                .parent("49d9acff-345d-7b20-9b82-09bd7d448f5e")
//                .markedOnDelete(false)
//                .fullName("reroute")
//                .group(false)
//                .build();
//        return organizationDto;
//
//    }

}

