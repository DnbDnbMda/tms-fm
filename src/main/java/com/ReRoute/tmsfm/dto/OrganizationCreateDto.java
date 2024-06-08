package com.ReRoute.tmsfm.dto;

import com.ReRoute.tmsfm.entity.Organization;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
public class OrganizationCreateDto {

    private Organization parent;
    private Boolean group;
    private String name;
    private String fullName;
    private Boolean markedOnDelete;
    private String inn;
    private String kpp;

}
