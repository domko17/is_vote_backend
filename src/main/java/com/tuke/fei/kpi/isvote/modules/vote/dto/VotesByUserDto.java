package com.tuke.fei.kpi.isvote.modules.vote.dto;

import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;
import com.tuke.fei.kpi.isvote.modules.resultVote.model.ResultVote;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VotesByUserDto {

    private String id;

    private UserDto user;

    private List<String> answersVote;

    private List<String> resultVote;

    private List<VoteUsersDto> voteUsers;

    private LocalDateTime term;

    private String note;

    private Integer typeVote;

    private Integer activeVoting;

    private LocalDateTime duration;

    private String contentVote;

    private String title;
}
