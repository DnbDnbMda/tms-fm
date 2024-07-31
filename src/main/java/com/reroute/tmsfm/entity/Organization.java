package com.reroute.tmsfm.entity;

import com.reroute.tmsfm.utility.Constant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "inn")
    private String inn;
    @Column(name = "kpp")
    private String kpp;
    @Column(name = "name")
    @NotBlank(message = "Наименование организации не может быть пустым")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "is_group")
    private boolean group;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime createdDate;
    @Column(name = "changed_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime changedDate;
    @Column(name = "is_marked_deletion")
    private boolean markedOnDelete;

    @Override
    public String toString() {
        String strId = id == null ? "" : this.getId().toString();
        String strGroup = String.valueOf(group);
        String strCreatedDate = createdDate == null ? "" : this.getCreatedDate().toString();
        String strChangedDate = changedDate == null ? "" : this.getChangedDate().toString();
        String strName = name == null ? "" : this.getName();
        String strFullName = fullName == null ? "" : this.getFullName();
        String strMarkedOnDelete = String.valueOf(markedOnDelete);
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
