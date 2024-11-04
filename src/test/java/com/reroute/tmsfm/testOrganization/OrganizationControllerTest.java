package com.reroute.tmsfm.testOrganization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reroute.tmsfm.controller.OrganizationController;
import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.service.OrganizationService;
import com.reroute.tmsfm.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationServiceImpl organizationService;

    @InjectMocks
    OrganizationController organizationController;

    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationService organizationServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createOrganizationControllerTest() throws Exception {
        OrganizationDto organizationDto = createTestOrganizationDto();

        when(organizationServiceImpl.createOrganization(any(OrganizationDto.class))).thenReturn(organizationDto);

        mockMvc.perform(post("/organization")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(organizationDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(organizationDto)));
    }

    @Test
    public void putOrganizationControllerTest() throws Exception {
        OrganizationDto organizationDto = createTestOrganizationDto();

        when(organizationServiceImpl.updateOrganization(any(OrganizationDto.class))).thenReturn(organizationDto);

        mockMvc.perform(put("/organization")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(organizationDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(organizationDto)));
    }

    @Test
    public void getAllOrganizationControllerTest() throws Exception {

        List<OrganizationDto> organizationDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            organizationDtoList.add(createTestOrganizationDtoRndId());
        }

        when(organizationServiceImpl.getAllOrganizations()).thenReturn(organizationDtoList);

        mockMvc.perform(get("/organization/all")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(organizationDtoList)));
    }

    @Test
    public void getPagesOrganizationControllerTest() throws Exception {

        List<OrganizationDto> organizationDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            organizationDtoList.add(createTestOrganizationDtoRndId());
        }

        when(organizationServiceImpl.getAllOrganizationsPages(anyInt(),anyInt())).thenReturn(organizationDtoList);

        mockMvc.perform(get("/organization")
                        .param("from","1")
                        .param("size", "3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(organizationDtoList)));
    }

    @Test
    public void getOrganizationByIdControllerTest() throws Exception {


           OrganizationDto organizationDto = createTestOrganizationDtoRndId();

        when(organizationServiceImpl.getOrganizationById(any())).thenReturn(organizationDto);

        mockMvc.perform(get("/organization/{organizationId}",UUID.randomUUID())
                                 .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(organizationDto)));
    }

    @Test
    public void deleteOrganizationByIdControllerTest() throws Exception {

       doNothing().when(organizationServiceImpl).deleteOrganizationById(any());

        mockMvc.perform(delete("/organization/{organizationId}",UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    private OrganizationDto createTestOrganizationDto() {
        return OrganizationDto.builder()
                .id(null)
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

    private OrganizationDto createTestOrganizationDtoRndId() {
        return OrganizationDto.builder()
                .id(UUID.randomUUID())
                .kpp("665099658")
                .inn("8976440746")
                .name("ООО Организация 1")
                .fullName("Общество с ограниченной отвественностью Организация 1")
                .createdDate(LocalDateTime.now())
                .changedDate(LocalDateTime.now())
                .group(true)
                .markedOnDelete(true)
                .build();
    }
}

