package com.tuke.fei.kpi.isvote.modules.resultVote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ResultVoteUpdateDto {


    @NotNull
    @NotBlank
    private String id;

}
