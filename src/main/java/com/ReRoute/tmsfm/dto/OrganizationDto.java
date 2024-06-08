package com.ReRoute.tmsfm.dto;

import com.ReRoute.tmsfm.entity.Account;
import com.ReRoute.tmsfm.entity.Organization;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class OrganizationDto {

    private UUID id;
    private Organization parent;
    private Boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    private String name;
    private String fullName;
    private Account createAuthor;
    private Account changeAuthor;
    private Boolean markedOnDelete;
    private String inn;
    private String kpp;
}
