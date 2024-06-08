package com.reroute.tmsfm.dto;

import com.reroute.tmsfm.utility.ValidationMarker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
    @NotNull(message = "Идентификатор не может быть null", groups = {ValidationMarker.OnUpdate.class})
    private UUID id;
    private UUID parent;
    private Boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    @NotNull(message = "Наименование организации не может быть null",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    @NotBlank(message = "Наименование организации не может содержать пробелы",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    @NotEmpty(message = "Наименование организации не может быть пустым",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    private String name;
    private String fullName;
    private UUID createAuthor;
    private UUID changeAuthor;
    private Boolean markedOnDelete;
    private String inn;
    private String kpp;
    private UUID ownerOrganization;
}
