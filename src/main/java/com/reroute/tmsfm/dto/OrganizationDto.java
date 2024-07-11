package com.reroute.tmsfm.dto;

import com.reroute.tmsfm.validate.ValidationMarker;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrganizationDto {
    @NotNull(message = "Идентификатор не может быть null", groups = {ValidationMarker.OnUpdate.class})
    @UUID(message = "Некорректный идентификатор организации")
    private String id;
    private String inn;
    private String kpp;
    @NotNull(message = "Наименование организации не может быть null",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    private String name;
    private String fullName;
    @NotNull(message = "Признак принадлежности к группе не может быть null")
    private Boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    private Boolean markedOnDelete;
}
