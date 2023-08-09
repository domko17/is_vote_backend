package com.tuke.fei.kpi.isvote.modules.vote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VoteStateDto {

    @NotNull
    private Integer active;

    @NotNull
    private String voteId;

}
