package com.reroute.tmsfm.dto;

import com.reroute.tmsfm.validate.ValidationMarker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrganizationDto {
    @NotNull(message = "Идентификатор не может быть null", groups = {ValidationMarker.OnUpdate.class})
    @org.hibernate.validator.constraints.UUID(message = "Некорректный идентификатор организации")
    private String id;
    @org.hibernate.validator.constraints.UUID(message = "Неккорректный идентфиикатор организации родителя")
    private String parent;
    @NotNull(message = "Признак принадлежности к группе не может быть null")
    private Boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    @NotNull(message = "Наименование организации не может быть null",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    @NotBlank(message = "Наименование организации не может содержать одни пробелы",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    @NotEmpty(message = "Наименование организации не может быть пустым",
            groups = {ValidationMarker.OnCreate.class,
                    ValidationMarker.OnUpdate.class})
    private String name;
    private String fullName;
    @org.hibernate.validator.constraints.UUID(message = "Неккорректный идентфиикатор пользователя создателя")
    private String createAuthor;
    @org.hibernate.validator.constraints.UUID(message = "Неккорректный идентфиикатор пользователя изменившего")
    private String changeAuthor;
    @NotNull(message = "Признак пометки на удаление не может быть null")
    private Boolean markedOnDelete;
    private String inn;
    private String kpp;
    @org.hibernate.validator.constraints.UUID(message = "Неккорректный идентфиикатор организации владельца")
    private String ownerOrganization;
}
