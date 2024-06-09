package com.reroute.tmsfm.entity;


import com.reroute.tmsfm.utility.AccountStatus;
import com.reroute.tmsfm.utility.Constant;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ref_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "middlename")
    private String middleName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "birthday")
    @DateTimeFormat(pattern = Constant.DATE_FORMAT)
    private LocalDate birthday;
    @Column(name = "phone")
    private String phone;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
    @Column(name = "updated_at")
    private LocalDateTime changedDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Account author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_id")
    private Account authorOfChange;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_organization")
    private Organization organization;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Column(name = "is_marked_deletion")
    private Boolean markedOnDelete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Account parent;
    @Column(name = "is_group")
    private Boolean group;
}
