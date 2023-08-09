package com.tuke.fei.kpi.isvote.modules.resultVote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ResultVoteCreateDto {


    @NotNull
    @NotBlank
    private String resultVote;

    @NotNull
    @NotBlank
    private String voteId;

}
