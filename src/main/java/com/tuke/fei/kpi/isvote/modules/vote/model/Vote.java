package com.tuke.fei.kpi.isvote.modules.vote.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

import javax.persistence.*;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`vote`")
@Getter
@Setter
public class Vote {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", unique = true, nullable = false)
    private String id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "ContentVote", nullable = false)
    private String contentVote;

    @Column(name = "Term", nullable = false)
    private LocalDateTime term;

    @Column(name = "Duration", nullable = false)
    private LocalDateTime duration;

    @Column(name = "Note")
    private String note;

    @Column(name = "TypeVote", nullable = false)
    private Integer typeVote;

    @ElementCollection
    @Column(name = "AnswersVote")
    private Collection<String> answersVote;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private Set<VoteUsers> voteUsers = new HashSet<>();


    @Column(name = "ActiveVoting", nullable = false)
    private Integer activeVoting;

    @OneToOne
    @CreatedBy
    @JoinColumn(name = "CreatedById", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "ChangedAt")
    private LocalDateTime changedAt;

}
