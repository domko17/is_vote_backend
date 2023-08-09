package com.tuke.fei.kpi.isvote.modules.resultVote.dto;

import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.vote.dto.VoteDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ResultVoteDto {


    private String id;

    private String resultVote;

    private UserDto user;

    private VoteDto vote;
}
