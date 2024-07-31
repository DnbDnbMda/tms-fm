package com.reroute.tmsfm.dto;

import com.reroute.tmsfm.validate.ValidationMarker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrganizationDto {
    @NotNull(message = "Идентификатор не может быть null", groups = {ValidationMarker.OnUpdate.class})
    private UUID id;
    @NotBlank(message = "Некорректный ИНН организации")
    private String inn;
    @NotBlank(message = "Некорректный КПП организации")
    private String kpp;
    @NotBlank(message = "Наименование организации не может быть null",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    private String name;
    @NotBlank(message = "Наименование организации не может быть null",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    private String fullName;
    private boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    private boolean markedOnDelete;
}
