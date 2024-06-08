package com.ReRoute.tmsfm.entity;


import com.ReRoute.tmsfm.service.AccountStatus;
import com.ReRoute.tmsfm.service.Constant;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ref_accounts")
public class Account {

    @Id
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
    @DateTimeFormat(pattern = Constant.dateFormat)
    private LocalDate birthday;
    @Column(name = "phone")
    private String phone;
    @Column(name = "created_at")
    private LocalDateTime createdDate;
    @Column(name = "updated_at")
    private LocalDateTime changedDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Account author;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Account authorOfChange;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Organization organization;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Column(name = "is_marked_deletion")
    private Boolean MarkedOnDelete;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Account parent;
    @Column(name = "is_group")
    private Boolean group;


}
