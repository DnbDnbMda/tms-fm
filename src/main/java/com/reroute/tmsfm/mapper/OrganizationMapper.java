package com.reroute.tmsfm.mapper;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(target = "name", source = "organizationDto.name")
    @Mapping(target = "fullName", source = "organizationDto.fullName")
    @Mapping(target = "group", source = "organizationDto.group")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "changedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", source = "organizationDto.id")
    @Mapping(target = "markedOnDelete", source = "organizationDto.markedOnDelete")
    @Mapping(target = "inn", source = "organizationDto.inn")
    @Mapping(target = "kpp", source = "organizationDto.kpp")
    Organization toOrganization(OrganizationDto organizationDto);

    OrganizationDto toOrganizationDto(Organization organization);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "changedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "name", source = "organizationDto.name")
    @Mapping(target = "fullName", source = "organizationDto.fullName")
    @Mapping(target = "inn", source = "organizationDto.inn")
    @Mapping(target = "kpp", source = "organizationDto.kpp")
    @Mapping(target = "markedOnDelete", source = "organizationDto.markedOnDelete")
    @Mapping(target = "group", source = "organizationDto.group")
    Organization updateOrganization(OrganizationDto organizationDto, @MappingTarget Organization organization);

}
