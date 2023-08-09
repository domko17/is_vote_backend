package com.tuke.fei.kpi.isvote.modules.common.model;

import com.tuke.fei.kpi.isvote.modules.vote.model.VoteUsers;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import com.tuke.fei.kpi.isvote.modules.common.enums.UserType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class User {
//    user model
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", unique = true, nullable = false)
    private String id;

    @Convert(converter = UserType.UserTypeConverter.class)
    @Column(name = "UserTypeId")
    private UserType userType;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Mail", nullable = false)
    private String mail;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private Set<VoteUsers> voteUsers = new HashSet<>();

    @Column(name = "Title", nullable = true)
    private String title;

    @Column(name = "Firstname", nullable = false)
    private String firstname;

    @Column(name = "Lastname", nullable = false)
    private String lastname;

    @Column(name = "Password", length = 128)
    private String password;

    @Column(name = "CreatedUser", nullable = true)
    private String createdUser;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "ChangedAt")
    private LocalDateTime changedAt;

    @Column(name = "LastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "IsVisible", nullable = false)
    private Boolean isVisible = true;

}
