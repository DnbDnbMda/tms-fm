package com.ReRoute.tmsfm.dto;

import com.ReRoute.tmsfm.entity.Organization;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrganizationDto {

    private UUID id;
    private Organization parent;
    private Boolean group;
    private LocalDateTime createdDate;
    private LocalDateTime changedDate;
    private String name;
    private AccountDto authorDto;
    private Boolean markedOnDelete;
    private Boolean typeReference;
    private String inn;
    private String kpp;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(LocalDateTime changedDate) {
        this.changedDate = changedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AccountDto authorDto) {
        this.authorDto = authorDto;
    }

    public Boolean getMarkedOnDelete() {
        return markedOnDelete;
    }

    public void setMarkedOnDelete(Boolean markedOnDelete) {
        this.markedOnDelete = markedOnDelete;
    }

    public Boolean getTypeReference() {
        return typeReference;
    }

    public void setTypeReference(Boolean typeReference) {
        this.typeReference = typeReference;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }
}
