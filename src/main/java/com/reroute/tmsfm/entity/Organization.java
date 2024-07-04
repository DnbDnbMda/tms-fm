package com.reroute.tmsfm.entity;

import com.reroute.tmsfm.utility.Constant;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ref_organization")
public class Organization {

    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Organization parent;
    @Column(name = "is_group")
    private Boolean group;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime createdDate;
    @Column(name = "changed_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime changedDate;
    @Column(name = "name")
    @NotNull(message = "Наименование организации не может быть пустым")
    @NotBlank(message = "Наименование организации не может быть пустым")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @Valid
    @NotNull(message = "Автор организации не может быть пустым")
    private Account createAuthor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_id")
    private Account changeAuthor;
    @Column(name = "is_marked_deletion")
    private Boolean markedOnDelete;
    @Column(name = "inn")
    private String inn;
    @Column(name = "kpp")
    private String kpp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_organization")
    private Organization ownerOrganization;

    @Override
    public String toString() {
        String strId = id == null ? "" : this.getId().toString();
        String strParent = parent == null ? "" : this.getParent().getId().toString();
        String strGroup = group == null ? "" : this.group.toString();
        String strCreatedDate = createdDate == null ? "" : this.getCreatedDate().toString();
        String strChangedDate = changedDate == null ? "" : this.getChangedDate().toString();
        String strName = name == null ? "" : this.getName();
        String strFullName = fullName == null ? "" : this.getFullName();
        String strCreateAuthor = createAuthor == null ? "" : this.getCreateAuthor().getId().toString();
        String strChangeAuthor = changeAuthor == null ? "" : this.getChangeAuthor().getId().toString();
        String strMarkedOnDelete = markedOnDelete == null ? "" : this.getMarkedOnDelete().toString();
        String strInn = inn == null ? "" : this.getInn();
        String strKpp = kpp == null ? "" : this.getKpp();
        String strOwnerOrganization = ownerOrganization == null ? "" : this.getOwnerOrganization().getId().toString();

        return "Organization{" +
                "id=" + strId +
                ", parent=" + strParent +
                ", group=" + strGroup +
                ", createdDate=" + strCreatedDate +
                ", changedDate=" + strChangedDate +
                ", name='" + strName +
                ", fullName='" + strFullName +
                ", createAuthor=" + strCreateAuthor +
                ", changeAuthor=" + strChangeAuthor +
                ", markedOnDelete=" + strMarkedOnDelete +
                ", inn='" + strInn +
                ", kpp='" + strKpp +
                ", ownerOrganization=" + strOwnerOrganization +
                '}';
    }
}
