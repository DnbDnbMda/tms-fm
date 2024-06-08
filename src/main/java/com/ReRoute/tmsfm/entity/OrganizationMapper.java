package com.ReRoute.tmsfm.entity;

import com.ReRoute.tmsfm.dto.OrganizationCreateDto;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;


public class OrganizationMapper {

    public Organization toOrganization(OrganizationCreateDto organizationCreateDto) {
        return Organization.builder()
                .id(null)
                .parent(organizationCreateDto.getParent())
                .group(organizationCreateDto.getGroup())
                .changedDate(null)
                .createdDate(null)
                .changeAuthor(null)
                .createAuthor(null)
                .inn(organizationCreateDto.getInn())
                .kpp(organizationCreateDto.getKpp())
                .name(organizationCreateDto.getName())
                .fullName(organizationCreateDto.getFullName())
                .markedOnDelete(organizationCreateDto.getMarkedOnDelete())
                .build();
    }
}
