package com.ReRoute.tmsfm.entity;

import com.ReRoute.tmsfm.service.Constant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "ref_organization")
public class Organization {

    @Id
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Organization parent;
    @Column(name = "is_group")
    private Boolean group;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = Constant.dateTimeFormat)
    private LocalDateTime createdDate;
    @Column(name = "changed_at")
    @DateTimeFormat(pattern = Constant.dateTimeFormat)
    private LocalDateTime changedDate;
    @Column(name = "name")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Account createAuthor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Account changeAuthor;
    @Column(name = "is_marked_deletion")
    private Boolean markedOnDelete;
    @Column(name = "inn")
    private String inn;
    @Column(name = "kpp")
    private String kpp;


}
