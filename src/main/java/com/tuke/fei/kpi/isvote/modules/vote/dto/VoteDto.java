package com.tuke.fei.kpi.isvote.modules.vote.dto;

import com.tuke.fei.kpi.isvote.modules.common.dto.UserUpdateDto;
import com.tuke.fei.kpi.isvote.modules.common.model.User;
import com.tuke.fei.kpi.isvote.modules.vote.model.VoteUsers;
import lombok.Getter;
import lombok.Setter;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VoteDto {

    private String id;

    private UserDto user;

    private List<String> answersVote;

    private List<VoteUsersDto> voteUsers;

    private LocalDateTime term;

    private String note;

    private Integer typeVote;

    private Integer activeVoting;

    private LocalDateTime duration;

    private String contentVote;

    private String title;

}
