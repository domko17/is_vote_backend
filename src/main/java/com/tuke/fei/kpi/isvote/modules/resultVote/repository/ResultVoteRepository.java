package com.tuke.fei.kpi.isvote.modules.resultVote.repository;

import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.resultVote.model.ResultVote;
import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultVoteRepository extends JpaRepository<ResultVote, String> {

    List<ResultVote> findAllByUser(User user);

    List<ResultVote> findAllByVote(Vote vote);

}
