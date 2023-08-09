package com.tuke.fei.kpi.isvote.modules.resultVote.model;

import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`voteResult`")
@Getter
@Setter
public class ResultVote {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", unique = true, nullable = false)
    private String id;

    @Column(name = "VoteResult", nullable = false)
    private String resultVote;

    @OneToOne
    @CreatedBy
    @JoinColumn(name = "VoteResultVote", nullable = false)
    private Vote vote;

    @OneToOne
    @CreatedBy
    @JoinColumn(name = "VoteResultUser", nullable = false)
    private User user;

    @Column(name = "SecretName")
    private String secretName;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "ChangedAt")
    private LocalDateTime changedAt;
}
