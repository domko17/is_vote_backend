package com.tuke.fei.kpi.isvote.modules.vote.repository;

import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteDto;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import com.tuke.fei.kpi.isvote.modules.vote.model.VoteUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteUsersRepository extends JpaRepository<VoteUsers, String> {
    List<VoteUsers> findAllByUser(User user);
    List<VoteUsers> findAllByVote(Vote vote);
}
