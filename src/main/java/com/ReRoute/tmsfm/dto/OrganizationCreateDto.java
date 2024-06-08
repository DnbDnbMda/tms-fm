package com.ReRoute.tmsfm.dto;

import com.ReRoute.tmsfm.entity.Organization;
import lombok.*;

@Builder
@ToString
public class OrganizationCreateDto {

    private Organization parent;
    private Boolean group;
    private String name;
    private String fullName;
    private Boolean markedOnDelete;
    private String inn;
    private String kpp;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getMarkedOnDelete() {
        return markedOnDelete;
    }

    public void setMarkedOnDelete(Boolean markedOnDelete) {
        this.markedOnDelete = markedOnDelete;
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
