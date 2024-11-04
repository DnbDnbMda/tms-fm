package com.reroute.tmsfm.testOrganization;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.exception.OrganizationDtoBodyIsNull;
import com.reroute.tmsfm.exception.OrganizationNotFound;
import com.reroute.tmsfm.exception.OrganizationWithSpecifiedIdAlreadyExists;
import com.reroute.tmsfm.mapper.OrganizationMapper;

import com.reroute.tmsfm.repository.OrganizationRepository;
import com.reroute.tmsfm.service.OrganizationService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "schema-test.sql")
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Order(1)
    @Test
    public void testGetPreCreatedOrganizationById() {
        OrganizationDto organizationDto = organizationService
                .getOrganizationById(UUID.fromString("31111111-1111-1111-1111-111111111111"));
        Assertions.assertEquals(organizationDto.getId(),
                UUID.fromString("31111111-1111-1111-1111-111111111111"));
    }

    @Order(2)
    @Test
    public void testCannotCreateTwoOrganizationsWithIdenticalIds() throws OrganizationWithSpecifiedIdAlreadyExists {
        Assertions.assertThrows(OrganizationWithSpecifiedIdAlreadyExists.class,
                () -> organizationService.createOrganization(createTestOrganizationDto()));

    }

    @Order(3)
    @Test
    public void testUpdatePredefinedRecord() {
        OrganizationDto organizationDtoForUpdate = createTestOrganizationDtoForUpdate();
        OrganizationDto updatesOrganizationdto =
                organizationService.updateOrganization(organizationDtoForUpdate);

        Assertions.assertNotNull(updatesOrganizationdto);
        Assertions.assertEquals(organizationDtoForUpdate.getId(), updatesOrganizationdto.getId());
    }

    @Order(4)
    @Test
    public void testDeletePredefinedRecordById() {
        organizationService.deleteOrganizationById(UUID.fromString("31111111-1111-1111-1111-111111111111"));
        Assertions.assertThrows(OrganizationNotFound.class, () -> organizationService
                .getOrganizationById(UUID.fromString("31111111-1111-1111-1111-111111111111")));
    }

    @Order(5)
    @Test
    public void testCreateOrganizationDtoNull() {
        Assertions.assertThrows(OrganizationDtoBodyIsNull.class,
                () -> organizationService.createOrganization(null));
    }

    @Order(6)
    @Test
    public void testUpdateOrganizationDtoNull() {
        Assertions.assertThrows(OrganizationDtoBodyIsNull.class,
                () -> organizationService.updateOrganization(null));
    }

    @Order(5)
    @Test
    public void testGetOrganizationIdNull() {
        Assertions.assertThrows(OrganizationNotFound.class,
                () -> organizationService.getOrganizationById(null));
    }

    @Order(6)
    @Test
    public void testDeleteOrganizationWithNullId() {
        Assertions.assertThrows(OrganizationNotFound.class,
                () -> organizationService.deleteOrganizationById(null));
    }

    @Order(7)
    @Test
    public void testDeleteOrganizationWithFakeId() {
        Assertions.assertThrows(OrganizationNotFound.class,
                () -> organizationService
                        .deleteOrganizationById(UUID.fromString("31111111-1111-1111-1111-111111111177")));
    }

    @Order(8)
    @Test
    public void testCreateOrganizationWithDtoIdNull() {
        OrganizationDto organizationDto = createTestOrganizationDtoWithNullId();
        Assertions.assertDoesNotThrow(() -> organizationService.createOrganization(organizationDto));
    }

    @Order(9)
    @Test
    public void testCreateOrganizationWithDtoNull() {
        Assertions.assertThrows(OrganizationDtoBodyIsNull.class,
                () -> organizationService.createOrganization(null));
    }

    @Order(10)
    @Test
    public void testCreateOrganizationWithSpecificId() {
        OrganizationDto organizationDto = createTestOrganizationDtoWithNewId();
        OrganizationDto createdOrganizationDto = organizationService.createOrganization(organizationDto);
        Assertions.assertNotNull(createdOrganizationDto);
    }

    @Order(11)
    @Test
    public void testUpdateOrganizationWithDtoNullId() {
        OrganizationDto organizationDto = createTestOrganizationDtoWithNullId();
        Assertions.assertThrows(OrganizationNotFound.class,
                () -> organizationService.updateOrganization(organizationDto));
    }

    @Order(12)
    @Test
    public void testUpdateOrganizationWithDtoFakeId() {
        OrganizationDto organizationDto = createTestOrganizationDtoWithNewId();
        Assertions.assertThrows(OrganizationNotFound.class,
                () -> organizationService.updateOrganization(organizationDto));
    }

    @Order(13)
    @Test
    @Sql(scripts = "testGetAllOrganizations.sql")
    public void testGetAllOrganizations() {
        List<OrganizationDto> organizationDtoList = organizationService.getAllOrganizations();
        Assertions.assertEquals(organizationDtoList.size(), 5);
    }

    @Order(14)
    @Test
    @Sql(scripts = "testGetAllOrganizations.sql")
    public void testGetAllOrganizationsPages() {
        List<OrganizationDto> organizationDtoList = organizationService.getAllOrganizationsPages(1, 3);
        Assertions.assertEquals(organizationDtoList.size(), 2);
    }

    private OrganizationDto createTestOrganizationDto() {
        return OrganizationDto.builder()
                .id(UUID.fromString("31111111-1111-1111-1111-111111111111"))
                .kpp("665099658")
                .inn("8976440746")
                .name("ООО Организация 1")
                .fullName("Общество с ограниченной отвественностью Организация 1")
                .createdDate(LocalDateTime.now())
                .changedDate(null)
                .group(true)
                .markedOnDelete(true)
                .build();
    }

    private OrganizationDto createTestOrganizationDtoForUpdate() {
        return OrganizationDto.builder()
                .id(UUID.fromString("31111111-1111-1111-1111-111111111111"))
                .kpp("111099658")
                .inn("1116440746")
                .name("ООО Организация 1 обновление")
                .fullName("Общество с ограниченной отвественностью Организация 1 обновление")
                .createdDate(null)
                .changedDate(null)
                .group(false)
                .markedOnDelete(false)
                .build();
    }

    private OrganizationDto createTestOrganizationDtoWithNullId() {
        return OrganizationDto.builder()
                .id(null)
                .kpp("000099658")
                .inn("0006440746")
                .name("ООО Организация 1 обновление")
                .fullName("Общество с ограниченной отвественностью Организация 1 обновление")
                .createdDate(null)
                .changedDate(null)
                .group(false)
                .markedOnDelete(false)
                .build();
    }

    private OrganizationDto createTestOrganizationDtoWithNewId() {
        return OrganizationDto.builder()
                .id(UUID.fromString("31111111-1111-1111-1111-111111111177"))
                .kpp("123499658")
                .inn("1234440746")
                .name("ООО Организация 2 новая")
                .fullName("Общество с ограниченной отвественностью Организация 2 новая")
                .createdDate(null)
                .changedDate(null)
                .group(false)
                .markedOnDelete(false)
                .build();
    }
}
