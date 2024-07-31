package com.reroute.tmsfm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reroute.tmsfm.validate.ValidationMarker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changedDate;
    private boolean markedOnDelete;
}
