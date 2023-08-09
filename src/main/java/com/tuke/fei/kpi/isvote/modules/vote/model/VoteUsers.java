package com.tuke.fei.kpi.isvote.modules.vote.model;

import com.tuke.fei.kpi.isvote.modules.common.model.User;
import lombok.*;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VoteUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public VoteUsers(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteUsers)) return false;
        VoteUsers that = (VoteUsers) o;
        return Objects.equals(vote.getTitle(), that.vote.getTitle()) &&
                Objects.equals(user.getFirstname(), that.user.getFirstname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(vote.getTitle(), user.getFirstname());
    }

}
