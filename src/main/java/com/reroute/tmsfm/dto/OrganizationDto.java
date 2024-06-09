package com.reroute.tmsfm.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizationDto {

    private UUID id;
    private UUID parent;
    private Boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    private String name;
    private String fullName;
    private UUID createAuthor;
    private UUID changeAuthor;
    private Boolean markedOnDelete;
    private String inn;
    private String kpp;
    private UUID ownerOrganization;
}
