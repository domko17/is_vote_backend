package com.tuke.fei.kpi.isvote.modules.vote.repository;

import com.tuke.fei.kpi.isvote.modules.vote.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String> {

    List<Vote> findAllByUser(User user);

    Page<Vote> findByUser(User user, Pageable pageable);
}
