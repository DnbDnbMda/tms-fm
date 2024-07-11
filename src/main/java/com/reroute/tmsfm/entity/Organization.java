package com.reroute.tmsfm.entity;

import com.reroute.tmsfm.utility.Constant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ref_organization")
public class Organization {

    @Id
    private UUID id;
    @Column(name = "inn")
    private String inn;
    @Column(name = "kpp")
    private String kpp;
    @Column(name = "name")
    @NotNull(message = "Наименование организации не может быть пустым")
    @NotBlank(message = "Наименование организации не может быть пустым")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "is_group")
    private Boolean group;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime createdDate;
    @Column(name = "changed_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime changedDate;
    @Column(name = "is_marked_deletion")
    private Boolean markedOnDelete;

    @Override
    public String toString() {
        String strId = id == null ? "" : this.getId().toString();
        String strGroup = group == null ? "" : this.group.toString();
        String strCreatedDate = createdDate == null ? "" : this.getCreatedDate().toString();
        String strChangedDate = changedDate == null ? "" : this.getChangedDate().toString();
        String strName = name == null ? "" : this.getName();
        String strFullName = fullName == null ? "" : this.getFullName();
        String strMarkedOnDelete = markedOnDelete == null ? "" : this.getMarkedOnDelete().toString();
        String strInn = inn == null ? "" : this.getInn();
        String strKpp = kpp == null ? "" : this.getKpp();

        return "Organization{" +
                "id=" + strId +
                ", group=" + strGroup +
                ", createdDate=" + strCreatedDate +
                ", changedDate=" + strChangedDate +
                ", name='" + strName +
                ", fullName='" + strFullName +
                ", markedOnDelete=" + strMarkedOnDelete +
                ", inn='" + strInn +
                ", kpp='" + strKpp +
                '}';
    }
}
