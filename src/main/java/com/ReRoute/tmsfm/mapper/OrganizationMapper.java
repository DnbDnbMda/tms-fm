package com.ReRoute.tmsfm.mapper;

import com.ReRoute.tmsfm.dto.OrganizationDto;
import com.ReRoute.tmsfm.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "fullname", source = "fullname")
    Organization toOrganization(OrganizationDto organization);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "fullname", source = "fullname")
    OrganizationDto toOrganizationDto(Organization organization);
}
